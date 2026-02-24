package com.apps.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            c -> c,
            c -> c,
            () -> false
    ),

    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,
            c -> (c * 9 / 5) + 32,
            () -> false
    ),

    KELVIN(
            k -> k - 273.15,
            c -> c + 273.15,
            () -> false
    );

    // Convert TO base (Celsius)
    private final Function<Double, Double> toCelsius;

    // Convert FROM base (Celsius)
    private final Function<Double, Double> fromCelsius;

    private final SupportsArithmetic supportsArithmetic;

    TemperatureUnit(Function<Double, Double> toCelsius,
                    Function<Double, Double> fromCelsius,
                    SupportsArithmetic supportsArithmetic) {

        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
        this.supportsArithmetic = supportsArithmetic;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toCelsius.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromCelsius.apply(baseValue);
    }

    @Override
    public double getConversionFactor() {
        return 1.0; // Not used for temperature
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support " + operation +
                " because arithmetic on absolute temperatures is not meaningful."
        );
    }
}