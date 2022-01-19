package com.kramphub.recruitment.service;

import org.springframework.stereotype.Component;

import com.kramphub.recruitment.dto.ResponseListFunDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ITunesHandler extends AbstractChain {
	
	private final LoadService loadService;
	
	@Override
	public ResponseListFunDTO handle(ResponseListFunDTO response, String text, Boolean previousStepWithError) {
		boolean setWithError = true;
		try {
			loadService.getITunes(text);
			this.handleNextIfExist(response, text, !itsOk);
		}
		catch (Exception e) {
			this.handleNextIfExist(response, text, itsOk);
		}
		finally {
			this
		}
		
		return null;
	}

}
