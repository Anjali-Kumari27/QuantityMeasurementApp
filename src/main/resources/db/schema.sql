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