package com.kramphub.recruitment.client.webclient;

import com.kramphub.recruitment.client.webclient.google.GoogleApiDTO;
import com.kramphub.recruitment.client.webclient.itunes.ItunesResults;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class ResultAggregate {

	private GoogleApiDTO googleApiDTO;
	private ItunesResults itunesResults;
	
}
