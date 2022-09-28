package com.paychex.clock.service;

import com.paychex.clock.dto.UserRegistrationDto;
import com.paychex.clock.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/*
 * I am using to demonstrate that I understand programming to an interface but given reflectively generated classes
 * I find if I am sure we do not need multiple implementations I would rather annotate the impl class and forgo defining
 * an explicit interface. If there could be multiple implementations then I can see the value although that can also
 * be injected at runtime with a configuration
 * */


public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
