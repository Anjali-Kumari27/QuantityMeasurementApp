package com.app.quantitymeasurement.userservice.auth;

/*
 * RegisterRequest DTO
 *
 * This class is used to receive user registration data.
 *
 * It contains:
 * - fullName: user's name
 * - email: user's unique email
 * - password: user's password
 *
 * This data is used to create a new user in database.
 */
import lombok.Data;

@Data
public class RegisterRequest {
	private String fullName;
	private String email;
	private String password;
}