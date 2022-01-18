package com.kramphub.recruitment.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.kramphub.recruitment.client.GoogleApiClient;
import com.kramphub.recruitment.client.ITunesClient;
import com.kramphub.recruitment.client.ResponsesFeign;
import com.kramphub.recruitment.exception.UnavailableException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Profile("resilience4j")
@Service
@RequiredArgsConstructor
@Slf4j
public class CircuitBreakerService {

	private final ITunesClient itunesClient;
	private final GoogleApiClient googleApiClient;	
	private final CircuitBreakerRegistry registry;
	
	@Value("${application.max-search.number:5}")
	private Integer limit;

	public ResponsesFeign getITunes(String text) {
    	
    	try {
    		
    		CircuitBreaker circuitBreaker = registry.circuitBreaker("itunes"); 
    		
    		Supplier<ResponsesFeign> supplier = () -> itunesClient.get(text, limit.intValue()); 
    		
    		Supplier<ResponsesFeign> decoratedProductsSupplier = circuitBreaker.decorateSupplier(supplier);
    		
    		return decoratedProductsSupplier.get();
    		
    		//return itunesClient.get(text, limit.intValue());
    		
    	}
    	catch (Exception e) {
    		throw e;
    	}
		
	}
	
}
