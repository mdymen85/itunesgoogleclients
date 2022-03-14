package com.kramphub.recruitment.client.webclient;

import org.springframework.stereotype.Service;

import com.kramphub.recruitment.client.webclient.google.GoogleApiDTO;
import com.kramphub.recruitment.client.webclient.google.GoogleWebClientService;
import com.kramphub.recruitment.client.webclient.itunes.ItunesResults;
import com.kramphub.recruitment.client.webclient.itunes.ItunesWebClientService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

	private final ItunesWebClientService itunesService;
	private final GoogleWebClientService googleService;
	
	public Mono<ResultAggregate> get(String term, Integer limit) {
		return Mono.zip(
				this.itunesService.getItunes(term, limit),
				this.googleService.getGoogle(term, limit)
			)
		.map(this::combine);
				
	}
	
	private ResultAggregate combine(Tuple2<ItunesResults, GoogleApiDTO> tuple) {
		return ResultAggregate.create(
				tuple.getT2(), 
				tuple.getT1()
			);
	}
	
}
