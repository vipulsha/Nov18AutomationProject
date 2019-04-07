package com.gmail.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gmail.pages.InboxPage;
import com.gmail.pages.LoginPage;
import com.gmail.utils.TestUtils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

public class ComposeEmailTest extends TestUtils {

	@Test
	public void composeEmailTest() {
		try {
			ATUReports.setAuthorInfo("Nikhil Ugale", Utils.getCurrentTime(),"1.0");
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(getData("username"), getData("password"));
			InboxPage inboxPage = new InboxPage(driver);
			Assert.assertTrue(inboxPage.isUserLoggedIn(getData("username")), "User is not logged in");

			inboxPage.composeEmail(getData("emailIds"), getData("subject"), getData("messageBody"));
			Assert.assertTrue(inboxPage.isMessageDisplayed(getData("message")), "Email not sent");
		} catch (Throwable e) {
			logFailure(e.getMessage());
		}
	}
}
