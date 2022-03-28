package com.kramphub.recruitment.client.webclient.google;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.kramphub.recruitment.client.webclient.itunes.ItunesResults;
import com.kramphub.recruitment.exception.UnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleWebClientService {
	
	private static String ENDPOINT_GOOGLE = "/books/v1/volumes?q={term}&maxResults={limit}";
	
	@Value("${application.google.url:}")
	private String url;
	
	@CircuitBreaker(name = "googleapi", fallbackMethod = "fallback")
    public Mono<GoogleApiDTO> getGoogle(String term, Integer limit) {
		
		var urlBuilded = new StringBuilder()
				.append(url)
				.append(ENDPOINT_GOOGLE)
				.toString();
    	
		log.info("Searching in Google API, term: {} with limit: {}.", term, limit);
		
    	var params = Map.of("term",term,"limit", String.valueOf(limit));
    	
        HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);

		var client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
			.baseUrl(urlBuilded)
			.defaultUriVariables(params)
			.build();
		
		log.info("Google API url: {}", urlBuilded);
    	
		return client.get()
			.accept(MediaType.ALL)
			.retrieve()
			.bodyToMono(GoogleApiDTO.class)
			.log()
			.onErrorResume(e -> Mono.error(new UnavailableException()));
    }
    
	private Mono<GoogleApiDTO> fallback(String term, Integer limit, Throwable throwable) {
		log.error("Circuit breaker active: {}", throwable.getMessage(), throwable);
		return Mono.just(new GoogleApiDTO());
	}
	
}
