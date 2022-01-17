package com.kramphub.recruitment.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class FeignConfig {

	  @Bean
	  public Decoder feignDecoder() {
	    return new JacksonDecoder();
	  }
	  
	  @Bean
	  public Encoder feignEncoder() {
	    return new JacksonEncoder();
	  }
	  
	  @Bean
	  public ObjectMapper getModelMapper() {
		return new ObjectMapper()
		  .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	  }
	
}
