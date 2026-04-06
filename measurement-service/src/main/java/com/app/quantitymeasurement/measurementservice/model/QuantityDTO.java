package com.app.quantitymeasurement.measurementservice.model;

import lombok.*;

/**
 * DTO (Data Transfer Object)
 *
 * Used to transfer data between:
 * Controller ↔ Service
 *
 * UC16 → direct objects
 * UC17 → DTO-based architecture
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {

    /**
     * Value of quantity
     */
    private Double value;

    /**
     * Unit name (FEET, KG, etc)
     */
    private String unit;

    /**
     * Measurement type (LengthUnit, WeightUnit, etc)
     */
    private String measurementType;

    /**
     * Important:
     * If value is null → return 0.0 (your test expects this)
     */
    public Double getValue() {
        return value == null ? 0.0 : value;
    }
}