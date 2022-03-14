package com.kramphub.recruitment.client.feign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsesFeign {
	
	private List<ResponseFeignITunesDTO> results;
		
}
