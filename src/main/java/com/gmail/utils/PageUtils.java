package com.gmail.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUtils {

	WebDriverWait wait;
	
	public PageUtils(WebDriver driver) {
		wait = new WebDriverWait(driver, 15);
	}
	
	public void waitForVisibilityOfElement(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForClickabilityOfElement(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public boolean isTextPresentInTitle(String text) {
		try {
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
	
}
