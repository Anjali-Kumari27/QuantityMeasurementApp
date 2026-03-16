package com.apps.quantitymeasurement.dto;

/**
 * UC15: QuantityDTO is a Data Transfer Object used for transferring quantity
 * measurement data between controller and service layers.
 *
 * This DTO intentionally does NOT use the application's IMeasurable interface.
 * Instead it defines its own internal unit interface (IMeasurableUnit) to
 * decouple external representation from internal business logic.
 *
 * Architectural Purpose: - Standardizes data transfer between layers - Prevents
 * service layer implementation leakage - Enables easier REST API integration
 * later
 */
public class QuantityDTO {

	private final double value;
	private final IMeasurableUnit unit;

	public QuantityDTO(double value, IMeasurableUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}

		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public IMeasurableUnit getUnit() {
		return unit;
	}

	public String getUnitName() {
		return unit.getUnitName();
	}

	public String getMeasurementType() {
		return unit.getMeasurementType();
	}

	@Override
	public String toString() {
		return "QuantityDTO(" + value + ", " + unit.getUnitName() + ")";
	}

	/**
	 * UC15: DTO-specific measurable unit abstraction. This is intentionally
	 * different from the application's IMeasurable interface used in the service
	 * layer.
	 */
	public interface IMeasurableUnit {
		String getUnitName();

		String getMeasurementType();
	}

	// ================= LENGTH =================

	public enum LengthUnit implements IMeasurableUnit {

		FEET, INCHES, YARDS, CENTIMETERS;

		@Override
		public String getUnitName() {
			return name();
		}

		@Override
		public String getMeasurementType() {
			return "Length";
		}
	}

	// ================= WEIGHT =================

	public enum WeightUnit implements IMeasurableUnit {

		KILOGRAM, GRAM, POUND;

		@Override
		public String getUnitName() {
			return name();
		}

		@Override
		public String getMeasurementType() {
			return "Weight";
		}
	}

	// ================= VOLUME =================

	public enum VolumeUnit implements IMeasurableUnit {

		LITRE, MILLILITRE, GALLON;

		@Override
		public String getUnitName() {
			return name();
		}

		@Override
		public String getMeasurementType() {
			return "Volume";
		}
	}

	// ================= TEMPERATURE =================

	public enum TemperatureUnit implements IMeasurableUnit {

		CELSIUS, FAHRENHEIT, KELVIN;

		@Override
		public String getUnitName() {
			return name();
		}

		@Override
		public String getMeasurementType() {
			return "Temperature";
		}
	}
}