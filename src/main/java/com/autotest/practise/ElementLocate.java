package com.autotest.practise;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autotest.common.ChromeDriverImpl;
import com.autotest.common.DriverFactory;

public class ElementLocate
{
	static String url = "https://www.jd.com";
	static String DRIVER_NAME="webdriver.chrome.driver";
	static String DRIVER_VALUE = "D:\\Google\\chromedriver.exe";
	
	public static void main(String[] args)
	{
		try
		{
			elementLocate();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static WebDriver getDriver()
	{
		DriverFactory driver = new ChromeDriverImpl();
		WebDriver dirver = driver.getDriver(url);
		return dirver;
	}
	
	public static void elementLocate() throws InterruptedException
	{
		WebDriver driver = getDriver();
		try
		{
//			byId(driver);
//			byClass(driver);
//			byLinkText(driver);
//			byPartialLinkText(driver);
//			byPartialLinkTexts(driver);
//			byXpath(driver);
			byCssSelect(driver);
			
			
			System.out.println("当前打开页面的标题是： " + driver.getTitle());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Thread.currentThread().sleep(1000*5);
			driver.quit();
		}
	
	}
	
	public static void byCssSelect(WebDriver driver)
	{
		WebElement e = driver.findElement(By.cssSelector("#J_cate > ul > li:nth-child(4) > a:nth-child(1)"));
		e.click();
		System.out.println("css选择器定位 :"+e.getText());
	}

	public static void byXpath(WebDriver driver)
	{
		WebElement e = driver.findElement(By.xpath("//*[@id=\"J_cate\"]/ul/li[4]/a[1]"));
			e.click();
			System.out.println("xpath定位 :"+e.getText());
	}

	//当找到多个时，全部打开
	public static void byPartialLinkTexts(WebDriver driver)
	{
		List<WebElement> el = driver.findElements(By.partialLinkText("汽"));
		int i = 1;
		for(WebElement e:el)
		{
			e.click();
			System.out.println("部分连接文本定位多个 第"+i+"个： "+e.getText());
		}
	}
	
	//连接部分文本内容
	public static void byPartialLinkText(WebDriver driver)
	{
		WebElement el = driver.findElement(By.partialLinkText("汽车用"));
		el.click();
		System.out.println("部分文本定位："+el.getText());
	}

	//链接文本
	public static void byLinkText(WebDriver driver)
	{
		WebElement el = driver.findElement(By.linkText("手机"));
		el.click();
		System.out.println("文本定位："+el.getText());
	}

	public static void byClass(WebDriver driver)
	{
		WebElement el = driver.findElement(By.className("cate_menu_lk"));
		el.click();
		System.out.println("class定位："+el.getText());
	}

	public static void byId(WebDriver driver)
	{
		WebElement el = driver.findElement(By.id("key"));
		el.sendKeys(Keys.RETURN);
	}

}
