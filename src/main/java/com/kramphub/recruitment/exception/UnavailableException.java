package com.kramphub.recruitment.exception;

@SuppressWarnings("serial")
public class UnavailableException extends BaseClassException {

	@Override
	protected String getCode() {
		return "REC-1";
	}

}
