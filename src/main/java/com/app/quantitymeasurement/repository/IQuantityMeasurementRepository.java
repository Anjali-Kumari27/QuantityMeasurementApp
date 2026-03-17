package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

/*
 * UC16: IQuantityMeasurementRepository
 *
 * This interface defines repository operations for storing
 * and retrieving quantity measurement history.
 */
public interface IQuantityMeasurementRepository {

	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> getAllMeasurements();

	List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

	List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

	int getTotalCount();

	void deleteAll();

	String getPoolStatistics();

	void releaseResources();

	/*
	 * Compatibility methods for older service/controller code.
	 */
	default List<QuantityMeasurementEntity> findAll() {
		return getAllMeasurements();
	}

	default void clear() {
		deleteAll();
	}
}