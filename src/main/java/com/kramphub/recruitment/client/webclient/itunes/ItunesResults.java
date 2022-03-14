package com.kramphub.recruitment.client.webclient.itunes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItunesResults {

	private List<ItunesResult> results;
	
	public ItunesResults() {}
}
