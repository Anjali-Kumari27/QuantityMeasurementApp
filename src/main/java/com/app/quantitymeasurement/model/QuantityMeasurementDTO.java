package com.app.quantitymeasurement.model;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*;

/**
 * Response DTO
 *
 * This is what API returns
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDTO {

    private String operation;

    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String resultString;

    private boolean isError;

    /**
     * Convert Entity → DTO
     */
    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        return QuantityMeasurementDTO.builder()
                .operation(entity.getOperation())
                .resultValue(entity.getResultValue())
                .resultUnit(entity.getResultUnit())
                .resultMeasurementType(entity.getResultMeasurementType())
                .resultString(entity.getResultString())
                .isError(entity.isError())
                .build();
    }

    /**
     * Convert List<Entity> → List<DTO>
     */
    public static List<QuantityMeasurementDTO> fromEntities(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }
}