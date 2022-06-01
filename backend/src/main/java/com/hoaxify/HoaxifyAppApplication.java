package com.hoaxify;
import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hoaxify.dto.HoaxSubmitDto;
import com.hoaxify.entity.User;
import com.hoaxify.service.HoaxService;
import com.hoaxify.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@RequiredArgsConstructor
public class HoaxifyAppApplication {
	private final UserService userService;
	private final HoaxService hoaxService;
	@PostConstruct
	public void init() {
		for(int i = 1; i<=25;i++) {				
			User user = new User();
			user.setUsername("user"+i);
			user.setDisplayName("display"+i);
			user.setPassword("P4ssword");
			userService.save(user);
			for(int j = 1;j<=20;j++) {
				HoaxSubmitDto hoax = new HoaxSubmitDto();
				hoax.setContent("hoax (" +j + ") from user ("+i+")");
				hoaxService.saveHoax(hoax, user);
			}
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HoaxifyAppApplication.class, args);
	}

}
