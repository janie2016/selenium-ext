package com.autotest.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.autotest.utils.ConfigUtils;

public class ChromeDriverImpl implements DriverFactory
{
	static String driver_name = null;
	static String driver_value = null;
	
	static {
		if(driver_name == null)
		{
			driver_name = ConfigUtils.getString("driver.name", "webdriver.chrome.driver");
		}
	
		if(driver_value == null)
		{
			driver_value = ConfigUtils.getString("driver.value", "D:\\Google\\chromedriver.exe");
		}
	}

	@Override
	public WebDriver getDriver(String url)
	{
		System.setProperty(driver_name, driver_value);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		int time = ConfigUtils.getInt("driver.wait");
		System.out.println(time);
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
		// 打开页面
		driver.get(url);
		return driver;
	}

}
