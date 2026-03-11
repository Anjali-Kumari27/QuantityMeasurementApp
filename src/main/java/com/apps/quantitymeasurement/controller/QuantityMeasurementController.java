package com.apps.quantitymeasurement.controller;

import java.util.List;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

/**
 * UC15: QuantityMeasurementController acts as the entry point
 * between user interaction and the service layer.
 *
 * Responsibilities:
 * - Accept QuantityDTO input from the application layer
 * - Perform minimal validation
 * - Delegate business logic to the service layer
 * - Return standardized DTO responses
 *
 * Architectural Role:
 * Controller Layer in N-Tier architecture.
 *
 * Important Rule:
 * This class must contain NO business logic.
 * All calculations are delegated to the service layer.
 */
public class QuantityMeasurementController {

	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(IQuantityMeasurementService service) {
		if (service == null) {
			throw new IllegalArgumentException("Service cannot be null");
		}
		this.service = service;
	}

	/**
	 * Performs equality comparison between two quantities.
	 */
	public boolean performComparison(QuantityDTO first, QuantityDTO second) {
		QuantityDTO result = service.compare(first, second);

		/*
		 * Service returns standardized DTO:
		 * 1.0 = true
		 * 0.0 = false
		 */
		return result.getValue() == 1.0;
	}

	/**
	 * Performs unit conversion.
	 */
	public QuantityDTO performConversion(QuantityDTO source, QuantityDTO target) {
		return service.convert(source, target);
	}

	/**
	 * Performs addition operation.
	 */
	public QuantityDTO performAddition(
			QuantityDTO first,
			QuantityDTO second,
			QuantityDTO targetUnit
	) {
		return service.add(first, second, targetUnit);
	}

	/**
	 * Performs subtraction operation.
	 */
	public QuantityDTO performSubtraction(
			QuantityDTO first,
			QuantityDTO second,
			QuantityDTO targetUnit
	) {
		return service.subtract(first, second, targetUnit);
	}

	/**
	 * Performs division operation.
	 */
	public double performDivision(QuantityDTO first, QuantityDTO second) {
		QuantityDTO result = service.divide(first, second);
		return result.getValue();
	}

	/**
	 * Returns repository operation history.
	 */
	public List<QuantityMeasurementEntity> getHistory() {
		return service.getHistory();
	}
}