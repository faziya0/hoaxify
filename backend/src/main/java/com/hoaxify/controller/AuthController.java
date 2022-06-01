package com.hoaxify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.dto.request.Credentials;
import com.hoaxify.dto.response.GenericResponse;
import com.hoaxify.service.AuthService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("api/1.0/auth")
	public ResponseEntity<?> auth(@RequestBody Credentials credentials){
		return ResponseEntity.ok(authService.authenticate(credentials));
		
	}
	
	@PostMapping("api/1.0/logout")
	public ResponseEntity<?> logout(@RequestHeader(name="Authorization") String authorization){
		String token = authorization.substring(7);
		authService.clearToken(token);
		return ResponseEntity.ok(new GenericResponse("Logout success"));
		
		
	}

}
