package com.app.quantitymeasurement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.quantity.QuantityModel;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

/**
 * Service Implementation
 *
 * This class contains all business logic.
 *
 * Main flow: Controller -> Service -> Repository
 *
 * Important: - converts DTO into business model - performs operation - saves
 * success / error history in DB - returns response DTO
 */
@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	/**
	 * JPA repository
	 */
	@Autowired
	private QuantityMeasurementRepository repository;

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityModel<IMeasurable> firstQuantity = convertDtoToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> secondQuantity = convertDtoToModel(thatQuantityDTO);

		try {
			validateSameMeasurementType(firstQuantity.getUnit(), secondQuantity.getUnit(), "compare");

			boolean result = firstQuantity.equals(secondQuantity);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "compare", String.valueOf(result));

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "compare", e.getMessage());
			throw wrap(e, "compare");
		}
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityModel<IMeasurable> sourceQuantity = convertDtoToModel(thisQuantityDTO);
		IMeasurable targetUnit = resolveUnit(thatQuantityDTO.getMeasurementType(), thatQuantityDTO.getUnit());

		try {
			validateSameMeasurementType(sourceQuantity.getUnit(), targetUnit, "convert");

			QuantityModel<IMeasurable> result = sourceQuantity.convertTo(targetUnit);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "convert", result.getValue(),
					result.getUnit().getUnitName(), result.getUnit().getMeasurementType());

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "convert", e.getMessage());
			throw wrap(e, "convert");
		}
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityModel<IMeasurable> firstQuantity = convertDtoToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> secondQuantity = convertDtoToModel(thatQuantityDTO);

		try {
			validateSameMeasurementType(firstQuantity.getUnit(), secondQuantity.getUnit(), "add");
			firstQuantity.getUnit().validateOperationSupport("add");

			QuantityModel<IMeasurable> result = firstQuantity.add(secondQuantity);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "add", result.getValue(),
					result.getUnit().getUnitName(), result.getUnit().getMeasurementType());

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "add", e.getMessage());
			throw wrap(e, "add");
		}
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		QuantityModel<IMeasurable> firstQuantity = convertDtoToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> secondQuantity = convertDtoToModel(thatQuantityDTO);

		try {
			validateSameMeasurementType(firstQuantity.getUnit(), secondQuantity.getUnit(), "add");

			IMeasurable targetUnit = resolveUnit(targetUnitDTO.getMeasurementType(), targetUnitDTO.getUnit());
			validateSameMeasurementType(firstQuantity.getUnit(), targetUnit, "add");
			firstQuantity.getUnit().validateOperationSupport("add");

			QuantityModel<IMeasurable> result = firstQuantity.add(secondQuantity, targetUnit);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "add", result.getValue(),
					result.getUnit().getUnitName(), result.getUnit().getMeasurementType());

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "add", e.getMessage());
			throw wrap(e, "add");
		}
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityModel<IMeasurable> firstQuantity = convertDtoToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> secondQuantity = convertDtoToModel(thatQuantityDTO);

		try {
			validateSameMeasurementType(firstQuantity.getUnit(), secondQuantity.getUnit(), "subtract");
			firstQuantity.getUnit().validateOperationSupport("subtract");

			QuantityModel<IMeasurable> result = firstQuantity.subtract(secondQuantity);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "subtract", result.getValue(),
					result.getUnit().getUnitName(), result.getUnit().getMeasurementType());

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "subtract", e.getMessage());
			throw wrap(e, "subtract");
		}
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		QuantityModel<IMeasurable> firstQuantity = convertDtoToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> secondQuantity = convertDtoToModel(thatQuantityDTO);

		try {
			validateSameMeasurementType(firstQuantity.getUnit(), secondQuantity.getUnit(), "subtract");

			IMeasurable targetUnit = resolveUnit(targetUnitDTO.getMeasurementType(), targetUnitDTO.getUnit());
			validateSameMeasurementType(firstQuantity.getUnit(), targetUnit, "subtract");
			firstQuantity.getUnit().validateOperationSupport("subtract");

			QuantityModel<IMeasurable> result = firstQuantity.subtract(secondQuantity, targetUnit);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "subtract", result.getValue(),
					result.getUnit().getUnitName(), result.getUnit().getMeasurementType());

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "subtract", e.getMessage());
			throw wrap(e, "subtract");
		}
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityModel<IMeasurable> firstQuantity = convertDtoToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> secondQuantity = convertDtoToModel(thatQuantityDTO);

		try {
			validateSameMeasurementType(firstQuantity.getUnit(), secondQuantity.getUnit(), "divide");
			firstQuantity.getUnit().validateOperationSupport("divide");

			double result = firstQuantity.divide(secondQuantity);

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.getValue(),
					thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType(), thatQuantityDTO.getValue(),
					thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType(), "divide", result, "RATIO",
					"Dimensionless");

			repository.save(entity);
			return QuantityMeasurementDTO.fromEntity(entity);

		} catch (Exception e) {
			saveError(thisQuantityDTO, thatQuantityDTO, "divide", e.getMessage());
			throw wrap(e, "divide");
		}
	}

	@Override
	public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
		return QuantityMeasurementDTO.fromEntities(repository.findByOperation(operation));
	}

	@Override
	public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType) {
		return QuantityMeasurementDTO.fromEntities(repository.findByThisMeasurementType(measurementType));
	}

	@Override
	public long getOperationCount(String operation) {
		return repository.countByOperationAndErrorFalse(operation);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return QuantityMeasurementDTO.fromEntities(repository.findByErrorTrue());
	}

	/**
	 * Convert input DTO into business model
	 */
	private QuantityModel<IMeasurable> convertDtoToModel(QuantityDTO dto) {
		return new QuantityModel<>(dto.getValue(), resolveUnit(dto.getMeasurementType(), dto.getUnit()));
	}

	/**
	 * Convert measurementType + unit string into actual enum
	 */
	private IMeasurable resolveUnit(String measurementType, String unit) {
		return switch (measurementType) {
		case "LengthUnit" -> LengthUnit.valueOf(unit);
		case "WeightUnit" -> WeightUnit.valueOf(unit);
		case "VolumeUnit" -> VolumeUnit.valueOf(unit);
		case "TemperatureUnit" -> TemperatureUnit.valueOf(unit);
		default -> throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
		};
	}

	/**
	 * Validate same measurement type before operation
	 */
	private void validateSameMeasurementType(IMeasurable first, IMeasurable second, String operation) {
		if (!first.getMeasurementType().equals(second.getMeasurementType())) {
			throw new QuantityMeasurementException(
					operation + " error: Cannot operate on different measurement categories");
		}
	}

	/**
	 * Save failed operation in DB
	 */
	private void saveError(QuantityDTO first, QuantityDTO second, String operation, String errorMessage) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first == null ? null : first.getValue(),
				first == null ? null : first.getUnit(), first == null ? null : first.getMeasurementType(),
				second == null ? null : second.getValue(), second == null ? null : second.getUnit(),
				second == null ? null : second.getMeasurementType(), operation, errorMessage, true);

		repository.save(entity);
	}

	/**
	 * Wrap normal exception into custom exception
	 */
	private QuantityMeasurementException wrap(Exception e, String operation) {
		if (e instanceof QuantityMeasurementException qme) {
			return qme;
		}

		return new QuantityMeasurementException(operation + " error: " + e.getMessage(), e);
	}
}