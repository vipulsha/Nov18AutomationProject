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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
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
	static Properties configProp = null;
	
	@BeforeSuite
	public void beforeSuite() {
		configProp = new Properties();
		try {
			configProp.load(new FileInputStream(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Config\\config.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		
		launchBrowser();
	}

	public String getData(String key) {
		return testData.get(key)==null?"" : testData.get(key).toString();
	}

	public String getConfig(String key) {
		return configProp.getProperty(key)==null?"" : configProp.getProperty(key).toString();
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
	
	public void launchBrowser() {
		String gridHubUrl = System.getenv("GRID_HUB_URL");
		String browser = null;
		String baseUrl = null;
		String waitTimeout = null;
		String executionType = null;
		
		if (gridHubUrl != null && !gridHubUrl.equals("")) {
			logger.info("Config parameters are being used from: Jenkins");
			browser = System.getenv("BROWSER");
			baseUrl = System.getenv("BASE_URL");
			waitTimeout = System.getenv("WAIT_TIMEOUT");
		} else {
			logger.info("Config parameters are being used from: config.properties");
			baseUrl = getConfig("baseUrl");
			waitTimeout = getConfig("waitTimeout");
			browser = getConfig("browserType");
			executionType = getConfig("executionType");
			gridHubUrl = getConfig("gridHubUrl");
		}

		if (baseUrl == null || baseUrl.equals("")) {
			logger.error("Base url not specified.");
			System.exit(0);
		}
		
		if (browser == null || browser.equals("")) {
			browser = "CHROME";
		}
		
		DesiredCapabilities capabilities = null;

		try {
			if (executionType != null && executionType.equalsIgnoreCase("LOCAL")) {
				logger.info("Running test on local: "+browser);
				String driverPath = getConfig("driverPath");
				if (driverPath == null || driverPath.equals("")) {
					logger.error("Driver path is not specified");
					System.exit(0);
				}
				
				if (browser != null && browser.equalsIgnoreCase("CHROME")) {
					System.setProperty("webdriver.chrome.driver", driverPath);					
					driver = new ChromeDriver();
				} else if (browser != null && browser.equalsIgnoreCase("IE")) {
					System.setProperty("webdriver.ie.driver", driverPath);
					driver = new InternetExplorerDriver();
				} else if (browser != null && browser.equalsIgnoreCase("FIREFOX")) {
					System.setProperty("webdriver.gecko.driver", driverPath);					
					driver = new FirefoxDriver();
				} else {
					logger.error("Invalid browser selected");
					System.exit(0);
				}
			} else {
				if (gridHubUrl == null || gridHubUrl.equals("")) {
					logger.error("GridHubURL is not specified.");
					System.exit(0);
				}

				logger.info("Running test on Selenium Grid: "+gridHubUrl);
				
				if (browser != null && browser.equalsIgnoreCase("CHROME")) {
					capabilities = DesiredCapabilities.chrome();
				} else if (browser != null && browser.equalsIgnoreCase("IE")) {
					capabilities = DesiredCapabilities.internetExplorer();
				} else if (browser != null && browser.equalsIgnoreCase("FIREFOX")) {
					capabilities = DesiredCapabilities.firefox();
				} else {
					logger.error("Invalid browser selected");
					System.exit(0);
				}
				capabilities.setJavascriptEnabled(true);
				driver = new RemoteWebDriver(new URL(gridHubUrl), capabilities);
			}
			
			if (waitTimeout != null && !waitTimeout.equals("")) {
				PageUtils.setTimeout(Integer.parseInt(waitTimeout));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		logger.info("Launched browser: CHROME");
		ATUReports.setWebDriver(driver);
		driver.get(baseUrl);
		logger.info("Navigated to url: "+baseUrl);
		ATUReports.add("Navigated to url", baseUrl, LogAs.PASSED, null);
	}
}
