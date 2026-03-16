package com.app.quantitymeasurement.controller;

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

/*
 * UC16: QuantityMeasurementController
 *
 * This class acts as the controller layer of the application.
 * It receives requests from the application layer and delegates
 * them to the service layer for processing.
 *
 * Responsibilities:
 * - Accept quantity measurement requests
 * - Call appropriate service methods
 * - Return results to the application layer
 *
 * The controller does not contain business logic. It only handles
 * communication between the application and service layers.
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
		 * Service returns standardized DTO: 1.0 = true 0.0 = false
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
	public QuantityDTO performAddition(QuantityDTO first, QuantityDTO second, QuantityDTO targetUnit) {
		return service.add(first, second, targetUnit);
	}

	/**
	 * Performs subtraction operation.
	 */
	public QuantityDTO performSubtraction(QuantityDTO first, QuantityDTO second, QuantityDTO targetUnit) {
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