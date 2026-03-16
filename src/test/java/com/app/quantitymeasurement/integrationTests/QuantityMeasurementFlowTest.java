package com.app.quantitymeasurement.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementFlowTest {

	private QuantityMeasurementController controller;
	private IQuantityMeasurementRepository repository;

	@BeforeEach
	void setUp() {
		repository = QuantityMeasurementCacheRepository.getInstance();
		repository.clear();

		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
		controller = new QuantityMeasurementController(service);
	}

	@Test
	void testDataFlow_ControllerToService() {
		boolean result = controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(result);
	}

	@Test
	void testDataFlow_ServiceToController() {
		QuantityDTO result = controller.performConversion(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

		assertEquals(12.0, result.getValue(), 1e-6);
		assertEquals("INCHES", result.getUnitName());
	}

	@Test
	void testIntegration_EndToEnd_LengthAddition() {
		QuantityDTO result = controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

		assertEquals(2.0, result.getValue(), 1e-6);
	}

	@Test
	void testIntegration_EndToEnd_TemperatureUnsupported() {
		assertThrows(QuantityMeasurementException.class,
				() -> controller.performAddition(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
						new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
						new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS)));
	}

	@Test
	void testHistoryPersistenceFlow() {
		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		List<QuantityMeasurementEntity> history = controller.getHistory();

		assertEquals(1, history.size());
		assertEquals("COMPARE", history.get(0).getOperationType());
	}
}