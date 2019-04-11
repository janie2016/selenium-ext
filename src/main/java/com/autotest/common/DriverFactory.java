package com.autotest.common;

import org.openqa.selenium.WebDriver;

public interface DriverFactory
{
	public WebDriver getDriver(String url);
}
