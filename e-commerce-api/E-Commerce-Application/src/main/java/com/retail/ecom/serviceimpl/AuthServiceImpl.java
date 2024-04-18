package com.retail.ecom.serviceimpl;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.ecom.cache.CacheStore;
import com.retail.ecom.enums.UserRole;
import com.retail.ecom.exceptions.OTPExpiredException;
import com.retail.ecom.exceptions.OTPInvalidException;
import com.retail.ecom.exceptions.RegistrationSessionExpireException;
import com.retail.ecom.exceptions.UserAlreadyExistByEmailException;
import com.retail.ecom.mail_service.MailService;
import com.retail.ecom.mail_service.MessageModel;
import com.retail.ecom.model.Customer;
import com.retail.ecom.model.Seller;
import com.retail.ecom.model.User;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.request.dto.OtpRequest;
import com.retail.ecom.request.dto.UserRequest;
import com.retail.ecom.response.dto.UserResponse;
import com.retail.ecom.service.AuthService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private UserRepository userRepository;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure simpleResponse;
	private MailService mailService;

	@Override
	public ResponseEntity<SimpleResponseStructure> registerUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail())) 
			throw new UserAlreadyExistByEmailException("failed to register the user , user emailId already exist");
		User user = mapToChildEntity(userRequest);
		String otp = generateOTP();
		
		otpCache.add(user.getEmail(), otp);
		userCache.add(user.getEmail(), user);
		
//		return ResponseEntity.ok(otp);
		
//		send mail with otp
		try {
			sendOTP(user , otp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(simpleResponse.setStatuscode(HttpStatus.ACCEPTED.value())
				.setMessage("Verify OTP through mail to complete registration  !" + "OTP expires in 1 minute"));
		
	}
	
	
	private void sendOTP(User user, String otp) throws MessagingException {
		MessageModel model = MessageModel.builder()
		.to(user.getEmail())
		.subject("Verify Your OTP")
		.text(
				"<p>Hi , <br>"
				+ "Thanks for your intrest in E-com,"
				+ "please verify your mail id using the OTP given below.</p>"
				+ "</br>"
				+"<h1>"+otp+"</h1>"
				+"<br>"
				+ "<p> Please ignore if it's not you</p>"
				+"<br>"
				+ "with best regards"
				+ "<h3>E-Com</h3>"
				)
		.build();
		
		mailService.sendMailMessage(model);
		
	}


	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000 , 999999));
	}
	

	private <T extends User> T mapToChildEntity(UserRequest userRequest) {
		UserRole role = userRequest.getUserRole();
		
		User user = null;
		switch (role) {
		case SELLER -> user = new Seller();
		case CUSTOMER -> user = new Customer();
		default -> throw new RuntimeException();
		}
		
		user.setDisplayName(userRequest.getName());
		user.setUsername(userRequest.getEmail().split("@gmail.com") [0]);
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setUserRole(role);
		user.setEmailVerified(false);
		
		return (T) user;
	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpRequest otpRequest) {
		if(otpCache.get(otpRequest.getEmail()) == null) throw new OTPExpiredException("otp expired");
		if(!otpCache.get(otpRequest.getEmail()).equals(otpRequest.getOtp()))
			throw new OTPInvalidException("Entered OTP is invalid");
		
		User user = userCache.get(otpRequest.getEmail());
		if(user == null) throw new RegistrationSessionExpireException("registration session expired");
		user.setEmailVerified(true);
		
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setData(mapToUserResponse(userRepository.save(user)))
						.setMessage("OTP verification successfully")
						.setStatuscode(HttpStatus.CREATED.value()));
	}


	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.displayName(user.getDisplayName())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.isEmailVerified(user.isEmailVerified())
				.build();
	}
	
	

}
