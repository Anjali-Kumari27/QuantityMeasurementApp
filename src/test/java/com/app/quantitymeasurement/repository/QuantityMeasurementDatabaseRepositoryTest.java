package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

/*
 * UC16: QuantityMeasurementDatabaseRepositoryTest
 *
 * This test class verifies the correct behavior of the
 * QuantityMeasurementDatabaseRepository implementation.
 *
 * It checks:
 * - saving entity in database
 * - retrieving all stored measurements
 * - counting stored records
 * - deleting all records
 *
 * These tests run on the H2 test database.
 */
public class QuantityMeasurementDatabaseRepositoryTest {

	private QuantityMeasurementDatabaseRepository repository;
	private QuantityMeasurementEntity testEntity;

	@BeforeAll
	static void setUpDatabase() {
		/*
		 * Step 1: set application environment to test This helps load test
		 * configuration if needed.
		 */
		System.setProperty("app.env", "test");
	}

	@BeforeEach
	void setUp() {
		/*
		 * Step 1: get singleton repository instance Step 2: clear all rows before each
		 * test Step 3: create a reusable test entity
		 */
		repository = QuantityMeasurementDatabaseRepository.getInstance();
		repository.deleteAll();
		createTestEntity();
	}

	@AfterEach
	void tearDown() {
		/*
		 * Step 1: clean database after each test This keeps tests isolated from one
		 * another.
		 */
		repository.deleteAll();
	}

	@Test
	void testSaveEntity() {
		/*
		 * Step 1: save one entity in database Step 2: retrieve all stored measurements
		 * Step 3: verify one record exists Step 4: verify operation value
		 */
		repository.save(testEntity);

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(1, history.size());
		assertEquals("COMPARE", history.get(0).getOperation());
	}

	@Test
	void testGetAllMeasurements() {
		/*
		 * Step 1: save first entity Step 2: save second entity Step 3: retrieve all
		 * stored records Step 4: verify total count is 2
		 */
		repository.save(testEntity);
		repository.save(createTestEntityCopy(24.0));

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(2, history.size());
	}

	@Test
	void testGetTotalCount() {
		/*
		 * Step 1: save three records Step 2: call total count method Step 3: verify
		 * total count is correct
		 */
		repository.save(testEntity);
		repository.save(createTestEntityCopy(24.0));
		repository.save(createTestEntityCopy(36.0));

		int count = repository.getTotalCount();

		assertEquals(3, count);
	}

	@Test
	void testDeleteAll() {
		/*
		 * Step 1: save two records Step 2: delete all records Step 3: retrieve history
		 * Step 4: verify no records remain
		 */
		repository.save(testEntity);
		repository.save(createTestEntityCopy(24.0));

		repository.deleteAll();

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(0, history.size());
	}

	/*
	 * Helper method to create a reusable base test entity.
	 */
	private void createTestEntity() {
		testEntity = new QuantityMeasurementEntity();

		testEntity.setOperation("COMPARE");
		testEntity.setThisValue(1.0);
		testEntity.setThisMeasurementType("Length");
		testEntity.setThatValue(12.0);
		testEntity.setThatMeasurementType("Length");
		testEntity.setResultValue(1.0);
		testEntity.setResultUnit("FEET");
		testEntity.setResultMeasurementType("Length");
		testEntity.setResultString("true");
		testEntity.setError(false);
	}

	/*
	 * Helper method to create similar test entities with different input values.
	 */
	private QuantityMeasurementEntity createTestEntityCopy(double value) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation("COMPARE");
		entity.setThisValue(value);
		entity.setThisMeasurementType("Length");
		entity.setThatValue(12.0);
		entity.setThatMeasurementType("Length");
		entity.setResultValue(1.0);
		entity.setResultUnit("FEET");
		entity.setResultMeasurementType("Length");
		entity.setResultString("true");
		entity.setError(false);

		return entity;
	}
}