package com.kramphub.recruitment.client.webclient.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class GoogleApiDTOs {

	private ResponseFeignGoogleBooksAPI volumeInfo;
	
	public GoogleApiDTOs() {}
	
}
