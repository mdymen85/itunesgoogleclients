package com.kramphub.recruitment.client.webclient.google;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleWebClientService {
	
    public Mono<GoogleApiDTO> getGoogle(String term, Integer limit) {
    	
    	var params = Map.of("term",term,"limit", String.valueOf(limit));
    	
		var client = WebClient.builder()
			.baseUrl("https://www.googleapis.com/books/v1/volumes?q={term}&maxResults={limit}")
			.defaultUriVariables(params)
			.build();
    	
		return client.get()
			.accept(MediaType.ALL)
			.retrieve()
			.bodyToMono(GoogleApiDTO.class)
			.log();
    }
	
}
