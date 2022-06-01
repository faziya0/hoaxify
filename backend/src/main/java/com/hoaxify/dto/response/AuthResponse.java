package com.hoaxify.dto.response;

import com.hoaxify.dto.UserDto;

import lombok.Data;

@Data
public class AuthResponse {

	private String token;
	private UserDto user;
	
}
