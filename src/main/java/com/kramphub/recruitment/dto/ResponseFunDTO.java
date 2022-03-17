package com.kramphub.recruitment.dto;

import lombok.Builder;
import lombok.Data;

@Deprecated
@Data
@Builder
public class ResponseFunDTO {

	private String title;
	private String author;
	private TypeResponse type;
	
}
