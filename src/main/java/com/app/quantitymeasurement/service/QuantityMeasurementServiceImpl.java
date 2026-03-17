package com.app.quantitymeasurement.service;

/*
 * UC16: QuantityMeasurementServiceImpl
 *
 * This class implements the service layer and contains the
 * core business logic for quantity measurement operations.
 *
 * Responsibilities:
 * - Convert DTO objects into internal models
 * - Perform unit conversions
 * - Validate measurement types
 * - Execute arithmetic operations
 * - Store operation history through the repository layer
 *
 * The service layer ensures separation between business
 * logic and data persistence.
 */

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private final IQuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Repository cannot be null");
		}
		this.repository = repository;
	}

	@Override
	public QuantityDTO compare(QuantityDTO first, QuantityDTO second) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			Quantity<IMeasurable> firstQuantity = toQuantity(first);
			Quantity<IMeasurable> secondQuantity = toQuantity(second);

			boolean result = firstQuantity.equals(secondQuantity);

			QuantityDTO response = new QuantityDTO(result ? 1.0 : 0.0, first.getUnit());

			repository.save(buildEntity("COMPARE", first, second, response, false, null));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("COMPARE", first, second, e);
		}
	}

	@Override
	public QuantityDTO convert(QuantityDTO source, QuantityDTO target) {
		validateNotNull(source, target);
		validateSameMeasurementType(source, target);

		try {
			Quantity<IMeasurable> sourceQuantity = toQuantity(source);
			IMeasurable targetUnit = mapDtoUnitToApplicationUnit(target);

			Quantity<IMeasurable> result = sourceQuantity.convertTo(targetUnit);
			QuantityDTO response = toDTO(result);

			repository.save(buildEntity("CONVERT", source, target, response, false, null));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("CONVERT", source, target, e);
		}
	}

	@Override
	public QuantityDTO add(QuantityDTO first, QuantityDTO second, QuantityDTO target) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			Quantity<IMeasurable> firstQuantity = toQuantity(first);
			Quantity<IMeasurable> secondQuantity = toQuantity(second);

			validateArithmeticSupported(firstQuantity.getUnit());

			Quantity<IMeasurable> result;
			if (target == null) {
				result = firstQuantity.add(secondQuantity);
			} else {
				validateSameMeasurementType(first, target);
				result = firstQuantity.add(secondQuantity, mapDtoUnitToApplicationUnit(target));
			}

			QuantityDTO response = toDTO(result);

			repository.save(buildEntity("ADD", first, second, response, false, null));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("ADD", first, second, e);
		}
	}

	@Override
	public QuantityDTO subtract(QuantityDTO first, QuantityDTO second, QuantityDTO target) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			Quantity<IMeasurable> firstQuantity = toQuantity(first);
			Quantity<IMeasurable> secondQuantity = toQuantity(second);

			validateArithmeticSupported(firstQuantity.getUnit());

			Quantity<IMeasurable> result;
			if (target == null) {
				result = firstQuantity.subtract(secondQuantity);
			} else {
				validateSameMeasurementType(first, target);
				result = firstQuantity.subtract(secondQuantity, mapDtoUnitToApplicationUnit(target));
			}

			QuantityDTO response = toDTO(result);

			repository.save(buildEntity("SUBTRACT", first, second, response, false, null));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("SUBTRACT", first, second, e);
		}
	}

	@Override
	public QuantityDTO divide(QuantityDTO first, QuantityDTO second) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			Quantity<IMeasurable> firstQuantity = toQuantity(first);
			Quantity<IMeasurable> secondQuantity = toQuantity(second);

			validateArithmeticSupported(firstQuantity.getUnit());

			double result = firstQuantity.divide(secondQuantity);

			QuantityDTO response = new QuantityDTO(result, first.getUnit());

			repository.save(buildEntity("DIVIDE", first, second, response, false, null));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("DIVIDE", first, second, e);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getHistory() {
		return repository.findAll();
	}

	private QuantityModel<IMeasurable> toModel(QuantityDTO dto) {
		IMeasurable mappedUnit = mapDtoUnitToApplicationUnit(dto);
		return new QuantityModel<>(dto.getValue(), mappedUnit);
	}

	private Quantity<IMeasurable> toQuantity(QuantityDTO dto) {
		QuantityModel<IMeasurable> model = toModel(dto);
		return new Quantity<>(model.getValue(), model.getUnit());
	}

	private QuantityDTO toDTO(Quantity<IMeasurable> quantity) {
		QuantityDTO.IMeasurableUnit dtoUnit = mapApplicationUnitToDtoUnit(quantity.getUnit());
		return new QuantityDTO(quantity.getValue(), dtoUnit);
	}

	private IMeasurable mapDtoUnitToApplicationUnit(QuantityDTO dto) {
		String measurementType = dto.getMeasurementType();
		String unitName = dto.getUnitName();

		switch (measurementType) {
		case "Length":
			return LengthUnit.valueOf(unitName.toUpperCase());
		case "Weight":
			return WeightUnit.valueOf(unitName.toUpperCase());
		case "Volume":
			return VolumeUnit.valueOf(unitName.toUpperCase());
		case "Temperature":
			return TemperatureUnit.valueOf(unitName.toUpperCase());
		default:
			throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
		}
	}

	private QuantityDTO.IMeasurableUnit mapApplicationUnitToDtoUnit(IMeasurable unit) {
		String measurementType = unit.getMeasurementType();
		String unitName = unit.getUnitName();

		switch (measurementType) {
		case "Length":
			return QuantityDTO.LengthUnit.valueOf(unitName.toUpperCase());
		case "Weight":
			return QuantityDTO.WeightUnit.valueOf(unitName.toUpperCase());
		case "Volume":
			return QuantityDTO.VolumeUnit.valueOf(unitName.toUpperCase());
		case "Temperature":
			return QuantityDTO.TemperatureUnit.valueOf(unitName.toUpperCase());
		default:
			throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
		}
	}

	private void validateNotNull(QuantityDTO first, QuantityDTO second) {
		if (first == null || second == null) {
			throw new QuantityMeasurementException("QuantityDTO cannot be null");
		}
	}

	private void validateSameMeasurementType(QuantityDTO first, QuantityDTO second) {
		if (!first.getMeasurementType().equals(second.getMeasurementType())) {
			throw new QuantityMeasurementException("Different measurement types are not allowed");
		}
	}

	private void validateArithmeticSupported(IMeasurable unit) {
		if ("Temperature".equals(unit.getMeasurementType())) {
			throw new QuantityMeasurementException(
					unit.getMeasurementType() + " does not support arithmetic operations");
		}
	}

	private QuantityMeasurementException saveAndWrap(String operation, QuantityDTO first, QuantityDTO second,
			Exception e) {
		repository.save(buildEntity(operation, first, second, null, true, e.getMessage()));
		return new QuantityMeasurementException(e.getMessage(), e);
	}

	private QuantityMeasurementEntity buildEntity(String operation, QuantityDTO first, QuantityDTO second,
			QuantityDTO result, boolean isError, String errorMessage) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setOperation(operation);

		if (first != null) {
			entity.setThisValue(first.getValue());
			entity.setThisMeasurementType(first.getMeasurementType());
		}

		if (second != null) {
			entity.setThatValue(second.getValue());
			entity.setThatMeasurementType(second.getMeasurementType());
		}

		if (result != null) {
			entity.setResultValue(result.getValue());
			entity.setResultUnit(result.getUnitName());
			entity.setResultMeasurementType(result.getMeasurementType());
			entity.setResultString(result.toString());
		}

		entity.setError(isError);
		entity.setErrorMessage(errorMessage);

		return entity;
	}
}