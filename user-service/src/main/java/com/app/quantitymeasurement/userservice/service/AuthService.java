package com.app.quantitymeasurement.userservice.service;

/*
 * Authentication Service
 *
 * This class contains business logic for authentication.
 *
 * Responsibilities:
 * 1. Register new user
 *    - Check if email already exists
 *    - Encrypt password using BCrypt
 *    - Save user in database
 *    - Generate JWT token
 *
 * 2. Login existing user
 *    - Authenticate using email & password
 *    - Generate JWT token
 */
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.userservice.auth.AuthRequest;
import com.app.quantitymeasurement.userservice.auth.AuthResponse;
import com.app.quantitymeasurement.userservice.auth.RegisterRequest;
import com.app.quantitymeasurement.userservice.model.User;
import com.app.quantitymeasurement.userservice.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService customUserDetailsService;
	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService,
			JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtService = jwtService;
	}

	public AuthResponse register(RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("User already exists with this email");
		}

		User user = User.builder().fullName(request.getFullName()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role("ROLE_USER").build();

		userRepository.save(user);

		var userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		String token = jwtService.generateToken(userDetails);

		return new AuthResponse(token);
	}

	public AuthResponse login(AuthRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		var userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
		String token = jwtService.generateToken(userDetails);

		return new AuthResponse(token);
	}
}