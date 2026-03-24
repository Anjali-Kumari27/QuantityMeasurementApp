package com.app.quantitymeasurement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuantityInputDTO
 *
 * This is the request wrapper used by controller.
 *
 * Why needed:
 * - compare needs 2 quantities
 * - add/subtract may also need target unit
 * - convert also uses source + target
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    /**
     * First input quantity
     */
    private QuantityDTO thisQuantityDTO;

    /**
     * Second input quantity
     */
    private QuantityDTO thatQuantityDTO;

    /**
     * Optional target unit for add/subtract
     */
    private QuantityDTO targetUnitDTO;
}