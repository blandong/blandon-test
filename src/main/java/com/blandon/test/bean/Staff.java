package com.blandon.test.bean;

import java.util.Map;

public class Staff extends Employee{
	Map<String, String> locationMap;

	public Map<String, String> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(Map<String, String> locationMap) {
		this.locationMap = locationMap;
	}

	@Override
	public String toString() {
		return "Staff [getId()=" + getId() + ", getFirstName()="
				+ getFirstName() + ", getLastName()=" + getLastName()
				+ ", getRoles()=" + getRoles() + "]"
				+", locationMap value of key1= "+getLocationMap().get("key1");
	}
	
	
	
}
