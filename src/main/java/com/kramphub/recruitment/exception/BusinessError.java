package com.kramphub.recruitment.exception;

@SuppressWarnings("serial")
public class BusinessError extends BaseClassException {
	
	@Override
	protected String getCode() {
		return "REC-3";
	}

}
