package com.yara.fieldservice.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yara.fieldservice.model.Field;
import com.yara.fieldservice.service.FieldService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 * The Class FieldControllerTest.
 */
@WebMvcTest(controllers =  FieldController.class)
public class FieldControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private FieldService fieldService;
	
	
	private List<Field> fieldList;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		this.fieldList = new ArrayList<>();
		fieldList.add(new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Potato field", new Date(), "", "DEU", null));
		fieldList.add(new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Tomato field", new Date(), "", "IND", null));
	}
	
	/**
	 * Should fetch all fields.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldFetchAllFields() throws Exception{
		given(fieldService.findAllFields()).willReturn(fieldList);
		this.mockMvc.perform(get("/fields")).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(fieldList.size())));
	}

	/**
	 * Should fetch field by id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldFetchFieldById() throws Exception{
		String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		final Field field = new Field(_id, "Potato field", new Date(), "", "DEU", null);
		given(fieldService.findFieldbyId(_id)).willReturn(Optional.of(field));

		this.mockMvc.perform(get("/fields/{id}", _id)).andExpect(status().isOk()).
		andExpect(jsonPath("$.countryCode", is(field.getCountryCode()))).andExpect(jsonPath("$.name", is(field.getName())));
	}
	
	/**
	 * Should return 404 when fetch field by id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn404WhenFetchFieldById() throws Exception{
		String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		given(fieldService.findFieldbyId(_id)).willReturn(Optional.empty());

		this.mockMvc.perform(get("/fields/{id}", _id)).andExpect(status().isNotFound());
	}
	
	/**
	 * Should create new field.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCreateNewField() throws Exception{
		final Field field = new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Potato field", new Date(), "", "DEU", null);
		given(fieldService.createField(field)).willAnswer(invocation -> invocation.getArgument(0));

		this.mockMvc.perform(post("/fields").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(field))).andExpect(status().isCreated());
	}
	
	/**
	 * Should update field.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldUpdateField() throws Exception{
		String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		final Field field = new Field(_id, "Potato field", new Date(), "", "DEU", null);
		given(fieldService.createField(field)).willAnswer(invocation -> invocation.getArgument(0));
		field.setName("Potato field");
		given(fieldService.updateField(field)).willAnswer(invocation -> invocation.getArgument(0));

		this.mockMvc.perform(put("/fields").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(field))).andExpect(status().is2xxSuccessful()).
				andExpect(status().isNoContent());
	}
	
	
	/**
	 * Should delete field.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteField() throws Exception{
		String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		final Field field = new Field(_id, "Potato field", new Date(), "", "DEU", null);
		given(fieldService.findFieldbyId(_id)).willReturn(Optional.of(field));
		doNothing().when(fieldService).deleteFieldById(_id);
		
		this.mockMvc.perform(delete("/fields/{id}", _id)).andExpect(status().isOk());
	}
	
	/**
	 * Should return weather successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturnWeatherSuccessfully() throws Exception{
		String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		ReflectionTestUtils.setField(fieldService, "weatherEndpoint", "https://samples.openweathermap.org/agro/1.0/weather/history?polyid=%s&start=1485703465&end=1485780512&&appid=bb0664ed43c153aa072c760594d775a7");		
		this.mockMvc.perform(get("/fields/{id}/weather", _id)).andExpect(status().isOk());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
