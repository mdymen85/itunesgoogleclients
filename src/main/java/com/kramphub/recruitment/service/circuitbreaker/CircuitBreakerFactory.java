package com.kramphub.recruitment.service.circuitbreaker;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import com.kramphub.recruitment.client.feign.ResponsesFeign;
import com.kramphub.recruitment.client.webclient.itunes.ItunesResults;
import com.kramphub.recruitment.config.AppConfig;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CircuitBreakerFactory {

	private final ApplicationContext ctx;
	
	@SuppressWarnings("unchecked")
	public CircuitBreakerService<Mono<ItunesResults>> getCircuitBreakerITunes() {
		String[] beanNamesForType = ctx.getBeanNamesForType(ResolvableType.forClassWithGenerics(CircuitBreakerService.class, ItunesResults.class));
		return (CircuitBreakerService<Mono<ItunesResults>>) ctx.getBean(beanNamesForType[0], CircuitBreakerService.class);	
	}
	
}
