package com.gmail.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gmail.utils.PageUtils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class InboxPage extends PageUtils {
	WebDriver driver;
	@FindBy(xpath = "//div[text()='Compose']")
	WebElement button_Compose;
	@FindBy(name = "to")
	WebElement textbox_To;
	@FindBy(name = "subjectbox")
	WebElement textbox_Subject;
	@FindBy(xpath = "//div[@aria-label='Message Body']")
	WebElement textArea_MessageBody;
	@FindBy(xpath = "//div[text()='Send']")
	WebElement button_Send;
	@FindBy(xpath = "//span/span[text()='Cc']")
	WebElement linkCc;
	@FindBy(xpath = "//span/span[text()='Bcc']")
	WebElement linkBcc;
	@FindBy(name = "cc")
	WebElement textbox_Cc;
	@FindBy(name = "bcc")
	WebElement textboxBcc;
	@FindBy(xpath = "//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']")
	WebElement button_Delete;
	Logger logger = Logger.getLogger(InboxPage.class);
	
	public InboxPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public boolean isUserLoggedIn(String userName) {
		if (isTextPresentInTitle(userName)) {
			ATUReports.add("Verified text is present in title", userName, LogAs.PASSED, null);
			return true;
		} else {
			ATUReports.add("Verified text is NOT present in title", userName, LogAs.PASSED, null);
			return false;
		}
	}
	
	public void composeEmail(String toEmailIds, String subject, String messageBody) {
		click(button_Compose);
		ATUReports.add("Clicked on Compose button", LogAs.PASSED, null);
		logger.info("Clicked on Compose button");
		
		enterText(textbox_To, toEmailIds);
		ATUReports.add("Entered email ids in to field", toEmailIds, LogAs.PASSED, null);
		logger.info("Entered email ids in to field: "+toEmailIds);
		
		enterText(textbox_Subject, subject);
		ATUReports.add("Entered subject", subject, LogAs.PASSED, null);
		logger.info("Entered subject: "+subject);
		
		enterText(textArea_MessageBody, messageBody);
		ATUReports.add("Entered message body", messageBody, LogAs.PASSED, null);
		logger.info("Entered message body: "+messageBody);
		
		click(button_Send);
		ATUReports.add("Clicked on Send button", LogAs.PASSED, null);
		logger.info("Clicked on Send button");
	}
}
