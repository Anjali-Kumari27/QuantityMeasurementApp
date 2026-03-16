/*
 * UC16: test-db-schema.sql
 *
 * This file defines the database schema used during automated tests.
 * It creates the required tables for the test database environment.
 *
 * Using a separate test schema ensures that tests run independently
 * without affecting the main application database.
 */

CREATE TABLE IF NOT EXISTS quantity_measurements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(50) NOT NULL,
    first_operand VARCHAR(255),
    second_operand VARCHAR(255),
    result VARCHAR(255),
    error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);