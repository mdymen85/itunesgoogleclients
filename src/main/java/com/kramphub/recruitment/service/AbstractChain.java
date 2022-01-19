package com.kramphub.recruitment.service;

import com.kramphub.recruitment.dto.ResponseListFunDTO;

public abstract class AbstractChain {

	protected AbstractChain next;
	
	public abstract ResponseListFunDTO handle(ResponseListFunDTO response, String text, Boolean previousStepWithError);
	
	public ResponseListFunDTO handleNextIfExist(ResponseListFunDTO response, String text, Boolean previousStepWithError) {
		if (next != null) {
			return this.next.handle(response, text, previousStepWithError);
		}
		return response;
		
	}
	
	public void setNext(AbstractChain next) {
		this.next = next;
	}
	
}
