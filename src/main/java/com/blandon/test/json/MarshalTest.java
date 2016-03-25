package com.blandon.test.json;

import java.util.Arrays;

import com.blandon.test.bean.Employee;
import com.google.gson.Gson;

public class MarshalTest {
	
	public static void main(String[] args) {
		
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("Lokesh");
		employee.setLastName("Gupta");
		employee.setRoles(Arrays.asList("ADMIN", "MANAGER"));
		
		String json = marshal(employee);
		
		System.out.println(json);
		
		Employee ep = unMarshal(json);
		System.out.println(ep.getId());
		
		
	}
	
	public static String marshal(Employee employee){
		//First way to create a Gson object for faster coding
		 
		Gson gson = new Gson();
		 
		//Second way to create a Gson object using GsonBuilder
		 
//		Gson gson = new GsonBuilder()
//		             .disableHtmlEscaping()
//		             .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
//		             .setPrettyPrinting()
//		             .serializeNulls()
//		             .create();
		
		String json = gson.toJson(employee);
		
		return  json;
		
	}
	
	
	public static Employee unMarshal(String json){
		Gson gson = new Gson();
		Employee ep = gson.fromJson(json, Employee.class);
		return ep;
		
	}
	
	
	

}
