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
				
		var wrapper = this.geITunes(text);
		this.getGoogle(text, wrapper);
		
		ResponseListFunDTO returnITunes = null;
		
		if (wrapper.getITunesException() == null) {
			returnITunes = toResponsesFun(wrapper.getResponseFeign());	
		}
		
		if (returnITunes == null) {
			return toResponsesFun(wrapper.getGoogleApiDTO());
		}
		
		if (wrapper.getGoogleApiException() != null) {
			return returnITunes;
		}
		
		return returnITunes.addSet(toResponsesFun(wrapper.getGoogleApiDTO()));
				
	}
	
	private WrapperResultClientCall geITunes(String text) {
		try {
			var response = loadService.getITunes(text);
			return WrapperResultClientCall
					.builder()
					.responseFeign(response)
					.build();
		}
		catch (Exception e) {
			return WrapperResultClientCall
					.builder()
					.iTunesException(e)
					.build();
		}
	}
	
	private WrapperResultClientCall getGoogle(String text, WrapperResultClientCall wrapper) {
		try {
			var googleApiDTO = loadService.getGoogleApi(text);			
			wrapper.setGoogleApiDTO(googleApiDTO);
			return wrapper;
		}
		catch (Exception e) {
			if (wrapper.getITunesException() != null) {
				throw e;
			}
			wrapper.setGoogleApiException(e);
			return wrapper;
		}
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
