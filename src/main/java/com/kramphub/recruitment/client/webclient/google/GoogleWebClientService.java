package com.kramphub.recruitment.client.webclient.google;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.kramphub.recruitment.client.webclient.itunes.ItunesResults;
import com.kramphub.recruitment.exception.UnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleWebClientService {
	
	@Value("${application.google.url:}")
	private String url;
	
	@CircuitBreaker(name = "googleapi", fallbackMethod = "fallback")
    public Mono<GoogleApiDTO> getGoogle(String term, Integer limit) {
    	
    	var params = Map.of("term",term,"limit", String.valueOf(limit));
    	
		var client = WebClient.builder()
			.baseUrl(url)
			.defaultUriVariables(params)
			.build();
    	
		return client.get()
			.accept(MediaType.ALL)
			.retrieve()
			.bodyToMono(GoogleApiDTO.class)
			.log()
			.onErrorResume(e -> Mono.error(new UnavailableException()));
    }
    
	private Mono<GoogleApiDTO> fallback(String term, Integer limit, Throwable throwable) {
		log.error("Message: {}", throwable.getMessage(), throwable);
		return Mono.just(new GoogleApiDTO());
	}
	
}
