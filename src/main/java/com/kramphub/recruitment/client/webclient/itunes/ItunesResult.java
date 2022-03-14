package com.kramphub.recruitment.client.webclient.itunes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItunesResult {

	private String artistName;
	private String collectionName;
	
	public ItunesResult() {}
	
}
