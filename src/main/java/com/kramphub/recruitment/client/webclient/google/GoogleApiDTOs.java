package com.kramphub.recruitment.client.webclient.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GoogleApiDTOs {

	private ResponseFeignGoogleBooksAPI volumeInfo;
	
	public GoogleApiDTOs() {}
	
}
