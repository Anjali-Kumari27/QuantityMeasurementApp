package com.app.quantitymeasurement.userservice.service;

/*
 * Custom UserDetailsService
 *
 * This class is used by Spring Security to load user details.
 *
 * Steps:
 * 1. Find user by email from database
 * 2. If not found, throw exception
 * 3. Convert user into Spring Security UserDetails object
 * 4. Provide username, password, and roles
 *
 * Used internally during authentication.
 */

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.userservice.model.User;
import com.app.quantitymeasurement.userservice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole())));
	}
}