package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;

/*
 * UC16: QuantityMeasurementServiceImplTest
 *
 * This test class verifies service-layer business logic.
 *
 * It checks:
 * - comparison logic
 * - conversion logic
 * - arithmetic operations
 * - invalid scenarios (errors)
 *
 * A fake in-memory repository is used for isolation.
 */
public class QuantityMeasurementServiceImplTest {

	private static final double EPS = 1e-6;

	private QuantityMeasurementServiceImpl service;
	private TestRepository repository;

	@BeforeEach
	void setUp() {
		/*
		 * Step 1: initialize fake repository Step 2: inject into service
		 */
		repository = new TestRepository();
		service = new QuantityMeasurementServiceImpl(repository);
	}

	@Test
	void testService_CompareEquality_SameUnit_Success() {
		QuantityDTO result = service.compare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET));

		assertEquals(1.0, result.getValue(), EPS);
	}

	@Test
	void testService_CompareEquality_DifferentUnit_Success() {
		QuantityDTO result = service.compare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		assertEquals(1.0, result.getValue(), EPS);
	}

	@Test
	void testService_CompareEquality_CrossCategory_Error() {
		assertThrows(QuantityMeasurementException.class,
				() -> service.compare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
						new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM)));
	}

	@Test
	void testService_Convert_Success() {
		QuantityDTO result = service.convert(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

		assertEquals(12.0, result.getValue(), EPS);
		assertEquals("INCHES", result.getUnitName());
	}

	@Test
	void testService_Add_Success() {
		QuantityDTO result = service.add(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

		assertEquals(2.0, result.getValue(), EPS);
	}

	@Test
	void testService_Add_UnsupportedOperation_Error() {
		assertThrows(QuantityMeasurementException.class,
				() -> service.add(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
						new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
						new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS)));
	}

	@Test
	void testService_Subtract_Success() {
		QuantityDTO result = service.subtract(new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM),
				new QuantityDTO(0.0, QuantityDTO.WeightUnit.KILOGRAM));

		assertEquals(1.5, result.getValue(), EPS);
	}

	@Test
	void testService_Divide_Success() {
		QuantityDTO result = service.divide(new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

		assertEquals(1.0, result.getValue(), EPS);
	}

	@Test
	void testService_Divide_ByZero_Error() {
		assertThrows(QuantityMeasurementException.class,
				() -> service.divide(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
						new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET)));
	}

	/*
	 * Fake repository implementation for testing service layer.
	 *
	 * Only minimal logic is implemented.
	 */
	private static class TestRepository implements IQuantityMeasurementRepository {

		private final List<QuantityMeasurementEntity> data = new ArrayList<>();

		@Override
		public void save(QuantityMeasurementEntity entity) {
			data.add(entity);
		}

		@Override
		public List<QuantityMeasurementEntity> getAllMeasurements() {
			return data;
		}

		@Override
		public void deleteAll() {
			data.clear();
		}

		@Override
		public int getTotalCount() {
			return data.size();
		}

		@Override
		public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
			return data;
		}

		@Override
		public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
			return data;
		}

		@Override
		public String getPoolStatistics() {
			return "Test repository";
		}

		@Override
		public void releaseResources() {
			// nothing to release
		}

		/*
		 * Compatibility methods (optional but safe)
		 */
		@Override
		public List<QuantityMeasurementEntity> findAll() {
			return getAllMeasurements();
		}

		@Override
		public void clear() {
			deleteAll();
		}
	}
}