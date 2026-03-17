package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

/*
 * UC16: QuantityMeasurementCacheRepositoryTest
 *
 * This test class verifies the correct behavior of the
 * QuantityMeasurementCacheRepository implementation.
 *
 * It checks:
 * - saving entities
 * - retrieving stored history
 * - clearing repository
 * - append-based persistence
 * - null save protection
 */
public class QuantityMeasurementCacheRepositoryTest {

	private QuantityMeasurementCacheRepository repository;

	/*
	 * Step 1: get repository instance Step 2: clear existing data before each test
	 * This ensures test isolation.
	 */
	@BeforeEach
	void setUp() {
		repository = QuantityMeasurementCacheRepository.getInstance();
		repository.clear();
	}

	/*
	 * Step 1: create a valid entity Step 2: save it in repository Step 3: verify
	 * that one record is stored Step 4: verify stored operation
	 */
	@Test
	void testRepository_Save_Success() {
		QuantityMeasurementEntity entity = createEntity("COMPARE", 1.0, "Length", 12.0, "Length", 1.0, "FEET", "Length",
				"true");

		repository.save(entity);

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(1, history.size());
		assertEquals("COMPARE", history.get(0).getOperation());
	}

	/*
	 * Step 1: save multiple entities Step 2: retrieve all stored history Step 3:
	 * verify count is correct
	 */
	@Test
	void testRepository_FindAll_ReturnsHistory() {
		repository.save(createEntity("COMPARE", 1.0, "Length", 12.0, "Length", 1.0, "FEET", "Length", "true"));

		repository.save(createEntity("CONVERT", 1.0, "Length", 0.0, "Length", 12.0, "INCHES", "Length",
				"QuantityDTO(12.0, INCHES)"));

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(2, history.size());
	}

	/*
	 * Step 1: save one entity Step 2: clear repository Step 3: verify history
	 * becomes empty
	 */
	@Test
	void testRepository_Clear_RemovesAllData() {
		repository.save(
				createEntity("ADD", 1.0, "Length", 12.0, "Length", 2.0, "FEET", "Length", "QuantityDTO(2.0, FEET)"));

		repository.clear();

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertTrue(history.isEmpty());
	}

	/*
	 * Step 1: try saving null entity Step 2: verify repository rejects it
	 */
	@Test
	void testRepository_Save_NullEntity_Error() {
		assertThrows(IllegalArgumentException.class, () -> repository.save(null));
	}

	/*
	 * Step 1: save multiple entities one after another Step 2: verify both remain
	 * stored This confirms append-style persistence behavior.
	 */
	@Test
	void testRepository_AppendPersistence() {
		repository.save(createEntity("COMPARE", 1.0, "Length", 12.0, "Length", 1.0, "FEET", "Length", "true"));

		repository.save(createEntity("DIVIDE", 24.0, "Length", 2.0, "Length", 1.0, "FEET", "Length", "1.0"));

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(2, history.size());
	}

	/*
	 * Helper method to create a valid entity in new UC16 style.
	 */
	private QuantityMeasurementEntity createEntity(String operation, double thisValue, String thisType,
			double thatValue, String thatType, double resultValue, String resultUnit, String resultMeasurementType,
			String resultString) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation(operation);
		entity.setThisValue(thisValue);
		entity.setThisMeasurementType(thisType);
		entity.setThatValue(thatValue);
		entity.setThatMeasurementType(thatType);
		entity.setResultValue(resultValue);
		entity.setResultUnit(resultUnit);
		entity.setResultMeasurementType(resultMeasurementType);
		entity.setResultString(resultString);
		entity.setError(false);

		return entity;
	}
}