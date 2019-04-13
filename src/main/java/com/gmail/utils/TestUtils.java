package com.gmail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
	MethodListener.class })
public class TestUtils {
	{
		String log4jConfPath = System.getProperty("user.dir")+"\\src\\main\\resources\\Log4J\\log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		System.setProperty("atu.reporter.config", System.getProperty("user.dir")+"\\src\\test\\resources\\atu\\atu.properties");
	}

	protected RemoteWebDriver driver = null;
	Properties testData = null;
	Logger logger = Logger.getLogger(TestUtils.class);

	@BeforeMethod
	public void setUp() {
		// Get test data
		testData = new Properties();
		try {
			String className = this.getClass().getSimpleName();
			String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\DataFiles\\"+className+".properties";
			testData.load(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.setProperty("webdriver.chrome.driver", "D:\\Vipul_Imp\\Softwares\\Drivers\\chromedriver.exe");
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		capabilities.setBrowserName("chrome");
//		capabilities.setPlatform(Platform.WIN10);
		capabilities.setJavascriptEnabled(true);
		
		try {
			driver = new RemoteWebDriver(new URL("http://192.168.1.9:4444/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Launched browser: CHROME");
		ATUReports.setWebDriver(driver);
		String baseUrl = "https://accounts.google.com/signin/v2/identifier";
		driver.get(baseUrl);
		logger.info("Navigated to url: "+baseUrl);
		ATUReports.add("Navigated to url", baseUrl, LogAs.PASSED, null);
	}

	public String getData(String key) {
		return testData.get(key)==null?"" : testData.get(key).toString();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		logger.info("Quit driver");
		ATUReports.add("Quit driver", LogAs.PASSED, null);
	}

	public void logFailure(String message) {
		logger.error(message);
		ATUReports.add("Failed with message: "+message, "", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
}
