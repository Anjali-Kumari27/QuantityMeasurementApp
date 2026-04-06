package com.app.quantitymeasurement.userservice.auth;

/*
 * AuthResponse DTO
 *
 * This class is used to send response after successful login/register.
 *
 * It contains:
 * - token: JWT token generated after authentication
 *
 * Client will use this token for accessing secured APIs.
 */
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	private String token;
}