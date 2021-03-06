package com.gmail.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gmail.pages.InboxPage;
import com.gmail.pages.LoginPage;
import com.gmail.utils.TestUtils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

public class SignInTest extends TestUtils {
	
	@Test
	public void signInTest() {
		try {
			ATUReports.setAuthorInfo("Vipul Sharma", Utils.getCurrentTime(),"1.0");
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(getData("username"), getData("password"));
//			loginPage.login(testData.get("username").toString(), testData.get("password1").toString());
			InboxPage inboxPage = new InboxPage(driver);

			// How to use TestNg Assertion
			Assert.assertTrue(inboxPage.isUserLoggedIn(getData("username")), "User is NOT logged in");
		} catch (Throwable e) {
			logFailure(e.getMessage());
		}
	}
}
