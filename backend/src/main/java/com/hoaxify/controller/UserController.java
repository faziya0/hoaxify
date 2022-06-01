package com.hoaxify.controller;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.constraint.CurrentUser;
import com.hoaxify.dto.UserUpdateDto;
import com.hoaxify.dto.response.GenericResponse;
import com.hoaxify.dto.UserDto;
import com.hoaxify.entity.User;
import com.hoaxify.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

private final UserService userService;

	
@PostMapping("api/1.0/users")
	public ResponseEntity<GenericResponse> createUser(@Valid @RequestBody User user) {
	userService.save(user);
	return ResponseEntity.ok(new GenericResponse("user is added"));

		
	}

    @GetMapping("api/1.0/users")
   public Page<UserDto> getUsers(Pageable pageable,@CurrentUser User user){
    return userService.getUsers(pageable,user).map(UserDto::new);
    };
    
    @GetMapping("api/1.0/users/{username}") 
    	public UserDto getUser(@PathVariable String username) {
    	User user=userService.getUser(username);
    	UserDto userVM = new UserDto(user);
    	return userVM;
    }
    
    @PutMapping("api/1.0/users/{username}") 
    @PreAuthorize("#username == principal.username")
	public UserDto updateUser(@Valid @RequestBody UserUpdateDto userUpdateVM, @PathVariable String username) {
	User user=userService.updateUser(username, userUpdateVM);
	UserDto userVM = new UserDto(user);
	return userVM;
}
    @DeleteMapping("api/1.0/users/{username}")
	@PreAuthorize("#username==principal.username")
	public ResponseEntity<?> deleteUser(@PathVariable String username){
		userService.deleteUser(username);
		return ResponseEntity.ok(new GenericResponse("user is removed"));
		
	}
    
   
    
   }



