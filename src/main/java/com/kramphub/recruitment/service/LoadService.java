package com.kramphub.recruitment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.kramphub.recruitment.client.feign.GoogleApiClient;
import com.kramphub.recruitment.client.feign.GoogleApiDTO;
import com.kramphub.recruitment.client.feign.ITunesClient;
import com.kramphub.recruitment.client.feign.ResponseFeignITunesDTO;
import com.kramphub.recruitment.client.feign.ResponsesFeign;
import com.kramphub.recruitment.dto.ResponseListFunDTO;
import com.kramphub.recruitment.exception.UnavailableException;
import com.kramphub.recruitment.service.circuitbreaker.CircuitBreakerFactory;
import com.kramphub.recruitment.service.circuitbreaker.CircuitBreakerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoadService {
	
	private static String ITUNES_CIRCUIT = "ITunes";
	private static String GOOGLE_API_CIRCUIT = "Google_Books";

	private final CircuitBreakerService<ResponsesFeign> circuitBreakerITunesService;
	private final CircuitBreakerService<GoogleApiDTO> circuitBreakerGoogleService;
	
	private final CircuitBreakerFactory circuitBreakerFactory;

	private final ITunesClient itunesClient;
	private final GoogleApiClient googleApiClient;	
	
	@Value("${application.max-search.number:5}")
	private Integer limit;

	public ResponsesFeign getITunes(String text) {
    	
		return circuitBreakerFactory.getCircuitBreakerITunes().get(text, ITUNES_CIRCUIT, () -> itunesClient.get(text, limit.intValue()));
		
    	//return circuitBreakerITunesService.get(text, ITUNES_CIRCUIT, () -> itunesClient.get(text, limit.intValue()));    
		
	}

	public GoogleApiDTO getGoogleApi(String text) {

		return circuitBreakerGoogleService.get(text, GOOGLE_API_CIRCUIT, () -> googleApiClient.get(text, limit.intValue()));
		
	}
	
}
