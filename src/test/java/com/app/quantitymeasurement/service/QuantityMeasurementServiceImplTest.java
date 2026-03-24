package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

class QuantityMeasurementServiceImplTest {

	private QuantityMeasurementRepository repository;
	private QuantityMeasurementServiceImpl service;

	@BeforeEach
	void setUp() throws Exception {
		repository = mock(QuantityMeasurementRepository.class);
		service = new QuantityMeasurementServiceImpl();

		// inject mock repo
		Field field = QuantityMeasurementServiceImpl.class.getDeclaredField("repository");
		field.setAccessible(true);
		field.set(service, repository);
	}

	@Test
	void testCompare_ShouldReturnTrue() {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

		var result = service.compare(q1, q2);

		assertEquals("compare", result.getOperation());
		assertEquals("true", result.getResultString());
	}

	@Test
	void testConvert_ShouldConvertCorrectly() {
		QuantityDTO source = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

		var result = service.convert(source, target);

		assertEquals(12.0, result.getResultValue());
		assertEquals("INCHES", result.getResultUnit());
	}

	@Test
	void testAdd_ShouldAddCorrectly() {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

		var result = service.add(q1, q2);

		assertEquals(2.0, result.getResultValue());
	}

	@Test
	void testSubtract_ShouldWorkCorrectly() {
		QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

		var result = service.subtract(q1, q2);

		assertEquals(1.0, result.getResultValue());
	}

	@Test
	void testDivide_ShouldReturnRatio() {
		QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LengthUnit");

		var result = service.divide(q1, q2);

		assertEquals(2.0, result.getResultValue());
	}

	@Test
	void testAdd_Temperature_ShouldThrowException() {
		QuantityDTO q1 = new QuantityDTO(10.0, "CELSIUS", "TemperatureUnit");
		QuantityDTO q2 = new QuantityDTO(20.0, "CELSIUS", "TemperatureUnit");

		assertThrows(QuantityMeasurementException.class, () -> service.add(q1, q2));
	}
}