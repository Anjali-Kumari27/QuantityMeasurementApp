package com.app.quantitymeasurement.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Global Exception Handler
 *
 * Handles all exceptions in one place
 *
 * Why needed:
 * - clean API responses
 * - no ugly stack traces
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Custom exception handler
     */
    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<String> handleCustomException(QuantityMeasurementException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Generic exception handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong: " + ex.getMessage());
    }
}