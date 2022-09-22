package com.paychex.clock.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.paychex.clock.dto.UserRegistrationDto;
import com.paychex.clock.enums.ProfileEnum;
import com.paychex.clock.model.Role;
import com.paychex.clock.model.User;
import com.paychex.clock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = User.builder()
				.firstName(registrationDto.getFirstName())
				.lastName(registrationDto.getLastName())
				.email(registrationDto.getEmail())
				.password(passwordEncoder.encode(registrationDto.getPassword()))
				.roles(Arrays.asList(new Role(ProfileEnum.ROLE_USER.name())))
				.build();

		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
