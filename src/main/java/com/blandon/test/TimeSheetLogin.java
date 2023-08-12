package com.blandon.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TimeSheetLogin {

    public TimeSheetLogin(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    WebDriver driver;

    @FindBy(name = "loginfmt")
    public WebElement emailBox;

    @FindBy(id = "idSIButton9")
    public WebElement nextButton;

    public void enterEmail() {
        emailBox.sendKeys("bdong@opentext.com");
        nextButton.click();
    }

}
