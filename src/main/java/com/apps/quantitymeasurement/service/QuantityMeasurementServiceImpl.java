package com.apps.quantitymeasurement.service;

import java.util.List;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.interfaces.IMeasurable;
import com.apps.quantitymeasurement.interfaces.SupportsArithmetic;
import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.units.LengthUnit;
import com.apps.quantitymeasurement.units.TemperatureUnit;
import com.apps.quantitymeasurement.units.VolumeUnit;
import com.apps.quantitymeasurement.units.WeightUnit;

/**
 * UC15: QuantityMeasurementServiceImpl contains the core business logic for
 * quantity measurement operations in the Service Layer.
 *
 * Responsibilities: - Accept QuantityDTO input from controller - Convert DTO
 * into internal QuantityModel - Perform validation and business logic - Execute
 * comparison, conversion, addition, subtraction, and division - Save operation
 * history in repository - Return standardized QuantityDTO responses
 *
 * Important UC15 Rule: This service layer should contain the actual business
 * logic. It should NOT depend on old monolithic business classes for core
 * processing.
 */
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private final IQuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Repository cannot be null");
		}
		this.repository = repository;
	}

	/**
	 * Compares two quantities after converting both to base unit.
	 *
	 * Returns standardized DTO: - 1.0 means equal - 0.0 means not equal
	 */
	@Override
	public QuantityDTO compare(QuantityDTO first, QuantityDTO second) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			QuantityModel<IMeasurable> firstModel = toModel(first);
			QuantityModel<IMeasurable> secondModel = toModel(second);

			double firstBase = toBaseValue(firstModel);
			double secondBase = toBaseValue(secondModel);

			// Small tolerance used for floating-point comparison
			boolean result = Math.abs(firstBase - secondBase) < 0.0001;

			repository.save(new QuantityMeasurementEntity("COMPARE", first.toString(), second.toString(),
					String.valueOf(result)));

			return new QuantityDTO(result ? 1.0 : 0.0, first.getUnit());

		} catch (Exception e) {
			throw saveAndWrap("COMPARE", first, second, e);
		}
	}

	/**
	 * Converts source quantity into target unit.
	 */
	@Override
	public QuantityDTO convert(QuantityDTO source, QuantityDTO target) {
		validateNotNull(source, target);
		validateSameMeasurementType(source, target);

		try {
			QuantityModel<IMeasurable> sourceModel = toModel(source);
			QuantityModel<IMeasurable> targetModel = toModel(target);

			double baseValue = toBaseValue(sourceModel);
			double convertedValue = targetModel.getUnit().fromBase(baseValue);

			QuantityDTO response = new QuantityDTO(convertedValue, mapApplicationUnitToDtoUnit(targetModel.getUnit()));

			repository.save(new QuantityMeasurementEntity("CONVERT", source.toString(), response.toString()));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("CONVERT", source, target, e);
		}
	}

	/**
	 * Adds two quantities.
	 *
	 * If target is null, result is returned in first quantity's unit. Otherwise,
	 * result is returned in target unit.
	 */
	@Override
	public QuantityDTO add(QuantityDTO first, QuantityDTO second, QuantityDTO target) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			QuantityModel<IMeasurable> firstModel = toModel(first);
			QuantityModel<IMeasurable> secondModel = toModel(second);

			validateArithmeticSupported(firstModel.getUnit());

			double firstBase = toBaseValue(firstModel);
			double secondBase = toBaseValue(secondModel);

			double resultBase = firstBase + secondBase;

			IMeasurable resultUnit = resolveResultUnit(firstModel, target, first);
			double resultValue = resultUnit.fromBase(resultBase);

			QuantityDTO response = new QuantityDTO(resultValue, mapApplicationUnitToDtoUnit(resultUnit));

			repository.save(
					new QuantityMeasurementEntity("ADD", first.toString(), second.toString(), response.toString()));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("ADD", first, second, e);
		}
	}

	/**
	 * Subtracts second quantity from first quantity.
	 *
	 * If target is null, result is returned in first quantity's unit. Otherwise,
	 * result is returned in target unit.
	 */
	@Override
	public QuantityDTO subtract(QuantityDTO first, QuantityDTO second, QuantityDTO target) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			QuantityModel<IMeasurable> firstModel = toModel(first);
			QuantityModel<IMeasurable> secondModel = toModel(second);

			validateArithmeticSupported(firstModel.getUnit());

			double firstBase = toBaseValue(firstModel);
			double secondBase = toBaseValue(secondModel);

			double resultBase = firstBase - secondBase;

			IMeasurable resultUnit = resolveResultUnit(firstModel, target, first);
			double resultValue = resultUnit.fromBase(resultBase);

			QuantityDTO response = new QuantityDTO(resultValue, mapApplicationUnitToDtoUnit(resultUnit));

			repository.save(new QuantityMeasurementEntity("SUBTRACT", first.toString(), second.toString(),
					response.toString()));

			return response;

		} catch (Exception e) {
			throw saveAndWrap("SUBTRACT", first, second, e);
		}
	}

	/**
	 * Divides first quantity by second quantity.
	 *
	 * Result is a scalar value, but UC15 keeps standardized DTO flow, so scalar is
	 * wrapped into QuantityDTO using first unit.
	 */
	@Override
	public QuantityDTO divide(QuantityDTO first, QuantityDTO second) {
		validateNotNull(first, second);
		validateSameMeasurementType(first, second);

		try {
			QuantityModel<IMeasurable> firstModel = toModel(first);
			QuantityModel<IMeasurable> secondModel = toModel(second);

			validateArithmeticSupported(firstModel.getUnit());

			double firstBase = toBaseValue(firstModel);
			double secondBase = toBaseValue(secondModel);

			if (Math.abs(secondBase) < 0.0000001) {
				throw new QuantityMeasurementException("Division by zero is not allowed");
			}

			double result = firstBase / secondBase;

			repository.save(new QuantityMeasurementEntity("DIVIDE", first.toString(), second.toString(),
					String.valueOf(result)));

			return new QuantityDTO(result, first.getUnit());

		} catch (Exception e) {
			throw saveAndWrap("DIVIDE", first, second, e);
		}
	}

	/**
	 * Returns full operation history from repository.
	 */
	@Override
	public List<QuantityMeasurementEntity> getHistory() {
		return repository.findAll();
	}

	/**
	 * Converts external DTO into internal service-layer model.
	 */
	private QuantityModel<IMeasurable> toModel(QuantityDTO dto) {
		IMeasurable mappedUnit = mapDtoUnitToApplicationUnit(dto);
		return new QuantityModel<>(dto.getValue(), mappedUnit);
	}

	/**
	 * Converts internal model value into base unit using the model's unit.
	 */
	private double toBaseValue(QuantityModel<IMeasurable> model) {
		return model.getUnit().toBase(model.getValue());
	}

	/**
	 * Resolves the unit in which result should be returned.
	 *
	 * Rule: - If target is provided -> use target unit - If target is null -> use
	 * first quantity's unit
	 */
	private IMeasurable resolveResultUnit(QuantityModel<IMeasurable> firstModel, QuantityDTO target,
			QuantityDTO first) {
		if (target == null) {
			return firstModel.getUnit();
		}

		validateSameMeasurementType(first, target);
		return mapDtoUnitToApplicationUnit(target);
	}

	/**
	 * Maps DTO unit representation to internal application unit.
	 */
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

	/**
	 * Maps internal application unit to DTO unit representation.
	 */
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

	/**
	 * Validates that required DTO inputs are not null.
	 */
	private void validateNotNull(QuantityDTO first, QuantityDTO second) {
		if (first == null || second == null) {
			throw new QuantityMeasurementException("QuantityDTO cannot be null");
		}
	}

	/**
	 * Validates that both quantities belong to the same measurement type. Example:
	 * Length with Length is allowed, Length with Weight is not allowed.
	 */
	private void validateSameMeasurementType(QuantityDTO first, QuantityDTO second) {
		if (!first.getMeasurementType().equals(second.getMeasurementType())) {
			throw new QuantityMeasurementException("Different measurement types are not allowed");
		}
	}

	/**
	 * Validates that arithmetic operations are supported by the unit category.
	 * Example: Length/Weight/Volume may support arithmetic, Temperature usually
	 * should not.
	 */
	private void validateArithmeticSupported(IMeasurable unit) {
		if (!(unit instanceof SupportsArithmetic)) {
			throw new QuantityMeasurementException(
					unit.getMeasurementType() + " does not support arithmetic operations");
		}
	}

	/**
	 * Saves failed operation into repository and wraps it in a custom exception.
	 */
	private QuantityMeasurementException saveAndWrap(String operation, QuantityDTO first, QuantityDTO second,
			Exception e) {
		repository.save(new QuantityMeasurementEntity(operation, first != null ? first.toString() : null,
				second != null ? second.toString() : null, null, true, e.getMessage()));

		return new QuantityMeasurementException(e.getMessage(), e);
	}
}