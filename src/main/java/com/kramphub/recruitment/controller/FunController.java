package com.kramphub.recruitment.controller;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.recruitment.client.feign.ResponsesFeign;
import com.kramphub.recruitment.client.webclient.ProductAggregatorService;
import com.kramphub.recruitment.client.webclient.ResultAggregate;
import com.kramphub.recruitment.client.webclient.google.GoogleApiDTO;
import com.kramphub.recruitment.client.webclient.google.GoogleApiDTOs;
import com.kramphub.recruitment.client.webclient.google.GoogleWebClientService;
import com.kramphub.recruitment.client.webclient.itunes.ItunesResults;
import com.kramphub.recruitment.client.webclient.itunes.ItunesWebClientService;
import com.kramphub.recruitment.dto.ResponseListFunDTO;
import com.kramphub.recruitment.exception.FeignCallNotPermittedException;
import com.kramphub.recruitment.service.AggregationService;
import com.kramphub.recruitment.service.circuitbreaker.CircuitBreakerService;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FunController {
	
	private final AggregationService sagaService;
	private final ProductAggregatorService itunesService;
	private final ObjectMapper mapper;
	private final ItunesWebClientService itunesClient;
	private final GoogleWebClientService googleClient;
	private final ProductAggregatorService aggregatorService;
	private final CircuitBreakerRegistry register;
	
	private final CircuitBreakerService<String> cservice;
	
	@RequestMapping(path = "/v1/circuit/{text}", method = RequestMethod.GET)
	public String circuit(@PathVariable String text) throws Exception {

		return cservice.get("text", "name", this::doSomething);
		
//		CircuitBreaker circuitBreaker = register
//				  .circuitBreaker("name");
//		
//		Supplier<String> decoratedSupplier = CircuitBreaker
//			    .decorateSupplier(circuitBreaker, this::doSomething);
//
//		return decoratedSupplier.get();
//		
		
	}
	
	public String doSomething() {
		throw new IllegalMonitorStateException("XX");
	}
	
	@RequestMapping(path = "/v1/aggregate/{text}", method = RequestMethod.GET)
	public ResponseEntity<Mono<ResultAggregate>> aggregator(@PathVariable String text) throws Exception {
		var result = aggregatorService.get(text, 5);
		
		return new ResponseEntity<Mono<ResultAggregate>>(result, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(path = "/v1/google/{text}", method = RequestMethod.GET)
	public ResponseEntity<Mono<GoogleApiDTO>> getGoogle(@PathVariable String text) throws Exception {
		var result = googleClient.getGoogle(text, 5);
		
		return new ResponseEntity<Mono<GoogleApiDTO>>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(path = "/v1/fun/{text}", method = RequestMethod.GET)
	public ResponseEntity<Mono<ItunesResults>> get(@PathVariable String text) throws Exception {
		
//		var strategy = ExchangeStrategies.builder().codecs((configurer) -> {
//            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, new MimeType("text", "javascript", StandardCharsets.UTF_8)));
//            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(mapper, new MimeType("text", "javascript", StandardCharsets.UTF_8)));
//        }).build();
		
//		  @SuppressWarnings("deprecation")
//		ExchangeStrategies strategies = ExchangeStrategies.builder().codecs(clientCodecConfigurer ->
//	        clientCodecConfigurer.customCodecs().decoder(
//	                new Jackson2JsonDecoder(mapper,
//	                        new MimeType("text", "javascript", StandardCharsets.UTF_8)))
//	    ).build();
		
//		var connector = new Jackson2JsonDecoder();
		
//		new Jackson2JsonDecoder().decode(arg0, arg1, arg2, arg3)
//		
//		var x = WebClient.builder()
//				.exchangeStrategies(strategy)
//				.baseUrl("https://itunes.apple.com/search?term=jack&limit=5")
//				.defaultHeader("Content-Type", "*/*")//.defaultHeader("Content-Type", "application/json").build();
//				.build();
		
//		Mono<ItunesResults> x2 = x.get()
//		.accept(MediaType.ALL)
//		.retrieve()
//		.bodyToMono(ItunesResults.class)
//		.log();
		
//	public ResponseEntity<ResponseListFunDTO> get(@PathVariable String text) throws Exception {
		
//		itunesService itunes(text, 5+"");
		
//		var x = itunesService.get();
		
//		return null;
		
		var result = itunesClient.getItunes(text, 5);
		
		return new ResponseEntity<Mono<ItunesResults>>(result, HttpStatus.OK);
		
//		var response = sagaService.get(text);
//		return new ResponseEntity<ResponseListFunDTO>(response, HttpStatus.OK);
	}
	
}
