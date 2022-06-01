package com.hoaxify.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hoaxify.entity.User;
import com.hoaxify.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUserName, String> {
 
	private final UserRepository userRepository;
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
     User user = userRepository.findByUsername(username);
     if(user!=null) {
    	 return false;
     }
     return true;

	}

	
	

}
