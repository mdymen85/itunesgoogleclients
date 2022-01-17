package com.kramphub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kramphub.recruitment.service.CircuitBrakerInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	private CircuitBrakerInterceptor circuitBrakerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	//	registry.addInterceptor(circuitBrakerInterceptor).addPathPatterns("/fun");
	}
	
}
