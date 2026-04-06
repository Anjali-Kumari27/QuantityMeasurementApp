package com.app.quantitymeasurement.userservice.auth;

/*
 * AuthRequest DTO
 *
 * This class is used to receive login request data from client.
 *
 * It contains:
 * - email: user's login email
 * - password: user's password
 *
 * This data is sent from frontend/Postman to /login API.
 */
import lombok.Data;

@Data
public class AuthRequest {
	private String email;
	private String password;
}