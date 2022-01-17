package com.kramphub.recruitment.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class InformationControllerAdvice {

	private final MessageSource messageSource;
	
	@ExceptionHandler(UnavailableException.class) 
	public ResponseEntity<ResponseErrorObject> buildingBasicInformationException(UnavailableException error) {			
		return new ResponseError(error.getCode(), 
				messageSource.getMessage(error.getCode(), null, Locale.ENGLISH), 
				HttpStatus.BAD_REQUEST)
				.getResponseErrorObject(); 
	}
}
