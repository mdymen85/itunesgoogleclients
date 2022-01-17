package com.kramphub.recruitment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.kramphub.recruitment.client.GoogleApiClient;
import com.kramphub.recruitment.client.GoogleApiDTO;
import com.kramphub.recruitment.client.ITunesClient;
import com.kramphub.recruitment.client.ResponseFeignITunesDTO;
import com.kramphub.recruitment.client.ResponsesFeign;
import com.kramphub.recruitment.dto.ResponseListFunDTO;
import com.kramphub.recruitment.exception.UnavailableException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoadService {

	private final ITunesClient itunesClient;
	private final GoogleApiClient googleApiClient;	
	
	@Value("${application.max-search.number:5}")
	private Integer limit;

    @Retryable( value = UnavailableException.class, maxAttempts = 2)
	public ResponsesFeign getITunes(String text) {
    	
    	try {
    		
    		return itunesClient.get(text, limit.intValue());
    		
    	}
    	catch (Exception e) {
    		throw new UnavailableException();
    	}
		
	}

    @Retryable( value = UnavailableException.class, maxAttempts = 2)
	public GoogleApiDTO getGoogleApi(String text) {
    	try {
    		
    		return googleApiClient.get(text, limit.intValue());
    		
    	}
    	catch (Exception e) {
    		throw new UnavailableException();
    	}
		
	}
	
}
