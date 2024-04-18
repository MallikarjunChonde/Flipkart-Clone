package com.retail.ecom.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class UserAlreadyExistByEmailException extends RuntimeException {
	
	private String message;

}
