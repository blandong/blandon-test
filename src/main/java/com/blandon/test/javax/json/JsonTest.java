package com.blandon.test.javax.json;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blandon.test.javax.Address;
import com.blandon.test.javax.Person;

public class JsonTest {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonTest.class);
	
	public static void main(String[] args) {
		Person p = readPersonFromFile();
		
		jsonWriter(p);
	}
	
	
	
	
	public static Person readPersonFromFile(){
		
		InputStream is = loadFile("person.txt");
		
		/*
		* {
			    "id":123,
			    "name":"Pankaj Kumar",
			    "permanent":true,
			    "address":{
			            "street":"El Camino Real",
			            "city":"San Jose",
			            "zipcode":95014
			        },
			    "phoneNumbers":[9988664422, 1234567890],
			    "role":"Developer"
			}
		 * 
		 * */
		
		JsonReader jr = Json.createReader(is);
		
		JsonObject jo = jr.readObject();
		
		Person p = new Person();
		
		p.setId(jo.getInt("id"));
		p.setName(jo.getString("name"));
		p.setPermanent(jo.getBoolean("permanent"));
		p.setRole(jo.getString("role"));
		
		JsonArray ja = jo.getJsonArray("phoneNumbers");
		
		long[] phoneNumbers = new long[ja.size()];
		
		int index =0;
		
		for(JsonValue value : ja){
			phoneNumbers[index++] = Long.parseLong(value.toString());
		}
		
		p.setPhoneNumbers(phoneNumbers);
		
		
		JsonObject addressObject =jo.getJsonObject("address");
		
		Address ad = new Address();
		
		ad.setCity(addressObject.getString("city"));
		ad.setStreet(addressObject.getString("street"));
		ad.setZipcode(addressObject.getInt("zipcode"));
		
		p.setAddress(ad);
		
		
		logger.debug("person info: {}", p);
		
		return p;
	}
	
	
	private static InputStream loadFile(String file){
		
		InputStream is = JsonTest.class.getClassLoader().getResourceAsStream(file);
		
		return is;
		
	}
	
	
	public static void  jsonWriter(Person p){
		
		/*
		 * ID=123
			Name=Pankaj Kumar
			Permanent=true
			Role=Developer
			Phone Numbers=[9988664422, 1234567890]
			Address=El Camino Real, San Jose, 95014
		 * 
		 * */
		
		JsonObjectBuilder personBuilder = Json.createObjectBuilder();
		
		JsonObjectBuilder addressBuilder = Json.createObjectBuilder();
		
		JsonArrayBuilder phoneBuilder = Json.createArrayBuilder();
		
		
		long[] phoneNumbers = p.getPhoneNumbers();
		
		for(long phoneNumber : phoneNumbers){
			phoneBuilder.add(phoneBuilder.add(phoneNumber));
		}
		
		addressBuilder.add("street", p.getAddress().getStreet());
		addressBuilder.add("city", p.getAddress().getCity());
		addressBuilder.add("zipCode", p.getAddress().getZipcode());
		
		
		personBuilder.add("name", p.getName()).add("id", p.getId()).add("roles", p.getRole());
		
		personBuilder.add("phoneNumbers", phoneBuilder);
		personBuilder.add("address", addressBuilder);
		
		JsonObject personObject = personBuilder.build();
		
		logger.debug("person json object: \n {}", personObject);
		
		
		
		
		
		
		
		
	}
	
}
