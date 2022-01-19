package com.kramphub.recruitment.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kramphub.recruitment.client.GoogleApiDTO;
import com.kramphub.recruitment.client.GoogleApiDTOs;
import com.kramphub.recruitment.client.ITunesClient;
import com.kramphub.recruitment.client.ResponseFeignGoogleBooksAPI;
import com.kramphub.recruitment.client.ResponseFeignITunesDTO;
import com.kramphub.recruitment.client.ResponsesFeign;
import com.kramphub.recruitment.dto.ResponseFunDTO;
import com.kramphub.recruitment.dto.ResponseListFunDTO;
import com.kramphub.recruitment.dto.TypeResponse;
import com.kramphub.recruitment.service.circuitbreaker.CircuitBreakerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaService {
	
	private final LoadService loadService;
	
	@Value("${application.max-search.number:5}")
	private Integer limit;
	
	@Value("${application.timeout.number:30}")
	private Long timeout;
	
	@Transactional(timeout = 60)
	public ResponseListFunDTO get(String text) throws InterruptedException, ExecutionException, TimeoutException {
		
//		var responseITunes = loadService.getITunes(text);
//		var responseGoogleApi = loadService.getGoogleApi(text);
//				
//		
		CompletableFuture<ResponsesFeign> responseITunes =  CompletableFuture.supplyAsync(() -> loadService.getITunes(text));
		CompletableFuture<GoogleApiDTO> responseGoogleApi =  CompletableFuture.supplyAsync(() -> loadService.getGoogleApi(text));
//		
		
		CompletableFuture.allOf(responseITunes, responseGoogleApi).join();
		
		var returnITunes = toResponsesFun(responseITunes.get());
		var returnTotal = returnITunes.addSet(toResponsesFun(responseGoogleApi.get()));
		
//		
//		var resultITunes = itunes.get();
//		var resultGoogleApi = googleApi.get();
		
		//TODO: here i need to combine the response from the itunes and googleapi
		return returnTotal;
		
	}
	
	private ResponseListFunDTO toResponsesFun(GoogleApiDTO googleApiDTO) {
		
		return new ResponseListFunDTO(googleApiDTO.getItems()
			.stream()
			.map(response -> this.toResponseFunDTO(response))
			.collect(Collectors.toSet()));	
	}
	
	private ResponseListFunDTO toResponsesFun(ResponsesFeign responseFromFeign) {
		
		return new ResponseListFunDTO(responseFromFeign.getResults()
			.stream()
			.map(response -> this.toResponseFunDTO(response))
			.collect(Collectors.toSet()));		
	}
	
	private ResponseFunDTO toResponseFunDTO(ResponseFeignITunesDTO responseFeignITunes) {
		return ResponseFunDTO
				.builder()
				.author(responseFeignITunes.getArtistName())
				.title(responseFeignITunes.getCollectionName())
				.type(TypeResponse.ALBUM)
				.build();
	}
	
	
	private ResponseFunDTO toResponseFunDTO(GoogleApiDTOs googleApiDTOs) {
		return ResponseFunDTO
			.builder()
			.author(googleApiDTOs.getVolumeInfo() != null && 
					googleApiDTOs.getVolumeInfo().getAuthors() != null ? 
							googleApiDTOs.getVolumeInfo().getAuthors().get(0) : 
								null)
			.title(googleApiDTOs.getVolumeInfo().getTitle())
			.type(TypeResponse.BOOK)
			.build();
	}
	
}
