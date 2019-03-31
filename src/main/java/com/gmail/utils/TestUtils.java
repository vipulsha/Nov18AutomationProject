package com.gmail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestUtils {
	protected WebDriver driver = null;
	Properties testData = null;
	
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
		
		System.setProperty("webdriver.chrome.driver", "D:\\Vipul_Imp\\Softwares\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://gmail.com");
	}
	
	public String getData(String key) {
		return testData.get(key)==null?"" : testData.get(key).toString();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
