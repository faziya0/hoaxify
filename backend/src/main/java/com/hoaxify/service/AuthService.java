package com.hoaxify.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoaxify.dto.UserDto;
import com.hoaxify.dto.request.Credentials;
import com.hoaxify.dto.response.AuthResponse;
import com.hoaxify.entity.User;
import com.hoaxify.exception.AuthException;
import com.hoaxify.token.Token;
import com.hoaxify.token.TokenRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final TokenRepository tokenRepository;
	
	public AuthResponse authenticate(Credentials credentials) {
		User userInDB=userService.findUserbyUsername(credentials.getUsername());
		if(userInDB==null) {
			throw new AuthException();
		}
		boolean matches=passwordEncoder.matches(credentials.getPassword(), userInDB.getPassword());
		if(!matches) {
			throw new AuthException();
		}
		
		String token= generateRandomName();
		Token tokenObject = new Token();
		tokenObject.setToken(token);
		tokenObject.setUser(userInDB);
		tokenRepository.save(tokenObject);
		UserDto userDto = new UserDto(userInDB);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setToken(token);
		authResponse.setUser(userDto);
		return authResponse;
	}
	
	public String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	@Transactional
	public UserDetails getUserDetails(String token) {
		Optional<Token> tokenOptional=tokenRepository.findById(token);
		if(!tokenOptional.isPresent()) {
			return null;
		}
		User user = tokenOptional.get().getUser();
		return user;
		
	}
	

	public void clearToken(String token) {
		tokenRepository.deleteById(token);
		
	}

}
