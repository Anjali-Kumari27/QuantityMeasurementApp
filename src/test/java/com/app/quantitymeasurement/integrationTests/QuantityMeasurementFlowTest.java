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

/*
 * UC16: QuantityMeasurementFlowTest
 *
 * This test class verifies the complete data flow between
 * controller, service, and repository layers.
 *
 * It checks:
 * - controller to service communication
 * - service to controller response handling
 * - successful end-to-end arithmetic flow
 * - unsupported operation handling
 * - history persistence flow
 */
public class QuantityMeasurementFlowTest {

	private QuantityMeasurementController controller;
	private IQuantityMeasurementRepository repository;

	@BeforeEach
	void setUp() {
		/*
		 * Step 1: get cache repository instance Step 2: clear old history before each
		 * test Step 3: create service layer Step 4: create controller layer
		 */
		repository = QuantityMeasurementCacheRepository.getInstance();
		repository.clear();

		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
		controller = new QuantityMeasurementController(service);
	}

	@Test
	void testDataFlow_ControllerToService() {
		/*
		 * Step 1: call comparison from controller Step 2: verify result is true This
		 * confirms controller correctly delegates to service.
		 */
		boolean result = controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(result);
	}

	@Test
	void testDataFlow_ServiceToController() {
		/*
		 * Step 1: call conversion through controller Step 2: verify converted value
		 * Step 3: verify target unit name This confirms response flows correctly back
		 * from service.
		 */
		QuantityDTO result = controller.performConversion(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

		assertEquals(12.0, result.getValue(), 1e-6);
		assertEquals("INCHES", result.getUnitName());
	}

	@Test
	void testIntegration_EndToEnd_LengthAddition() {
		/*
		 * Step 1: perform addition using controller Step 2: verify final result This
		 * checks full end-to-end length addition flow.
		 */
		QuantityDTO result = controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

		assertEquals(2.0, result.getValue(), 1e-6);
	}

	@Test
	void testIntegration_EndToEnd_TemperatureUnsupported() {
		/*
		 * Step 1: try addition for temperature quantities Step 2: verify exception is
		 * thrown This confirms temperature arithmetic is blocked.
		 */
		assertThrows(QuantityMeasurementException.class,
				() -> controller.performAddition(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
						new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
						new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS)));
	}

	@Test
	void testHistoryPersistenceFlow() {
		/*
		 * Step 1: perform one comparison operation Step 2: read history from controller
		 * Step 3: verify one record is stored Step 4: verify stored operation name
		 */
		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		List<QuantityMeasurementEntity> history = controller.getHistory();

		assertEquals(1, history.size());
		assertEquals("COMPARE", history.get(0).getOperation());
	}
}