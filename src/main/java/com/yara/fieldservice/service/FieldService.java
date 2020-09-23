package com.yara.fieldservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yara.fieldservice.exception.FieldNotFoundException;
import com.yara.fieldservice.exception.WeatherNotFoundException;
import com.yara.fieldservice.model.Field;
import com.yara.fieldservice.repository.FieldRepository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * The Class FieldService.
 */
@Service
public class FieldService {
	
	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(FieldService.class);
	
	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;
	
	/** The field repository. */
	@Autowired
	private FieldRepository fieldRepository;
	
	
	/** The weather endpoint. */
	@Value("${weather.endpoint}")
	private String weatherEndpoint;
	
	/**
	 * Creates the field.
	 *
	 * @param field the field
	 * @return the string
	 */
	public String createField(Field field) {
		Field fieldResponse = fieldRepository.save(field);
		return fieldResponse.getId();
	}
	
	/**
	 * Find fieldby id.
	 *
	 * @param fieldId the field id
	 * @return the optional
	 */
	public Optional<Field> findFieldbyId(String fieldId){
		return fieldRepository.findById(fieldId);
	}
	
	
	/**
	 * Delete field by id.
	 *
	 * @param fieldId the field id
	 */
	public void deleteFieldById(String fieldId) {
		fieldRepository.deleteById(fieldId);
	}
	
	/**
	 * Find all fields.
	 *
	 * @return the list
	 */
	public List<Field> findAllFields(){
		// Pagination needs to be implemented for better performance
		return fieldRepository.findAll();
	}
	
	/**
	 * Update field.
	 *
	 * @param field the field
	 * @return the field
	 */
	public Field updateField(Field field) {
		Optional<Field> fieldResponse = fieldRepository.findById(field.getId());
		fieldResponse.orElseThrow(() -> new FieldNotFoundException(field.getId()));
		return fieldRepository.save(field);
	}
	
	/**
	 * Fetch weather info.
	 *
	 * @param fieldId the field id
	 * @return the JSON object
	 */
	@SuppressWarnings("unchecked")
	public JSONObject fetchWeatherInfo(String fieldId) {
		logger.debug("calling weather service api {} ", fieldId);
		JSONArray responseArray = new JSONArray();
		JSONObject response = new JSONObject();
		try {
			
			final String url = String.format(weatherEndpoint, fieldId);
			String apiResponse = restTemplate.getForObject(url, String.class);
			JSONArray weatherArray = JSONArray.fromObject(apiResponse);
			weatherArray.forEach( item -> {
				JSONObject json = (JSONObject) item;
				JSONObject values = new JSONObject();
				values.put("timestamp", json.getString("dt"));
				values.put("temperature", json.getJSONObject("main").get("temp"));
				values.put("humidity", json.getJSONObject("main").get("humidity"));
				values.put("temperaturMax", json.getJSONObject("main").get("temp_max"));
				values.put("temperatureMin", json.getJSONObject("main").get("temp_min"));
				responseArray.add(values);
			});
			
			response.put("weather", responseArray);
			
		} catch (Exception e) {
			logger.error("error while calling weather service api {} ", e.getMessage());
			throw new WeatherNotFoundException();
		}
		return response;
		
	}
	

}
