package com.edutech.team26.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResultHeader {

	boolean result;
	String message;
	
	public ResponseResultHeader(boolean result, String message) {
		this.result = result;
		this.message = message;
	}
	public ResponseResultHeader(boolean result) {
		this.result = result;
	}
}
