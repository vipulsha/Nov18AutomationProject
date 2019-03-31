package com.gmail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gmail.utils.PageUtils;

public class InboxPage extends PageUtils {
	WebDriver driver;
	@FindBy(xpath="//div[text()='Compose']") WebElement button_Compose;
	
	public InboxPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public boolean isUserLoggedIn(String userName) {
		return isTextPresentInTitle(userName);
	}
}
