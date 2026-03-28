package com.app.quantitymeasurement.model;

/*
 * User Entity
 *
 * This class represents user data stored in database.
 *
 * Fields:
 * - id: unique identifier
 * - fullName: user's name
 * - email: unique login identifier
 * - password: encrypted password
 * - role: USER or ADMIN
 *
 * This is mapped to "users" table in database.
 */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;

	@Column(unique = true, nullable = false)
	private String email;

	private String password;

	private String role;
}