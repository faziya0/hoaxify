package com.hoaxify.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.hoaxify.entity.Hoax;
import com.hoaxify.entity.User;
import com.hoaxify.repository.HoaxRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoaxSecurityService {

	private final HoaxRepository hoaxRepository;
	public boolean isAllowedDelete(int id, User user) {
		Optional<Hoax> optionalHoax=hoaxRepository.findById(id);
		if(!optionalHoax.isPresent()) {
			return false;
		}
		Hoax hoaxDB=optionalHoax.get();
		if(!(hoaxDB.getUser().getUsername().equals(user.getUsername()))) {
			return false;
		}
		
		
		
		return true;
		
	}

}
