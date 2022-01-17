package com.kramphub.recruitment.dto;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

@Data
public class ResponseListFunDTO {

	private Set<ResponseFunDTO> results = new TreeSet<ResponseFunDTO>();
	
	public ResponseListFunDTO(Set<ResponseFunDTO> results) {
		this.results = results;
	}
	
	public ResponseListFunDTO addSet(ResponseListFunDTO responseListFunDTO) {
		this.results.addAll(responseListFunDTO.getResults());
		return this;
	}

	
	
	
	
}
