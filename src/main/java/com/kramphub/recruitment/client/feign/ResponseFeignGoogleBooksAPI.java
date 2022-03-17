package com.kramphub.recruitment.client.feign;

import java.util.List;

import lombok.Data;

@Deprecated
@Data
public class ResponseFeignGoogleBooksAPI {

	private String title;
	private List<String> authors;
	
}
