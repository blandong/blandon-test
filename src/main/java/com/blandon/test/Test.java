package com.blandon.test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
//		login(driver);
		enterTime(driver);
	}

	public static void login(WebDriver driver){
		LoginPage loginPage = new LoginPage(driver);
		String user = "[D-INS01-TEST2]D-INS01-TEST2_ADMIN";
		String password = "test1234";
		loginPage.login(user, password);
	}

	public static void enterTime(WebDriver driver){
		TimeSheet timeSheet = new TimeSheet(driver);
		timeSheet.enterCat2AndEnter();
	}
}
