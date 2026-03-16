package com.app.quantitymeasurement.exception;
/*
 * UC16: DatabaseException
 *
 * This is a custom exception used to represent database related errors
 * in the Quantity Measurement application.
 *
 * Responsibilities:
 * - Wrap SQL and database access errors
 * - Provide meaningful error messages for debugging
 * - Separate database errors from business logic errors
 *
 * This improves error handling and keeps database exceptions consistent
 * throughout the application.
 */
public class DatabaseException extends RuntimeException {

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}