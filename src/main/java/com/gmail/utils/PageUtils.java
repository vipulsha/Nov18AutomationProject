package com.gmail.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUtils {
	WebDriverWait wait;
//	final static CustomLogger logger = new CustomLogger("PageUtils");
	Logger logger = Logger.getLogger(PageUtils.class);
	
	public PageUtils(WebDriver driver) {
		wait = new WebDriverWait(driver, 10);
	}
	
	public void waitForVisibilityOfElement(WebElement element) {
		logger.info("Waiting for visibility of element: "+element.toString());
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForClickabilityOfElement(WebElement element) {
		logger.info("Waiting for clickability of element: "+element.toString());
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public boolean isTextPresentInTitle(String text) {
		try {
			if(wait.until(ExpectedConditions.titleContains(text))) {
//			ATUReports.add("Verifying text in title", text, LogAs.PASSED, null);
//				logger.pass("Title contains the text", text);
				logger.info("Title contains the text: "+text);
				return true;
			}
		} catch (Exception e) {
			logger.info("Title DOES NOT contain the text: "+text);
		}
		return false;
	}
	
	private void click(WebElement element) {
		waitForClickabilityOfElement(element);
		element.click();
	}

	public void click(WebElement element, String elementName) {
		click(element);
//		logger.pass("Clicked on "+elementName, "");
		logger.info("Clicked on "+elementName);
	}
	
	private void enterText(WebElement element, String text) {
		waitForVisibilityOfElement(element);
		element.sendKeys(text);
	}

	public void enterText(WebElement element, String text, String elementName) {
		enterText(element, text);
//		logger.pass("Entered text in "+elementName, text);
		logger.info("Entered text in "+elementName);
	}
}
