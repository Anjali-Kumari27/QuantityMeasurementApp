package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuantityMeasurementRepositoryTest {

	@Autowired
	private QuantityMeasurementRepository repository;

	@Test
	void testFindByOperation() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(1.0, "FEET", "LengthUnit", 12.0, "INCHES",
				"LengthUnit", "compare", "true");
		repository.save(entity);

		List<QuantityMeasurementEntity> result = repository.findByOperation("compare");

		assertEquals(1, result.size());
	}

	@Test
	void testFindByErrorTrue() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(1.0, "FEET", "LengthUnit", 1.0, "KILOGRAM",
				"WeightUnit", "add", "Different measurement types", true);
		repository.save(entity);

		List<QuantityMeasurementEntity> result = repository.findByErrorTrue();

		assertFalse(result.isEmpty());
		assertTrue(result.get(0).isError());
	}
}