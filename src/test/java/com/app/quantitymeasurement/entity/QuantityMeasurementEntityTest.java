package com.app.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 * UC16: QuantityMeasurementEntityTest
 *
 * This test class verifies the behavior of QuantityMeasurementEntity.
 *
 * It checks:
 * - default construction
 * - setter and getter behavior
 * - success entity values
 * - error entity values
 * - string representation for success and error cases
 */
public class QuantityMeasurementEntityTest {

	@Test
	void testQuantityEntity_DefaultConstruction() {
		/*
		 * Step 1: create entity with default constructor Step 2: verify object is
		 * created successfully
		 */
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		assertNotNull(entity);
	}

	@Test
	void testQuantityEntity_SuccessValues() {
		/*
		 * Step 1: create entity Step 2: set values for a successful operation Step 3:
		 * verify values through getters
		 */
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation("CONVERT");
		entity.setThisValue(1.0);
		entity.setThisMeasurementType("Length");
		entity.setThatValue(12.0);
		entity.setThatMeasurementType("Length");
		entity.setResultValue(12.0);
		entity.setResultUnit("INCHES");
		entity.setResultMeasurementType("Length");
		entity.setResultString("QuantityDTO(12.0, INCHES)");
		entity.setError(false);

		assertEquals("CONVERT", entity.getOperation());
		assertEquals(1.0, entity.getThisValue());
		assertEquals("Length", entity.getThisMeasurementType());
		assertEquals(12.0, entity.getThatValue());
		assertEquals("Length", entity.getThatMeasurementType());
		assertEquals(12.0, entity.getResultValue());
		assertEquals("INCHES", entity.getResultUnit());
		assertEquals("Length", entity.getResultMeasurementType());
		assertEquals("QuantityDTO(12.0, INCHES)", entity.getResultString());
		assertFalse(entity.isError());
	}

	@Test
	void testQuantityEntity_BinaryOperationValues() {
		/*
		 * Step 1: create entity for binary operation Step 2: set add operation details
		 * Step 3: verify getters
		 */
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation("ADD");
		entity.setThisValue(1.0);
		entity.setThisMeasurementType("Length");
		entity.setThatValue(12.0);
		entity.setThatMeasurementType("Length");
		entity.setResultValue(2.0);
		entity.setResultUnit("FEET");
		entity.setResultMeasurementType("Length");
		entity.setResultString("QuantityDTO(2.0, FEET)");
		entity.setError(false);

		assertEquals("ADD", entity.getOperation());
		assertEquals(1.0, entity.getThisValue());
		assertEquals("Length", entity.getThisMeasurementType());
		assertEquals(12.0, entity.getThatValue());
		assertEquals("Length", entity.getThatMeasurementType());
		assertEquals(2.0, entity.getResultValue());
		assertEquals("FEET", entity.getResultUnit());
		assertEquals("Length", entity.getResultMeasurementType());
		assertEquals("QuantityDTO(2.0, FEET)", entity.getResultString());
		assertFalse(entity.isError());
	}

	@Test
	void testQuantityEntity_ErrorValues() {
		/*
		 * Step 1: create entity for failed operation Step 2: set error values Step 3:
		 * verify error data
		 */
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation("ADD");
		entity.setThisValue(0.0);
		entity.setThisMeasurementType("Temperature");
		entity.setThatValue(32.0);
		entity.setThatMeasurementType("Temperature");
		entity.setError(true);
		entity.setErrorMessage("Temperature does not support arithmetic operations");

		assertEquals("ADD", entity.getOperation());
		assertTrue(entity.isError());
		assertEquals("Temperature does not support arithmetic operations", entity.getErrorMessage());
	}

	@Test
	void testQuantityEntity_ToString_Success() {
		/*
		 * Step 1: create successful entity Step 2: call toString Step 3: verify
		 * important values are included
		 */
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation("COMPARE");
		entity.setThisValue(1.0);
		entity.setThisMeasurementType("Length");
		entity.setThatValue(12.0);
		entity.setThatMeasurementType("Length");
		entity.setResultValue(1.0);
		entity.setResultUnit("FEET");
		entity.setResultMeasurementType("Length");
		entity.setResultString("QuantityDTO(1.0, FEET)");
		entity.setError(false);

		String text = entity.toString();

		assertTrue(text.contains("SUCCESS"));
		assertTrue(text.contains("COMPARE"));
		assertTrue(text.contains("Length"));
		assertTrue(text.contains("QuantityDTO(1.0, FEET)"));
	}

	@Test
	void testQuantityEntity_ToString_Error() {
		/*
		 * Step 1: create error entity Step 2: call toString Step 3: verify error
		 * information appears
		 */
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation("DIVIDE");
		entity.setThisValue(10.0);
		entity.setThisMeasurementType("Length");
		entity.setThatValue(0.0);
		entity.setThatMeasurementType("Length");
		entity.setError(true);
		entity.setErrorMessage("Cannot divide by zero");

		String text = entity.toString();

		assertTrue(text.contains("ERROR"));
		assertTrue(text.contains("DIVIDE"));
		assertTrue(text.contains("Cannot divide by zero"));
	}
}