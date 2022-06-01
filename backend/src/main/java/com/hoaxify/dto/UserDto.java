package com.hoaxify.dto;

import com.hoaxify.entity.User;

import lombok.Data;

@Data
public class UserDto {
	private String username;
	private String displayName;
	private String image;
	public UserDto(User user) {
		this.setUsername(user.getUsername());
		this.setDisplayName(user.getDisplayName());
		this.setImage(user.getImage());
		
	}

}
