package com.app.quantitymeasurement.service;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

/**
 * Service interface
 *
 * This defines all business operations of UC17.
 *
 * Controller will call this interface. Implementation class will provide actual
 * logic.
 */
public interface IQuantityMeasurementService {

	/**
	 * Compare two quantities
	 */
	QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/**
	 * Convert one quantity to another unit
	 */
	QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/**
	 * Add two quantities
	 */
	QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/**
	 * Add two quantities and return result in target unit
	 */
	QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	/**
	 * Subtract two quantities
	 */
	QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/**
	 * Subtract two quantities and return result in target unit
	 */
	QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO);

	/**
	 * Divide two quantities
	 */
	QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/**
	 * Get history by operation
	 */
	List<QuantityMeasurementDTO> getHistoryByOperation(String operation);

	/**
	 * Get history by measurement type
	 */
	List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);

	/**
	 * Get count of successful operations
	 */
	long getOperationCount(String operation);

	/**
	 * Get all error history
	 */
	List<QuantityMeasurementDTO> getErrorHistory();
}