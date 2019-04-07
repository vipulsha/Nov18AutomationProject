package com.gmail.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gmail.pages.InboxPage;
import com.gmail.pages.LoginPage;
import com.gmail.utils.TestUtils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class SignInTest extends TestUtils {
	
	@Test
	public void signInTest() {
		try {
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(getData("username"), getData("password"));
			InboxPage inboxPage = new InboxPage(driver);
			Assert.assertTrue(inboxPage.isUserLoggedIn(getData("username")), "User is NOT logged in");
		} catch (Throwable e) {
//			logger.fail("Failed: "+e.getMessage());
			logger.error("Failed: "+e.getMessage());
			ATUReports.add("Failed: "+e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
}
