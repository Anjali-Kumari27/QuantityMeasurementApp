package com.app.quantitymeasurement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuantityMeasurementController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuantityMeasurementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IQuantityMeasurementService service;

	@Test
	void testCompareEndpoint_ShouldReturn200() throws Exception {
		QuantityInputDTO input = new QuantityInputDTO();
		input.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
		input.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LengthUnit"));

		QuantityMeasurementDTO response = QuantityMeasurementDTO.builder().operation("compare").resultString("true")
				.build();

		when(service.compare(any(), any())).thenReturn(response);

		mockMvc.perform(post("/api/v1/quantities/compare").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))).andExpect(status().isOk())
				.andExpect(jsonPath("$.operation").value("compare"))
				.andExpect(jsonPath("$.resultString").value("true"));
	}

	@Test
	void testConvertEndpoint_ShouldReturn200() throws Exception {
		QuantityInputDTO input = new QuantityInputDTO();
		input.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
		input.setThatQuantityDTO(new QuantityDTO(0.0, "INCHES", "LengthUnit"));

		QuantityMeasurementDTO response = QuantityMeasurementDTO.builder().operation("convert").resultValue(12.0)
				.resultUnit("INCHES").build();

		when(service.convert(any(), any())).thenReturn(response);

		mockMvc.perform(post("/api/v1/quantities/convert").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))).andExpect(status().isOk())
				.andExpect(jsonPath("$.operation").value("convert")).andExpect(jsonPath("$.resultValue").value(12.0))
				.andExpect(jsonPath("$.resultUnit").value("INCHES"));
	}

	@Test
	void testAddEndpoint_ShouldReturn200() throws Exception {
		QuantityInputDTO input = new QuantityInputDTO();
		input.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
		input.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LengthUnit"));

		QuantityMeasurementDTO response = QuantityMeasurementDTO.builder().operation("add").resultValue(2.0)
				.resultUnit("FEET").build();

		when(service.add(any(), any())).thenReturn(response);

		mockMvc.perform(post("/api/v1/quantities/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))).andExpect(status().isOk())
				.andExpect(jsonPath("$.operation").value("add"));
	}

	@Test
	void testGetHistoryByOperation_ShouldReturn200() throws Exception {
		QuantityMeasurementDTO response = QuantityMeasurementDTO.builder().operation("compare").resultString("true")
				.build();

		when(service.getHistoryByOperation("compare")).thenReturn(List.of(response));

		mockMvc.perform(get("/api/v1/quantities/history/operation/compare")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].operation").value("compare"));
	}
}