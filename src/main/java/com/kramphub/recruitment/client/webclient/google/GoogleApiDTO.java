package com.kramphub.recruitment.client.webclient.google;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class GoogleApiDTO {

	private List<GoogleApiDTOs> items;
	
	public GoogleApiDTO() {}
}
