package com.yara.fieldservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.yara.fieldservice.exception.WeatherNotFoundException;
import com.yara.fieldservice.model.Field;
import com.yara.fieldservice.repository.FieldRepository;

import net.sf.json.JSONObject;

/**
 * The Class FieldServiceTest.
 */
@ExtendWith(MockitoExtension.class)
public class FieldServiceTest {

	/** The field service. */
	@InjectMocks
	private FieldService fieldService;
	
	/** The field repository. */
	@Mock
	private FieldRepository fieldRepository;
	
	
	/** The rest template. */
	@Mock
	private RestTemplate restTemplate;
	
	/**
	 * Should create field successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCreateFieldSuccessfully() throws Exception {
		final Field field = new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Potato field", new Date(), "", "DEU", null);
		given(fieldRepository.save(field)).willAnswer(invocation -> invocation.getArgument(0));
		String _id = fieldService.createField(field);
		assertThat(_id).isNotNull();
		assertEquals(_id, field.getId());
		verify(fieldRepository).save(any(Field.class));
		
	}
	
	/**
	 * Should find field successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldFindFieldByIdSuccessfully() throws Exception {
		String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		final Field field = new Field(_id, "Potato field", new Date(), "", "DEU", null);
		given(fieldRepository.findById(_id)).willReturn(Optional.of(field));
		Optional<Field> actual = fieldService.findFieldbyId(_id);
		assertThat(actual).isNotNull();
		assertEquals(actual.get().getId(), field.getId());
		
	}
	
	/**
	 * Should update field successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldUpdateFieldSuccessfully() throws Exception {
		Field field = new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Potato field", new Date(), "", "DEU", null);
		given(fieldRepository.save(field)).willReturn(field);
		given(fieldRepository.findById(field.getId())).willReturn(Optional.of(field));
		field.setName("Tomato Field");
		Field updatedField = fieldService.updateField(field);
		assertThat(updatedField).isNotNull();
		assertEquals(updatedField.getName(), "Tomato Field");
		verify(fieldRepository).save(any(Field.class));
		
	}
	
	/**
	 * Should delete field successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteFieldSuccessfully() throws Exception {
		final String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		fieldService.deleteFieldById(_id);
		verify(fieldRepository, times(1)).deleteById(_id);
	}
	
	/**
	 * Should return all field successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturnAllFieldSuccessfully() throws Exception {
		Field potatoField = new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Potato field", new Date(), "", "DEU", null);
		Field tomatoField = new Field("a0f63e74-d7ef-4924-acb3-0e770ae9ec98", "Tomato field", new Date(), "", "IND", null);
		List<Field> fieldList = new ArrayList<>();
		fieldList.add(potatoField);
		fieldList.add(tomatoField);
		given(fieldRepository.findAll()).willReturn(fieldList);
		
		List<Field> actualFields = fieldService.findAllFields();
		assertThat(actualFields).isNotNull();
		assertEquals(actualFields, fieldList);
	}
	
	/**
	 * Should return weather info successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturnWeatherInfoSuccessfully() throws Exception {

		ReflectionTestUtils.setField(fieldService, "weatherEndpoint", "mockUrl");
		final String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
		String apiResponse = "[{dt:1485702000,weather:[{id:801,main:\"Clouds\",description:\"few clouds\",icon:\"02n\"}],main:{temp:276.91,pressure:1031,humidity:81,temp_min:274.15,temp_max:280.15},wind:{speed:2,deg:140},clouds:{all:20}},{dt:1485705600,weather:[{id:804,main:\"Clouds\",description:\"overcast clouds\",icon:\"04d\"}],main:{temp:276.9,pressure:1014,humidity:56,temp_min:273.15,temp_max:280.15},wind:{speed:6,deg:250},clouds:{all:90}},{dt:1485709200,weather:[{id:701,main:\"Mist\",description:\"mist\",icon:\"50d\"}],main:{temp:278.65,pressure:1014,humidity:60,temp_min:275.15,temp_max:286.15},wind:{speed:4,deg:250},clouds:{all:90}},{dt:1485712800,weather:[{id:801,main:\"Clouds\",description:\"few clouds\",icon:\"02d\"}],main:{temp:282.44,pressure:1032,humidity:71,temp_min:280.15,temp_max:287.15},wind:{speed:2,deg:100},clouds:{all:20}},{dt:1485716400,weather:[{id:804,main:\"Clouds\",description:\"overcast clouds\",icon:\"04d\"}],main:{temp:283.9,pressure:1013,humidity:52,temp_min:280.15,temp_max:288.15},wind:{speed:7,deg:300},clouds:{all:90}},{dt:1485720000,weather:[{id:803,main:\"Clouds\",description:\"broken clouds\",icon:\"04d\"}],main:{temp:285.68,pressure:1031,humidity:71,temp_min:280.15,temp_max:288.15},wind:{speed:2,deg:240},clouds:{all:75}},{dt:1485723600,weather:[{id:804,main:\"Clouds\",description:\"overcast clouds\",icon:\"04d\"}],main:{temp:286.78,pressure:1014,humidity:80,temp_min:279.15,temp_max:297.15},wind:{speed:4,deg:310},clouds:{all:90}},{dt:1485727200,weather:[{id:804,main:\"Clouds\",description:\"overcast clouds\",icon:\"04d\"}],main:{temp:287.43,pressure:1015,humidity:60,temp_min:278.15,temp_max:296.15},wind:{speed:4,deg:290},clouds:{all:90}}]";

		doReturn(apiResponse).when(restTemplate).getForObject("mockUrl", String.class);
		JSONObject fetchWeatherInfo = fieldService.fetchWeatherInfo(_id);
		assertNotNull(fetchWeatherInfo);
	}
	
	/**
	 * Should fail when return weather info.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldFailWhenReturnWeatherInfo() throws Exception {

		ReflectionTestUtils.setField(fieldService, "weatherEndpoint", null);
		final String _id = "a0f63e74-d7ef-4924-acb3-0e770ae9ec98";
	
		assertThrows(WeatherNotFoundException.class, () -> fieldService.fetchWeatherInfo(_id));
	}
	
}
