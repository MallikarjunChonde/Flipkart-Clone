package com.retail.ecom.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.request.dto.OtpRequest;
import com.retail.ecom.request.dto.UserRequest;
import com.retail.ecom.response.dto.UserResponse;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

public interface AuthService {

	ResponseEntity<SimpleResponseStructure> registerUser(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpRequest otpRequest);

}
