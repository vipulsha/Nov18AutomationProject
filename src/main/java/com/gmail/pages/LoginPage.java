package com.gmail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gmail.utils.PageUtils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class LoginPage extends PageUtils {
	
	@FindBy(id="identifierId") WebElement textbox_EmailId;
	@FindBy(xpath="//span[text()='Next']") WebElement button_Next;
	@FindBy(name="password") WebElement textbox_Password;

	public LoginPage(WebDriver driver) {
		super(driver);
		// Initialize web elements
		PageFactory.initElements(driver, this);
	}
	
	public void login(String username, String password) {
		enterText(textbox_EmailId, username);
		ATUReports.add("Enter email id", username, LogAs.PASSED, null);
		
		click(button_Next);
		ATUReports.add("Click on Next button", LogAs.PASSED, null);
		
		enterText(textbox_Password, password);
		ATUReports.add("Enter password", password, LogAs.PASSED, null);
		
		click(button_Next);
		ATUReports.add("Click on Next button", LogAs.PASSED, null);
	}
}
