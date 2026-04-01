package com.app.quantitymeasurement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

/**
 * REST Controller
 *
 * This class handles API requests.
 *
 * Endpoints:
 * - compare
 * - convert
 * - add
 * - subtract
 * - divide
 * - history
 */
@RestController
@RequestMapping("/api/v1/quantities")
@CrossOrigin(origins = "http://localhost:5173")
public class QuantityMeasurementController {

    /**
     * Service layer dependency
     */
    @Autowired
    private IQuantityMeasurementService service;

    /**
     * Compare endpoint
     */
    @PostMapping("/compare")
    public QuantityMeasurementDTO compare(@RequestBody QuantityInputDTO input) {
        return service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    /**
     * Convert endpoint
     */
    @PostMapping("/convert")
    public QuantityMeasurementDTO convert(@RequestBody QuantityInputDTO input) {
        return service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    /**
     * Add endpoint
     *
     * If target unit is present, use overloaded add method
     */
    @PostMapping("/add")
    public QuantityMeasurementDTO add(@RequestBody QuantityInputDTO input) {
        if (input.getTargetUnitDTO() != null) {
            return service.add(
                    input.getThisQuantityDTO(),
                    input.getThatQuantityDTO(),
                    input.getTargetUnitDTO()
            );
        }

        return service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    /**
     * Subtract endpoint
     *
     * If target unit is present, use overloaded subtract method
     */
    @PostMapping("/subtract")
    public QuantityMeasurementDTO subtract(@RequestBody QuantityInputDTO input) {
        if (input.getTargetUnitDTO() != null) {
            return service.subtract(
                    input.getThisQuantityDTO(),
                    input.getThatQuantityDTO(),
                    input.getTargetUnitDTO()
            );
        }

        return service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    /**
     * Divide endpoint
     */
    @PostMapping("/divide")
    public QuantityMeasurementDTO divide(@RequestBody QuantityInputDTO input) {
        return service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    /**
     * Get history by operation
     */
    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementDTO> getHistoryByOperation(@PathVariable String operation) {
        return service.getHistoryByOperation(operation);
    }

    /**
     * Get history by measurement type
     */
    @GetMapping("/history/measurementType/{measurementType}")
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(@PathVariable String measurementType) {
        return service.getHistoryByMeasurementType(measurementType);
    }

    /**
     * Get successful operation count
     */
    @GetMapping("/count/{operation}")
    public long getOperationCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }

    /**
     * Get all failed operations
     */
    @GetMapping("/history/errors")
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return service.getErrorHistory();
    }
}