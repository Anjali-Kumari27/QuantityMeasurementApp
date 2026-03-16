package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementDatabaseRepositoryTest {

	private QuantityMeasurementDatabaseRepository repository;
	private QuantityMeasurementEntity testEntity;

	@BeforeAll
	static void setUpDatabase() {

		// Set test environment
		System.setProperty("app.env", "test");
	}

	@BeforeEach
	void setUp() {

		repository = QuantityMeasurementDatabaseRepository.getInstance();

		// Clean database before each test
		repository.deleteAll();

		createTestEntity();
	}

	@AfterEach
	void tearDown() {
		repository.deleteAll();
	}

	@Test
	void testSaveEntity() {

		repository.save(testEntity);

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(1, history.size());
		assertEquals("COMPARE", history.get(0).getOperationType());
	}

	@Test
	void testGetAllMeasurements() {

		repository.save(testEntity);
		repository.save(createTestEntityCopy(24.0));

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(2, history.size());
	}

	@Test
	void testGetTotalCount() {

		repository.save(testEntity);
		repository.save(createTestEntityCopy(24.0));
		repository.save(createTestEntityCopy(36.0));

		int count = repository.getTotalCount();

		assertEquals(3, count);
	}

	@Test
	void testDeleteAll() {

		repository.save(testEntity);
		repository.save(createTestEntityCopy(24.0));

		repository.deleteAll();

		List<QuantityMeasurementEntity> history = repository.findAll();

		assertEquals(0, history.size());
	}

	private void createTestEntity() {

		testEntity = new QuantityMeasurementEntity("COMPARE", "QuantityDTO(1.0, FEET)", "QuantityDTO(12.0, INCHES)",
				"true");
	}

	private QuantityMeasurementEntity createTestEntityCopy(double value) {

		return new QuantityMeasurementEntity("COMPARE", "QuantityDTO(" + value + ", INCHES)",
				"QuantityDTO(12.0, INCHES)", "true");
	}
}