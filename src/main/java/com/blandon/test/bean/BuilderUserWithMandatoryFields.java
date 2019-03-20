package com.blandon.test.bean;

public class BuilderUserWithMandatoryFields {
	private String name; //mandatory
	private int age; //mandatory
	private String city;
	private String country;
	
	private BuilderUserWithMandatoryFields (Builder builder) {
		this.name = builder.name;
		this.age = builder.age;
		this.city = builder.city;
		this.country = builder.country;
	}
	
	
	public static class Builder implements Name, Age{
		private String name; //mandatory
		private int age; //mandatory
		private String city;
		private String country;
		@Override
		public Builder age(int age) {
			this.age = age;
			return this;
		}
		@Override
		public Age name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder city(String city) {
			this.city = city;
			return this;
		}
		
		public BuilderUserWithMandatoryFields build() {
			return new BuilderUserWithMandatoryFields(this);
		}
		
	}
	
	public static interface Name{
		Age name (String name);
	}
	
	public static interface Age {
		Builder age(int age);
	}

}
