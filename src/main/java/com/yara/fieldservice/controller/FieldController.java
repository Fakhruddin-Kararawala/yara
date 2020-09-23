package com.yara.fieldservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yara.fieldservice.exception.ApiResponse;
import com.yara.fieldservice.exception.FieldNotFoundException;
import com.yara.fieldservice.model.Field;
import com.yara.fieldservice.service.FieldService;

import net.sf.json.JSONObject;

/**
 * The Class FieldController.
 */
@RestController
@RequestMapping("/fields")
public class FieldController {

	/** The field service. */
	@Autowired
	private FieldService fieldService;
	
	
	/**
	 * Creates the.
	 *
	 * @param field the field
	 * @return the response entity
	 */
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody Field field){
		String _id = fieldService.createField(field);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/fields/").path("{_id}")
				.buildAndExpand(_id).toUri();
		return ResponseEntity.created(location).body(new ApiResponse("Field Created Successfully"));
	}
	
	
	/**
	 * Find.
	 *
	 * @param fieldId the field id
	 * @return the response entity
	 */
	@GetMapping("{fieldId}")
	@ResponseBody
	public ResponseEntity<?> find(@PathVariable("fieldId") String fieldId){
		Optional<Field> fieldResponse = fieldService.findFieldbyId(fieldId);
		if(fieldResponse.isEmpty()) {
			throw new FieldNotFoundException(fieldId);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(fieldResponse.get());
	}
	
	/**
	 * Delete.
	 *
	 * @param fieldId the field id
	 */
	@DeleteMapping("{fieldId}")
	public void delete(@PathVariable("fieldId") String fieldId) {
		fieldService.deleteFieldById(fieldId);
	}
	
	/**
	 * List.
	 *
	 * @return the response entity
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<?> list(){
		List<Field> fieldResponse = fieldService.findAllFields();
		return ResponseEntity.status(HttpStatus.OK).body(fieldResponse);
	}
	
	/**
	 * Update.
	 *
	 * @param field the field
	 * @return the response entity
	 */
	@PutMapping
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Field field){
		fieldService.updateField(field);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Field Updated Successfully"));
	}
	
	/**
	 * Weather.
	 *
	 * @param fieldId the field id
	 * @return the response entity
	 */
	@GetMapping("{fieldId}/weather")
	@ResponseBody
	public ResponseEntity<?> weather(@PathVariable("fieldId") String fieldId){
		JSONObject weatherInfo = fieldService.fetchWeatherInfo(fieldId);
		return ResponseEntity.status(HttpStatus.OK).body(weatherInfo);
	}
}
