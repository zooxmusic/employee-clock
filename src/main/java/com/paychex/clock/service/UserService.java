package com.paychex.clock.service;

import com.paychex.clock.dto.UserRegistrationDto;
import com.paychex.clock.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
