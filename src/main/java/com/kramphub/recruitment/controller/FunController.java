package com.kramphub.recruitment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kramphub.recruitment.client.webclient.ProductAggregatorService;
import com.kramphub.recruitment.client.webclient.ResultAggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FunController {
	
	private final ProductAggregatorService aggregatorService;
	
	@RequestMapping(path = "/v1/aggregate/{text}", method = RequestMethod.GET)
	public ResponseEntity<Mono<ResultAggregate>> aggregator(@PathVariable String text) throws Exception {
		
		log.info("Received request for search the word: {}.", text);
		
		var result = aggregatorService.get(text);		
		
		log.info("Result of the research: {} {}.", text, result);
		
		return new ResponseEntity<Mono<ResultAggregate>>(result, HttpStatus.OK);
		
	}
	
}
