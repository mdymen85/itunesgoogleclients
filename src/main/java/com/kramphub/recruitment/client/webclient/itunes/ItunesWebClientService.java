package com.kramphub.recruitment.client.webclient.itunes;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

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
	
    public Mono<ItunesResults> getItunes(String term, Integer limit) {
    	
    	var params = Map.of("term",term,"limit", String.valueOf(limit));
    	
		var client = WebClient.builder()
			.exchangeStrategies(strategies)
			.baseUrl(url)
			.defaultUriVariables(params)
			.build();
    	
		return client.get()
			.accept(MediaType.ALL)
			.retrieve()
			.bodyToMono(ItunesResults.class)
			.log()
			.onErrorReturn(new ItunesResults());
    }

}