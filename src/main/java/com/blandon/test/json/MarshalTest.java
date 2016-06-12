package com.blandon.test.json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blandon.test.bean.Employee;
import com.blandon.test.bean.Staff;
import com.google.gson.Gson;

public class MarshalTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MarshalTest.class);
	
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
		
		unMarshalFromJsonString();
		
		
		Staff staff = createStaff();
		
		marshal(staff);
		
		unMarshal();
		
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
	
	
	private static Staff createStaff(){
		
		Staff staff = new Staff();
		
		staff.setFirstName("fName");
		staff.setLastName("lName");
		
		Map<String, String> location = new HashMap<String, String>();
		location.put("key1", "value1");
		
		staff.setLocationMap(location);
		
		return staff;
	}
	
	
	public static void marshal(Staff staff){
		
		Gson gson = new Gson();
		
		String staffString = gson.toJson(staff, Staff.class);
		
		logger.debug("staff string: {}", staffString);
		
	}
	
	
	public static Employee unMarshal(String json){
		Gson gson = new Gson();
		Employee ep = gson.fromJson(json, Employee.class);
		return ep;
		
	}
	
	
	public static Staff unMarshal(){
		
		//{"locationMap":{"key1":"value1"},"firstName":"fName","lastName":"lName"}
		
		String s = "{\"locationMap\":{\"key1\":\"value1\"},\"firstName\":\"fName\",\"lastName\":\"lName\"}";
		
		Gson gson = new Gson();
		
		Staff staff = gson.fromJson(s, Staff.class);
		
		logger.debug("staff: {}", staff);
		
		return staff;
	}
	
	
	public static Employee unMarshalFromJsonString(){
		
		//{"id":1,"firstName":"Lokesh","lastName":"Gupta","roles":["ADMIN","MANAGER"]}
	    String jsonString = "{\"id\":1,\"firstName\":\"Lokesh\",\"lastName\":\"Gupta\",\"roles\":[\"ADMIN\",\"MANAGER\"]}";
	    
	    Gson gson = new Gson();
	    
	    Employee e = gson.fromJson(jsonString, Employee.class);
	    
	    System.out.println("unmarshalled to employee: "+e.getFirstName());
	    
	    return e;
	    
	}
	
	
	

}
