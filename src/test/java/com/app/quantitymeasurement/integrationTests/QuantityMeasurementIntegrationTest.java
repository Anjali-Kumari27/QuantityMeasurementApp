package com.app.quantitymeasurement.integrationTests;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementIntegrationTest {

    @Test
    void testFullFlowComparison() {

        QuantityMeasurementController controller =
                new QuantityMeasurementController(
                        new QuantityMeasurementServiceImpl(
                                QuantityMeasurementCacheRepository.getInstance()
                        )
                );

        QuantityDTO feet = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO inches = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        boolean result = controller.performComparison(feet, inches);

        assertTrue(result);
    }
}