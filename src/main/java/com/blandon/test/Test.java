package com.blandon.test;

import java.awt.AWTException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.davidafsilva.apple.OSXKeychain;
import pt.davidafsilva.apple.OSXKeychainException;

public class Test {
	public static final Logger logger = LoggerFactory.getLogger(Test.class);
	public static void main(String[] args) throws AWTException, InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("download.default_directory=/Users/bdong/Downloads");
//		options.addArguments("download.prompt_for_download=false");
		options.addArguments("disable-popup-blocking");
		Map<String, Object> prefs = new HashMap<>();
//		prefs.put("download.default_directory", "/Users/bdong/Downloads");
//		prefs.put("profile.default_content_settings.popups", 0);
//		String _PATH_TO_DOWNLOAD_DIR = "/Users/bdong/Downloads";
//		prefs.put("browser.set_download_behavior","{ behavior: 'allow' , downloadPath: '"+_PATH_TO_DOWNLOAD_DIR+"'}");
//		options.setExperimentalOption("prefs", prefs);
//		DesiredCapabilities cap = DesiredCapabilities
//		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		cap.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(options);
		login(driver);
		HomePage homePage = new HomePage(driver);
		homePage.closePopup();
		homePage.clickJournal();
		homePage.clickListView();
		homePage.download();
		//close the browser when done
//		driver.quit();

	}

	public static void login(WebDriver driver){
		LoginPage loginPage = new LoginPage(driver);
		String user = "bai528.dong@gmail.com";
		String password = getPasswordFromMacKeyChain("kindercare", "kindercare");
//		loginPage.login(user, password);
	}


	/**
	 * Extract generic password from Mac keychain.
	 *
	 * @return password for given keychain entry.
	 *
	 * @throws OSXKeychainException
	 */
	public static String getPasswordFromMacKeyChain(String serviceName, String accountName) {
		try {
			final OSXKeychain keychain = OSXKeychain.getInstance();
			return keychain.findGenericPassword(serviceName, accountName).get();
		} catch (OSXKeychainException e) {
			logger.error(String.format("Error occurred on getting keychain password for serviceName %s and accountName %s", serviceName, accountName), e);
		}
		return null;
	}

}
