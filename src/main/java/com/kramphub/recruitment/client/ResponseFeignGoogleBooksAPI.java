package com.kramphub.recruitment.client;

import java.util.List;

import lombok.Data;

@Data
public class ResponseFeignGoogleBooksAPI {

	private String title;
	private List<String> authors;
	
}
