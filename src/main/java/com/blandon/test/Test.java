package com.blandon.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.blandon.test.bean.User;

public class Test {
	
	//Lambda expression form
	//Argument List	              Arrow Token	                Body
	//(int x, int y)   	          ->	                            x + y
	public static void main(String[] args) {

		//https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
		
		List<User> users = new ArrayList();

		users.add(new User("atest", 10));
		users.add(new User("atest2", 20));
		users.add(new User("atest3", 30));
		users.add(new User("atest4", 40));
		//runnableTest();
		//ageMoreThan20(users);
		//lambdaForEach(users);
		lambdaFilter(users);
		lambdaMap(users);
		//lambdaCollectFilteredUsers(users);
	}
	
	
	
	
	private static void lambdaFilter(List<User> users) {
		//users.stream().filter(u -> u.getAge()>20).forEach(u -> System.out.println(u.getName()+", "+u.getAge()));
		//users.stream().filter(u -> u.getAge()>20).forEach(User::printPerson);
		System.out.println(users.stream().filter(u -> u.getAge()>20).mapToInt(u -> u.getAge()).average().getAsDouble());
	}
	
	private static void lambdaMap(List<User> users) {
		 List<String> nameList = users.stream().map(u -> u.getName()).collect(Collectors.toList());
		 System.out.println(nameList);
	}
	
	private static void lambdaMap2(List<User> users) {
		 List<Integer> nameList = users.stream().map(u -> u.getAge()*2).collect(Collectors.toList());
		 System.out.println(nameList);
	}
	
	/**
	 * Keep filtered users to a new list
	 * */
	private static void lambdaCollectFilteredUsers(List<User> users) {
		List<User> newUsers = users.stream().filter(u->u.getAge()>20).collect(Collectors.toList());
		newUsers.forEach(User::printPerson);
		
		User atestUser = users.stream().filter(u->"atest".equals(u.getName())).findAny().orElse(null);
	}
	
	private static void lambdaForEach(List<User> users) {
		users.forEach(u -> System.out.println(u.getName()+", "+u.getAge()));
	}
	
	
	/**
	 * Filter out users older than 20 leveraging Predicate functional interface
	 * */
	private static void ageMoreThan20(List<User> users) {
		
		Predicate<User> preUsers = (user) -> user.getAge()>20;
		
		for(User user: users) {
			if(preUsers.test(user)) {
				System.out.println("Users who are older than 20: "+user.getName()+", "+user.getAge());
			}
		}
	}
	
	private static void testIfListContainElement(List<User> users) {
		boolean testIfUserExists = users.stream().anyMatch(u -> "atest".equals(u.getName()));
		
		boolean testIfUserExists2 =false;
		Predicate<User> preUsers = (user) -> "atest".equals(user.getName());
		for(User user: users) {
			if(preUsers.test(user)) {
				 testIfUserExists2 = true;
			}
		}
		
	}
	
	//Implement Runnable interface
	private static void runnableTest() {
		
		//implement Runnable through anonymous class
		Runnable run1 = new Runnable(){
			public void run() {
				System.out.println("Run 1");
			}
		};
		
		run1.run();
		
		//implement Runnable through lambda
		Runnable run2 = () -> System.out.println("Run 2");
		Runnable run3 = () -> {System.out.println("Run 2");};
		run2.run();
	}
	
}
