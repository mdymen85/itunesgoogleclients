package com.kramphub.recruitment.exception;

@SuppressWarnings("serial")
public abstract class BaseClassException extends RuntimeException {

	protected abstract String getCode();
	
}
