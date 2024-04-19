package com.retail.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.request.dto.OtpRequest;
import com.retail.ecom.request.dto.UserRequest;
import com.retail.ecom.response.dto.UserResponse;
import com.retail.ecom.service.AuthService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/ecom/v1")
@AllArgsConstructor
public class AuthController {
	
	private AuthService authService;
	
	@PostMapping("/register")
	ResponseEntity<SimpleResponseStructure> registerUSer(@RequestBody UserRequest userRequest) {
		return authService.registerUser(userRequest);
	}

	@PostMapping("/verify-email")
	ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(@RequestBody OtpRequest otpRequest) {
		return authService.verifyOtp(otpRequest);
		
	}
}
