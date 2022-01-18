package com.kramphub.recruitment.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kramphub.recruitment.client.GoogleApiDTO;
import com.kramphub.recruitment.client.GoogleApiDTOs;
import com.kramphub.recruitment.client.ResponseFeignGoogleBooksAPI;
import com.kramphub.recruitment.client.ResponseFeignITunesDTO;
import com.kramphub.recruitment.client.ResponsesFeign;
import com.kramphub.recruitment.dto.ResponseFunDTO;
import com.kramphub.recruitment.dto.ResponseListFunDTO;
import com.kramphub.recruitment.dto.TypeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaService {

	private final CircuitBreakerService circuitBreakerService;
	private final LoadService loadService;
	
	//TODO: Needed to see that an exception from one client dont need to crash de application, because
	//de other client can respond
	public ResponseListFunDTO get(String text) throws InterruptedException, ExecutionException {
		
		var responseITunes = circuitBreakerService.getITunes(text);
		var responseGoogleApi = loadService.getGoogleApi(text);
		
		var returnITunes = toResponsesFun(responseITunes);
		var returnTotal = returnITunes.addSet(toResponsesFun(responseGoogleApi));
		
//		
//		CompletableFuture<Void> itunes =  CompletableFuture.runAsync(() -> loadService.getITunes(text));
//		CompletableFuture<Void> googleApi =  CompletableFuture.runAsync(() -> loadService.getGoogleApi(text));
//		
//		CompletableFuture.allOf(itunes, googleApi).join();
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
