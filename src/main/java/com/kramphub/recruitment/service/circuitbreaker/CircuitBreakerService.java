package com.kramphub.recruitment.service.circuitbreaker;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.kramphub.recruitment.client.GoogleApiClient;
import com.kramphub.recruitment.client.ITunesClient;
import com.kramphub.recruitment.client.ResponsesFeign;
import com.kramphub.recruitment.exception.FeignCallNotPermittedException;
import com.kramphub.recruitment.exception.UnavailableException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Profile("resilience4j")
@Service
@RequiredArgsConstructor
@Slf4j
public class CircuitBreakerService<T> {
	
	private final CircuitBreakerRegistry registry;
	
	public T get(String text, String circuit, Supplier<T> supplier) {
    	try {
    		
    		CircuitBreaker circuitBreaker = registry.circuitBreaker(circuit); 
    		
    		Supplier<T> decoratedProductsSupplier = circuitBreaker.decorateSupplier(supplier);
    		
    		return decoratedProductsSupplier.get();    		
    		
    	}
    	catch (CallNotPermittedException e) {
    		throw new FeignCallNotPermittedException(circuit);
    	}
    	catch (Exception e) {
    		throw new UnavailableException();
    	}		
	}
	
}
