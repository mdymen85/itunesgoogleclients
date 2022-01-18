package com.kramphub.recruitment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
//@ComponentScan(basePackages = "com.kramphub.")
//@Profile("resilience4j")
public class CircuitBreakerConfiguration {

	@Bean
	public CircuitBreakerRegistry circuitConfig() {
		CircuitBreakerConfig config = CircuitBreakerConfig
				  .custom()
				  .slidingWindowType(SlidingWindowType.COUNT_BASED)
				  .slidingWindowSize(10)
				  .failureRateThreshold(70.0f)
				  .build();
		CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config); 
		return registry;
	}
	
}