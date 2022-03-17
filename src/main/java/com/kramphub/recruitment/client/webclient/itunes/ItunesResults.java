package com.kramphub.recruitment.client.webclient.itunes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class ItunesResults {

	private List<ItunesResult> results;
	
	public ItunesResults() {}
}
