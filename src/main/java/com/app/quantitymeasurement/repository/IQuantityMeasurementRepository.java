package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

/**
 * UC15: IQuantityMeasurementRepository defines the contract for the repository
 * layer responsible for persistence of quantity measurement operations.
 *
 * Responsibilities: - Store measurement operation records - Retrieve stored
 * operation history - Clear persisted records when needed
 *
 * Architectural Role: This interface belongs to the Repository Layer in the
 * N-Tier architecture. It abstracts the persistence mechanism from the service
 * layer.
 *
 * Why Interface: - Enables loose coupling between service and repository -
 * Allows future repository implementations (database, cache, cloud storage) -
 * Improves testability through mock repositories
 *
 * Current Implementation: QuantityMeasurementCacheRepository
 *
 * Future Implementations (Possible): - DatabaseRepository (JDBC) -
 * RedisCacheRepository - CloudStorageRepository
 */
public interface IQuantityMeasurementRepository {

	/**
	 * Saves a quantity measurement operation entity.
	 *
	 * @param entity operation record to persist
	 */
	void save(QuantityMeasurementEntity entity);

	/**
	 * Returns all stored measurement history.
	 *
	 * @return list of stored measurement entities
	 */
	List<QuantityMeasurementEntity> findAll();

	/**
	 * Clears repository history and removes persisted data.
	 */
	void clear();
}