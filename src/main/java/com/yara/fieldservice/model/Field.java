package com.yara.fieldservice.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Properties {

}

@Getter
@Setter
class Geometry{
 
	public String type;
 
	public List<List<List<Double>>> coordinates;
}

@Getter
@Setter
class GeoJson {
	
	public String type;
	
	public Properties properties;
	
	public Geometry geometry;
}

@Getter
@Setter
class Bounderies {
	
	public String id;
	
	public Date created;
	
	public String updated;
	
	public GeoJson geoJson;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("fields")
public class Field {
	
	@Id
	public String id;
	
	public String name;
	
	public Date created;
	
	public String updated;
	
	public String countryCode;
	
	public Bounderies bounderies;
}
