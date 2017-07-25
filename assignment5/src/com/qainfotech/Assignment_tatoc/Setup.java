package com.qainfotech.Assignment_tatoc;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Setup {
	static WebDriver chromesetup(WebDriver driver) {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
	    driver = new ChromeDriver(chromeOptions);
		return driver;
	}
}


