package com.kramphub.recruitment.client.feign;

import lombok.Builder;
import lombok.Data;

@Deprecated
@Data
@Builder
public class ResponseFeignITunesDTO {

	private String artistName;
	private String collectionName;
	
}
