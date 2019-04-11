package com.autotest.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autotest.utils.ConfigUtils;

public class DriverHelper
{
	static SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat fmt1 = new SimpleDateFormat("HHmmss");
	
	public WebDriver getDriver()
	{
		return driver;
	}

	public void setDriver(WebDriver driver)
	{
		this.driver = driver;
	}

	private WebDriver driver;

	public DriverHelper(WebDriver driver)
	{
		this.driver = driver;
	}

	public WebElement getElementById(String id)
	{
		return driver.findElement(By.id(id));
	}

	public WebElement getElementByClass(String name)
	{
		return driver.findElement(By.className(name));
	}

	public WebElement getElementByCss(String css)
	{
		return driver.findElement(By.cssSelector(css));
	}

	public WebElement getElementByXPath(String xpath)
	{
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementByPartialLinkText(String content)
	{
		return driver.findElement(By.partialLinkText(content));
	}

	public WebElement getElementByLinkText(String content)
	{
		return driver.findElement(By.partialLinkText(content));
	}

	
	public void close()
	{
		driver.quit();
	}
	
	public void wait(int sec) throws InterruptedException
	{
		Thread.currentThread().sleep(1000*sec);
	}
	
	public void shot(String name)
	{
		String path = getScreenShotFilePath(name);
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try
		{
			FileUtils.copyFile(src, new File(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getScreenShotFilePath(String name)
	{
		String screenshot = ConfigUtils.getString("screenshot");
		String path = screenshot+File.separator+fmt.format(new Date())+File.separator+name+fmt1.format(new Date())+".png";
		return path;
	}
}
