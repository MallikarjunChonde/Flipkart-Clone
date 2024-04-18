package com.retail.ecom.request.dto;

import com.retail.ecom.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRequest {
	private String name;
	private String email;
    private String password;
    private UserRole userRole;
}
