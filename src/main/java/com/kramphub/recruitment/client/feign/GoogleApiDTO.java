package com.kramphub.recruitment.client.feign;

import java.util.List;

import lombok.Data;

@Data
public class GoogleApiDTO {

	private List<GoogleApiDTOs> items;
	
}
