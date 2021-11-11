package com.blandon.test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
	public static void main(String[] args) {
		// declaration and instantiation of objects/variables  
	    System.setProperty("webdriver.chrome.driver", "/Users/bdong/tmp/selenium/chromedriver");  
	    
	    WebDriver driver=new ChromeDriver();  
	      
	    // Launch website  
	    driver.navigate().to("http://www.google.com/");  
	          
	    // Click on the search text box and send value  
	    driver.findElement(By.name("q")).sendKeys("javatpoint tutorials");  
	          
	    // Click on the search button  
	    List<WebElement>  searchButton = driver.findElements(By.name("btnK"));
	    
	    searchButton.get(1).click();
		
	}
}
