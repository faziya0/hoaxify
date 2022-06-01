package com.hoaxify.token;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.hoaxify.entity.User;

import lombok.Data;

@Entity
@Data
public class Token {
	@Id
	private String token;
	
	@ManyToOne
	private User user;

}
