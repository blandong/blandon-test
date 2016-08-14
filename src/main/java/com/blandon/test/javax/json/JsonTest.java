package com.blandon.test.javax.json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
		//Person p = readPersonFromFile();
		
		//jsonWriter(p);
		
		testJson2();
		
		
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
	
	
	private static void testJson2(){
		
        JsonObjectBuilder payloadBuilder = Json.createObjectBuilder();
        JsonArrayBuilder packageClaimsBuilder = Json.createArrayBuilder();
        
        payloadBuilder.add("requestId", 11);
        payloadBuilder.add("requestedPackageName", "SGM portal");
        payloadBuilder.add("justification", "request access");
        payloadBuilder.add("requestorName", "Tom Johnson");
        payloadBuilder.add("requestorIdentityNumber", "310826198004283713");
        payloadBuilder.add("requestorPhone", "18301588382");
        payloadBuilder.add("requestorType", "Supplier");
        payloadBuilder.add("requestorTitle", "PortalSupplierUser");
        payloadBuilder.add("requestorCompanyName", "SupplierA");
        payloadBuilder.add("subject", "Supplier access request.");
        payloadBuilder.add("sgmPurchaserUID", "aaa");
        payloadBuilder.add("sgmPurchaserEmail", "bai.dong@covisint.com");
        
        List<String> packageClaims = new ArrayList<String>();
        packageClaims.add("1_3_4_value_desc");
        packageClaims.add("2_4_5_value2_desc2");
        
        for(String claim : packageClaims){
            packageClaimsBuilder.add(claim);
        }
        
        payloadBuilder.add("packageClaims", packageClaimsBuilder);
        
        String payload = payloadBuilder.build().toString();
        
        System.out.println(payload);
	}
	
	
	private static void testJson(){
		JsonObjectBuilder payloadBuilder = Json.createObjectBuilder();
		   
		   payloadBuilder.add("firstName", "Superuser (S-FEBIDM-FIDM4)");
		   payloadBuilder.add("middleName", "test");
		   payloadBuilder.add("lastName", "Covisint");
		   
		   payloadBuilder.add("identityNo", "310826198005273963");
		   
		   payloadBuilder.add("email", "bai.dong@covisint.com");
		   payloadBuilder.add("phone", "18501655384");
		   payloadBuilder.add("mobilePhone", "18501655384");
		   
		   payloadBuilder.add("address", "HongQiao Rd");
		   payloadBuilder.add("postalCode", "48075");
		   
		   payloadBuilder.add("jobTitle", "developer");
		   payloadBuilder.add("language", "en");
		   
		   payloadBuilder.add("company", "S-FEBIDM-FIDM4");
		   payloadBuilder.add("duns", "12345");
		   payloadBuilder.add("state", "");
		   payloadBuilder.add("country", "US");
		   
		   payloadBuilder.add("serviceIds", "sgmService1, sgmService2");
		   
		   payloadBuilder.add("cuid", "FHQ61E37");
		   
		   payloadBuilder.add("sgmUserId", "sgmTestId");
		   
		   String payload = payloadBuilder.build().toString();
		   
		   logger.debug("constructed json body: \n {}", payload);
		   
		   System.out.println(payload);
	}
	
}
