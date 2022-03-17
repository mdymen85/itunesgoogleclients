package com.kramphub.recruitment.client.feign;

import java.util.List;

import lombok.Data;

@Deprecated
@Data
public class GoogleApiDTO {

	private List<GoogleApiDTOs> items;
	
}
