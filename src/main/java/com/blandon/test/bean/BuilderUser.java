package com.blandon.test.bean;

public class BuilderUser {
	
	private String name;
	private int age;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	
	private BuilderUser (Builder builder) {
		this.name = builder.name;
		this.age = builder.age;
	}

	
	public static class Builder{
		
		private String name;
		private int age;
		
		public Builder name(String name) {
			this.name= name;
			return this;
		}
		
		public Builder age (int age) {
			this.age=age;
			return this;
		}
		
		public BuilderUser build() {
			return new BuilderUser(this);
		}
		
	}

}
