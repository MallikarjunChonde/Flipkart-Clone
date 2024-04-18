package com.retail.ecom.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class SimpleResponseStructure {

	private int statuscode;
	private String message;
	
	public SimpleResponseStructure setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}
	
	public SimpleResponseStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	
}
