package com.kramphub.recruitment.service.circuitbreaker;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import com.kramphub.recruitment.client.feign.ResponsesFeign;
import com.kramphub.recruitment.config.AppConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CircuitBreakerFactory {

	private final ApplicationContext ctx;
	
	@SuppressWarnings("unchecked")
	public CircuitBreakerService<ResponsesFeign> getCircuitBreakerITunes() {
		String[] beanNamesForType = ctx.getBeanNamesForType(ResolvableType.forClassWithGenerics(CircuitBreakerService.class, ResponsesFeign.class));
		return (CircuitBreakerService<ResponsesFeign>) ctx.getBean(beanNamesForType[0], CircuitBreakerService.class);	
	}
	
}
