package com.kramphub.recruitment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseError {

	private ResponseEntity<ResponseErrorObject> responseErrorObject;

	@Builder
	public ResponseError(String code, String message, HttpStatus httpStatus) {
		var responseErrorObject = ResponseErrorObject
				.builder()
				.code(code)
				.message(message)
				.build();
		
		this.responseErrorObject = new ResponseEntity<ResponseErrorObject>(responseErrorObject, httpStatus);
	}
	
}
