package com.apps.quantitymeasurement.service;

import java.util.List;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {

	QuantityDTO compare(QuantityDTO first, QuantityDTO second);

	QuantityDTO convert(QuantityDTO source, QuantityDTO target);

	QuantityDTO add(QuantityDTO first, QuantityDTO second, QuantityDTO target);

	QuantityDTO subtract(QuantityDTO first, QuantityDTO second, QuantityDTO target);

	QuantityDTO divide(QuantityDTO first, QuantityDTO second);

	List<QuantityMeasurementEntity> getHistory();
}