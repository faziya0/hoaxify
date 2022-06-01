package com.hoaxify.service;


import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoaxify.dto.UserUpdateDto;
import com.hoaxify.entity.User;
import com.hoaxify.exception.NotFoundException;
import com.hoaxify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final FileService fileService;
	
	public void save(User user) {
		String encodedpassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedpassword);	
		userRepository.save(user);
	}
	
	public User findUserbyUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public Page<User> getUsers(Pageable pageable,User user){
		
		if(user!=null) {
			User userinDB=userRepository.findByUsername(user.getUsername());
		    Page<User> page= userRepository.findByUsernameNot(userinDB.getUsername(), pageable)	;
		    return page;
		}
		Page<User> page=userRepository.findAll(pageable);
		return page;
		
	}
	
	public User getUser(String username) {
		User user=userRepository.findByUsername(username);
		if(user==null) {
			throw new NotFoundException();
		}
		return user;
		
	}
	public User updateUser(String username,UserUpdateDto userUpdateDto) {
		User user=getUser(username);
		user.setDisplayName(userUpdateDto.getDisplayName());
		if(userUpdateDto.getImage()!=null) {
			String oldImage=user.getImage();
			try {
				String image =fileService.writebase64EncodedStringToFile(userUpdateDto.getImage());
				user.setImage(image);
			} catch (IOException e) {

				e.printStackTrace();
			}
			fileService.deleteProfileImage(oldImage);
			
		}
		userRepository.save(user);
		return user;
		
	}
	
	public void deleteUser(String username) {
		User userInDB=userRepository.findByUsername(username);
		fileService.deleteAllStoredFilesForUser(userInDB);
		userRepository.delete(userInDB);
	}
	
	
	
	


}
