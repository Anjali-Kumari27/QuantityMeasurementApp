package com.app.quantitymeasurement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * UC16: QuantityMeasurementEntity
 *
 * This entity represents a quantity measurement operation record
 * that is stored in the database.
 *
 * It keeps the input values, input measurement types, operation name,
 * result details, error information, and timestamps.
 */
public class QuantityMeasurementEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private double thisValue;
	private String thisMeasurementType;

	private double thatValue;
	private String thatMeasurementType;

	private String operation;

	private double resultValue;
	private String resultUnit;
	private String resultMeasurementType;

	private String resultString;

	private boolean error;
	private String errorMessage;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public QuantityMeasurementEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getThisValue() {
		return thisValue;
	}

	public void setThisValue(double thisValue) {
		this.thisValue = thisValue;
	}

	public String getThisMeasurementType() {
		return thisMeasurementType;
	}

	public void setThisMeasurementType(String thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public double getThatValue() {
		return thatValue;
	}

	public void setThatValue(double thatValue) {
		this.thatValue = thatValue;
	}

	public String getThatMeasurementType() {
		return thatMeasurementType;
	}

	public void setThatMeasurementType(String thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public double getResultValue() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public String getResultMeasurementType() {
		return resultMeasurementType;
	}

	public void setResultMeasurementType(String resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		if (error) {
			return "[ERROR] operation=" + operation + ", thisValue=" + thisValue + ", thisMeasurementType="
					+ thisMeasurementType + ", thatValue=" + thatValue + ", thatMeasurementType=" + thatMeasurementType
					+ ", errorMessage=" + errorMessage;
		}

		return "[SUCCESS] operation=" + operation + ", thisValue=" + thisValue + ", thisMeasurementType="
				+ thisMeasurementType + ", thatValue=" + thatValue + ", thatMeasurementType=" + thatMeasurementType
				+ ", resultValue=" + resultValue + ", resultUnit=" + resultUnit + ", resultMeasurementType="
				+ resultMeasurementType + ", resultString=" + resultString;
	}
}