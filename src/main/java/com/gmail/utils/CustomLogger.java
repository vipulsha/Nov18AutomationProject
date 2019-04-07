package com.gmail.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class CustomLogger extends Logger {
	static Logger logger;

	protected CustomLogger(String name) {
		super(name);
		logger = Logger.getLogger(name);
	    logger.setLevel(Level.ALL);
	}
	
	@Override
	public void info(Object message) {
		super.info(message);
	}
	
	@Override
	public void error(Object message) {
		super.error(message);
	}
	
	public void pass(Object message, String data) {
		super.info(message+" : "+data);
		ATUReports.add(message.toString(), data, LogAs.PASSED, null);
	}
	
	public void fail(Object message) {
		super.error(message);
		ATUReports.add(message.toString(), "", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
}
