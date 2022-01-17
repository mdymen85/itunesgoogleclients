package com.kramphub.recruitment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kramphub.recruitment.dto.ResponseListFunDTO;
import com.kramphub.recruitment.service.SagaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FunController {
	
	private final SagaService sagaService;

	@RequestMapping(path = "/v1/fun/{text}", method = RequestMethod.GET)
	public ResponseEntity<ResponseListFunDTO> get(@PathVariable String text) throws Exception {
		
		var response = sagaService.get(text);
		return new ResponseEntity<ResponseListFunDTO>(response, HttpStatus.OK);
	}
	
}
