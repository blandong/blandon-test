package com.blandon.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get("https://classroom.kindercare.com/headlines");
        Utility.waitForElementToAppear(this.closeButton, driver, Duration.ofSeconds(3));
    }


    @FindBy(className = "contacts-close-button")
    public WebElement closeButton;

//    @FindBy(linkText = "/accounts/399146/journal")
    @FindBy(linkText = "Journal")
    public WebElement journal;

    @FindBy(className = "btn-light")
    public WebElement listView;

//    @FindBy(xpath = "//ul[@class='pagination']/li")
//    public WebElement listView;

    public void closePopup() {
        this.closeButton.click();
    }

    public void clickJournal() {
//        Utility.waitForElementToAppear(this.journal, driver, Duration.ofSeconds(3));
        this.journal.click();
    }

    public void clickListView(){
//        Utility.waitForElementToAppear(this.journal, driver, Duration.ofSeconds(3));
        this.listView.click();
    }

    public void download() throws AWTException, InterruptedException {
        List<WebElement> downloadLinks = driver.findElements(By.cssSelector("[download]"));
        List<WebElement> oneList = Arrays.asList(downloadLinks.get(0));

        downloadLinks.forEach(downloadLink -> {
            // Create an Actions object
/*
            Actions actions = new Actions(driver);
            // Perform a right-click on the link
            actions.contextClick(downloadLink).build().perform();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            // Send keyboard keys to select "Save Link As"
            actions.sendKeys("V").build().perform(); // This corresponds to "Save Link As" in most browsers
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
*/
            // Execute JavaScript to initiate download
//            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//            jsExecutor.executeScript("arguments[0].click();", downloadLink);
            // Wait for the download to complete (you might need a more robust way to wait here)
            String url = downloadLink.getAttribute("href");
            String fileName = downloadLink.getAttribute("download");
            downloadFromUrl(url, "/Users/bdong/Downloads/Kindercare/"+fileName);
        });

        WebElement nextButton = driver.findElement(By.cssSelector("[rel='next']"));
        nextButton.click();
        Thread.sleep(2000);
        download();


    }

    private static void downloadFromUrl(String url, String downloadPath) {

        try (InputStream in = new URL(url).openStream();
             FileOutputStream fos = new FileOutputStream(downloadPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("Image downloaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void download2() throws AWTException {
        List<WebElement> downloadLinks = driver.findElements(By.cssSelector("[download]"));
        List<WebElement> oneList = Arrays.asList(downloadLinks.get(0));
        // Create an instance of Robot
        Robot robot = new Robot();


        oneList.forEach(downloadLink -> {
            // Create an Actions object
//            Actions actions = new Actions(driver);
//
//            // Perform a right-click on the link
//            actions.contextClick(downloadLink).perform();
//
//            // Send keyboard keys to select "Save Link As"
//            actions.sendKeys("V").perform(); // This corresponds to "Save Link As" in most browsers

            // Perform a right-click using Robot
            performRightClick(robot, downloadLink);

            // Wait for context menu to appear
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Simulate keyboard shortcuts to save the link
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        });

    }

    private static void performRightClick(Robot robot, WebElement element) {
        Point location = element.getLocation();
        int x = location.getX();
        int y = location.getY();

        // Move the mouse to the element's location
        robot.mouseMove(x, y);

        // Simulate a right-click
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
}
