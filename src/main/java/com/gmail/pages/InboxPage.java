package com.gmail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gmail.utils.PageUtils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class InboxPage extends PageUtils {
	WebDriver driver;
	@FindBy(xpath="//div[text()='Compose']") WebElement button_Compose;
	
	public InboxPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public boolean isUserLoggedIn(String userName) {
		if (isTextPresentInTitle(userName)) {
			ATUReports.add("User is logged in", userName, LogAs.PASSED, null);
			return true;
		} else {
			ATUReports.add("User is not logged in", userName, LogAs.PASSED, null);
			return false;
		}
	}
}
