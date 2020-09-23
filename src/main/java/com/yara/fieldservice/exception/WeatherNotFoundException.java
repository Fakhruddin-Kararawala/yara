package com.yara.fieldservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class FieldNotFoundException.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WeatherNotFoundException extends RuntimeException{
	
	/**
	 * Instantiates a new field not found exception.
	 *
	 * @param fieldId the field id
	 */
	public WeatherNotFoundException() {
		super("Something went wrong. Please try again later.. ");
	}

}
