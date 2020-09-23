package com.yara.fieldservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The Class FieldServiceApplication.
 */
@SpringBootApplication
public class FieldServiceApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FieldServiceApplication.class, args);
	}
	
	/**
	 * Gets the rest template.
	 *
	 * @return the rest template
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
