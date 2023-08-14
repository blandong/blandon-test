package com.blandon.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user_login")
    public WebElement user;

    @FindBy(id = "user_password")
    public WebElement password;

    @FindBy(name = "commit")
    public WebElement loginButton;

    public void waitForElementToAppear(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    public void login(String user, String password) {
        driver.get("https://classroom.kindercare.com/login");
        this.user.sendKeys(user);
        this.password.sendKeys(password);
        loginButton.click();
    }


}
