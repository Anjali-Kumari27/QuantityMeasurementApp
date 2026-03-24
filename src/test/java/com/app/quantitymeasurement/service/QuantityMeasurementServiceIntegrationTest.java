package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

@SpringBootTest
class QuantityMeasurementServiceIntegrationTest {

	@Autowired
	private IQuantityMeasurementService service;

	@Test
	void testCompare_Integration() {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

		QuantityMeasurementDTO result = service.compare(q1, q2);

		assertEquals("true", result.getResultString());
	}

	@Test
	void testConvert_Integration() {
		QuantityDTO source = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

		QuantityMeasurementDTO result = service.convert(source, target);

		assertEquals(12.0, result.getResultValue());
	}

	@Test
	void testAdd_Integration() {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

		QuantityMeasurementDTO result = service.add(q1, q2);

		assertEquals(2.0, result.getResultValue());
	}

	@Test
	void testHistory_Integration() {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

		service.compare(q1, q2);

		List<QuantityMeasurementDTO> history = service.getHistoryByOperation("compare");

		assertFalse(history.isEmpty());
	}

	@Test
	void testErrorHistory_Integration() {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");

		try {
			service.compare(q1, q2);
		} catch (Exception ignored) {
		}

		List<QuantityMeasurementDTO> errors = service.getErrorHistory();

		assertFalse(errors.isEmpty());
	}
}