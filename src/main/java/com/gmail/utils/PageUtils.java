package com.gmail.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class PageUtils {
	WebDriverWait wait;
	protected @FindBy(xpath="//span[@class='bAq']") WebElement notificationMessage;
	Logger logger = Logger.getLogger(PageUtils.class);
	private static int WAIT_TIMEOUT = 10;
	
	public PageUtils(WebDriver driver) {
		wait = new WebDriverWait(driver, 15);
		PageFactory.initElements(driver, this);
	}
	
	public static void setTimeout(int sec) {
		WAIT_TIMEOUT = sec;
	}
	
	public void waitForVisibilityOfElement(WebElement element) {
		logger.info("Waiting for element to be visible: "+element.toString());
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForClickabilityOfElement(WebElement element) {
		logger.info("Waiting for element to be clickable: "+element.toString());
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public boolean isTextPresentInTitle(String text) {
		try {
			logger.info("Waiting for text to be present in title: "+text);
			return wait.until(ExpectedConditions.titleContains(text));
		} catch (Exception e) {
			return false;
		}
	}
	
	public void click(WebElement element) {
		waitForClickabilityOfElement(element);
		element.click();
	}
	
	public void enterText(WebElement element, String text) {
		waitForVisibilityOfElement(element);
		element.sendKeys(text);
	}
	
	public boolean isMessageDisplayed(String message) {
		try {
			if (wait.until(ExpectedConditions.textToBePresentInElement(notificationMessage, message))) {
				ATUReports.add("Verified message is present on screen", message, LogAs.PASSED, null);
				return true;
			} else {
				ATUReports.add("Verified message is NOT present on screen", message, LogAs.PASSED, null);
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
