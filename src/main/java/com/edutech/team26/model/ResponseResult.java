package com.edutech.team26.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResult {

	ResponseResultHeader header;
	Object body;
	
	public ResponseResult(boolean result, String message) {
		header = new ResponseResultHeader(result, message);
	}
	public ResponseResult(boolean result) {
		header = new ResponseResultHeader(result);
	}
}
