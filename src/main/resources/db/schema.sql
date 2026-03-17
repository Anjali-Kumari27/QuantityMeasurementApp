/*
 * UC16: schema.sql
 *
 * This file defines the database schema required by the application.
 * It creates the table used to store quantity measurement operations.
 *
 * The table stores information such as:
 * - operation type (compare, add, convert, etc.)
 * - input operands
 * - result of the operation
 * - error details (if any)
 * - timestamp of execution
 *
 * The schema is automatically executed when the database is initialized.
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