/*
 * UC16: test-db-schema.sql
 *
 * This file defines the database schema used during automated tests.
 * It creates the required tables for the test database environment.
 *
 * Using a separate test schema ensures that tests run independently
 * without affecting the main application database.
 */

CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    this_value DOUBLE,
    this_measurement_type VARCHAR(100),
    that_value DOUBLE,
    that_measurement_type VARCHAR(100),
    operation VARCHAR(100),
    result_value DOUBLE,
    result_unit VARCHAR(100),
    result_measurement_type VARCHAR(100),
    result_string VARCHAR(255),
    is_error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);