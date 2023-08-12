package com.blandon.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TimeSheet {

    public TimeSheet(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    WebDriver driver;

    @FindBy(id = "ToolbarOkCode")
    public WebElement cat2InputBox;

    public void enterCat2AndEnter() {
        driver.get("https://saperp.opentext.net:8101/webgui?sap-client=100&sap-language=EN");
        cat2InputBox.sendKeys("cat2");
        TimeSheetLogin timeSheetLogin = new TimeSheetLogin(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        timeSheetLogin.enterEmail();
    }

}
