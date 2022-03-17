package com.kramphub.recruitment.client.webclient.itunes;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.kramphub.recruitment.exception.BusinessError;
import com.kramphub.recruitment.exception.FeignCallNotPermittedException;
import com.kramphub.recruitment.exception.UnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItunesWebClientService {

	@Value("${application.itunes.url:}")
	private String url;
	
	@Qualifier("webclientStrategy")
	private final ExchangeStrategies strategies;
	
	@CircuitBreaker(name = "itunes", fallbackMethod = "fallback")
    public Mono<ItunesResults> getItunes(String term, Integer limit) {
    	
		log.info("Searching in Itunes, term: {} with limit: {}.", term, limit);
		
    	var params = Map.of("term",term,"limit", String.valueOf(limit));
    	
		var client = WebClient.builder()
			.exchangeStrategies(strategies)
			.baseUrl(url)
			.defaultUriVariables(params)
			.build();
		
		log.info("Itunes url: {}", url);
    	
		return client.get()
			.accept(MediaType.ALL)
			.retrieve()
			.bodyToMono(ItunesResults.class)
			.log()
			.onErrorResume(e -> Mono.error(new UnavailableException()));
    }

	private Mono<ItunesResults> fallback(String term, Integer limit, Throwable throwable) {
		log.error("Circuit breaker active: {}", throwable.getMessage(), throwable);
		return Mono.just(new ItunesResults());
	}

}