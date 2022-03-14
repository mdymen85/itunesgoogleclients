package com.kramphub.recruitment.client.webclient.google;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseFeignGoogleBooksAPI {

	private String title;
	private List<String> authors;
	
	public ResponseFeignGoogleBooksAPI() {}
	
}
