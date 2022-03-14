package com.kramphub.recruitment.client.webclient;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class WebClientConfig {


	@Bean(name = "webclientStrategy")
	public ExchangeStrategies getStrategies() {
		return ExchangeStrategies.builder().codecs((configurer) -> {
	        configurer.defaultCodecs().jackson2JsonDecoder(this.getJacksonDecoder());
	    }).build();
	}
	
	@Bean
	public Jackson2JsonDecoder getJacksonDecoder() {
		return new Jackson2JsonDecoder(this.getModelMapper(), this.getMimeType());
	}
	
	@Bean 
	public MimeType getMimeType() {
		return new MimeType("text", "javascript", StandardCharsets.UTF_8); 
	}
	
	@Bean
	public ObjectMapper getModelMapper() {
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
