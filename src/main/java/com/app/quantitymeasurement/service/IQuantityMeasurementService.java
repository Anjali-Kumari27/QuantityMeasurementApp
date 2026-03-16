package com.app.quantitymeasurement.service;

/*
 * UC16: IQuantityMeasurementService
 *
 * This interface defines the operations supported by the
 * quantity measurement service layer.
 *
 * Responsibilities:
 * - Compare quantities
 * - Convert units
 * - Perform arithmetic operations such as addition,
 *   subtraction and division
 * - Retrieve operation history
 *
 * Implementations of this interface contain the actual
 * business logic for measurement processing.
 */

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {

	QuantityDTO compare(QuantityDTO first, QuantityDTO second);

	QuantityDTO convert(QuantityDTO source, QuantityDTO target);

	QuantityDTO add(QuantityDTO first, QuantityDTO second, QuantityDTO target);

	QuantityDTO subtract(QuantityDTO first, QuantityDTO second, QuantityDTO target);

	QuantityDTO divide(QuantityDTO first, QuantityDTO second);

	List<QuantityMeasurementEntity> getHistory();
}