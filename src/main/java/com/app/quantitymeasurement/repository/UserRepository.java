package com.app.quantitymeasurement.repository;

/*
 * User Repository
 *
 * This interface handles database operations for User.
 *
 * It extends JpaRepository, so basic CRUD is available.
 *
 * Custom methods:
 * - findByEmail() → fetch user by email
 * - existsByEmail() → check if user already exists
 */
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.quantitymeasurement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);
}