package com.app.quantitymeasurement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * UC15: QuantityMeasurementEntity is the persistence model used to store
 * quantity operation history in the repository layer.
 *
 * Responsibilities: 
 * - Stores details of operations such as compare, convert, add, subtract, divide 
 * - Captures operands, result, error state, and timestamp
 * - Provides a serializable record for repository persistence
 *
 * Why Entity is Needed: 
 * - Maintains operation audit/history 
 * - Supports logging and debugging 
 * - Allows persistence across application restarts
 *
 * Architectural Role: This class is part of the Entity/Persistence layer and
 * should remain separate from DTO and internal model classes.
 *
 * Design Considerations: - Serializable for disk storage 
 * - Immutable-style constructor initialization 
 * - Clear distinction between success and error
 * states
 */

public class QuantityMeasurementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String operationType;
    private final String firstOperand;
    private final String secondOperand;
    private final String result;
    private final boolean error;
    private final String errorMessage;
    private final LocalDateTime timestamp;

    public QuantityMeasurementEntity(String operationType, String firstOperand, String result) {
        this(operationType, firstOperand, null, result, false, null);
    }

    public QuantityMeasurementEntity(String operationType, String firstOperand, String secondOperand, String result) {
        this(operationType, firstOperand, secondOperand, result, false, null);
    }

    public QuantityMeasurementEntity(String operationType,
                                     String firstOperand,
                                     String secondOperand,
                                     String result,
                                     boolean error,
                                     String errorMessage) {
        this.operationType = operationType;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.result = result;
        this.error = error;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    public String getOperationType() {
        return operationType;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public String getResult() {
        return result;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        if (error) {
            return "[ERROR] time=" + timestamp
                    + ", operation=" + operationType
                    + ", first=" + firstOperand
                    + ", second=" + secondOperand
                    + ", message=" + errorMessage;
        }

        return "[SUCCESS] time=" + timestamp
                + ", operation=" + operationType
                + ", first=" + firstOperand
                + ", second=" + secondOperand
                + ", result=" + result;
    }
}