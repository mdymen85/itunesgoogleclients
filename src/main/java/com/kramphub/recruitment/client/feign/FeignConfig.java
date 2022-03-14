package com.kramphub.recruitment.client.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
	
}
