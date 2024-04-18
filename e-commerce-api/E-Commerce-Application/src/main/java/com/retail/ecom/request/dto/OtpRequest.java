package com.retail.ecom.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OtpRequest {

	private String email;
	private String otp;
}
