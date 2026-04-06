package com.app.quantitymeasurement.measurementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.quantitymeasurement.measurementservice.model.QuantityMeasurementEntity;

/**
 * Repository interface for QuantityMeasurementEntity
 *
 * Spring Data JPA creates implementation automatically.
 */
@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

	/**
	 * Get history by operation
	 */
	List<QuantityMeasurementEntity> findByOperation(String operation);

	/**
	 * Get history by measurement type
	 */
	List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

	/**
	 * Count only successful operations
	 *
	 * Works only if entity field name is 'error'
	 */
	long countByOperationAndErrorFalse(String operation);

	/**
	 * Get only failed operations
	 */
	List<QuantityMeasurementEntity> findByErrorTrue();
}