package com.gmail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestUtils {
	protected WebDriver driver = null;
	Properties testData = null;
	String className = this.getClass().getSimpleName();
//	protected CustomLogger logger = new CustomLogger(className);
	protected Logger logger = Logger.getLogger(TestUtils.class);
	
	{
		System.setProperty("atu.reporter.config",
				System.getProperty("user.dir") + "\\src\\test\\resources\\atu\\atu.properties");
	}

	@BeforeMethod
	public void setUp() {
		// Get test data
		testData = new Properties();
		try {
			
			String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\DataFiles\\" + className
					+ ".properties";
			testData.load(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.setProperty("webdriver.chrome.driver", "D:\\Vipul_Imp\\Softwares\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		ATUReports.setWebDriver(driver);
		driver.manage().window().maximize();
		String baseUrl = "http://gmail.com";
		driver.get(baseUrl);
		ATUReports.add("Navigate to url", baseUrl, LogAs.PASSED, null);
	}

	public String getData(String key) {
		return testData.get(key) == null ? "" : testData.get(key).toString();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		ATUReports.add("Quiting driver", LogAs.PASSED, null);
	}
}
