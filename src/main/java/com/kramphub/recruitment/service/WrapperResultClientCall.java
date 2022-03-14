package com.kramphub.recruitment.service;

import com.kramphub.recruitment.client.feign.GoogleApiDTO;
import com.kramphub.recruitment.client.feign.ResponsesFeign;
import com.kramphub.recruitment.exception.BaseClassException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WrapperResultClientCall {

	private ResponsesFeign responseFeign;
	private GoogleApiDTO googleApiDTO;
	private Exception iTunesException;
	private Exception googleApiException;
	
}
