package com.app.quantitymeasurement.userservice.controller;

/*
 * Authentication Controller
 *
 * This controller exposes public APIs:
 *
 * 1. /register
 *    - Register a new user
 *    - Returns JWT token
 *
 * 2. /login
 *    - Authenticate user
 *    - Returns JWT token
 *
 * These endpoints are public (no authentication required).
 */
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.userservice.auth.AuthRequest;
import com.app.quantitymeasurement.userservice.auth.AuthResponse;
import com.app.quantitymeasurement.userservice.auth.RegisterRequest;
import com.app.quantitymeasurement.userservice.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public AuthResponse register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		return authService.login(request);
	}
}