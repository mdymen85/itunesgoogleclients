package com.kramphub.recruitment.exception;

@SuppressWarnings("serial")
public class FeignCallNotPermittedException extends BaseClassException {

	private final String service;
	
	public FeignCallNotPermittedException(String service) {
		this.service = service;
	}
	
	@Override
	protected String getCode() {
		return "REC-2";
	}

	public String getService() {
		return service;
	}

}
