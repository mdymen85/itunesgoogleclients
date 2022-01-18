package com.kramphub.recruitment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.retry.annotation.EnableRetry;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableRetry
public class AppConfig {
    
	@Bean
    public ResourceBundleMessageSource messageSource() {

        var source = new ResourceBundleMessageSource();
        source.setBasenames("error_messages");
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }
	
}
