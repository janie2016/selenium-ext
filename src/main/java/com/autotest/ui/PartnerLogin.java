package com.autotest.ui;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class PartnerLogin
{

	public static void main(String[] args)
	{
		login();
	}
	
	private static void login()
	{
//		
		
		System.setProperty("webdriver.chrome.driver", "D:\\Google\\chromedriver.exe");
		// 实例化一个Chrome浏览器的实例
		WebDriver driver = new ChromeDriver();
//		 设置打开的浏览器窗口最大化
		driver.manage().window().maximize();
		// 设置隐性的等待时间
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		// 使用get()打开一个网站
		driver.get("http://192.168.9.86:8006/partner/");
		// getTitle()获取当前页面的title，用System.out.println()打印在控制台
		System.out.println("当前打开页面的标题是： " + driver.getTitle());
//		 关闭浏览器
		List<WebElement> els = driver.findElements(By.tagName("input"));
		for(WebElement e : els)
		{
			
			System.out.println(e.getTagName());
		}
		
		List<WebElement> els1 = driver.findElements(By.className("el-input__inner"));
		for(WebElement e : els1)
		{
			
			System.out.println(e.getTagName());
			System.out.println(e.getAttribute("placeholder"));
			if("账号".equals(e.getAttribute("placeholder")))
			{
				e.sendKeys("yiche");
			}
			else
			{
				e.sendKeys("123456");
			}
		}
		WebElement bt = driver.findElement(By.tagName("button"));
		bt.click();
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		System.out.println(driver.getWindowHandle());
		Set<String> set = driver.getWindowHandles();
		for(String s : set)
		{
			System.out.println(s);
		}
//		WebElement es = driver.findElement(By.className("wrapper goods"));
		WebElement ess = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div/div/div[1]/div[2]"));
		Actions action = new Actions(driver);
		action.moveToElement(ess);
		List<WebElement> e = driver.findElements(By.tagName("button"));
		e.get(0).click();
		
		/*System.out.println("*************************************");
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		System.out.println(driver.getWindowHandle());
		Set<String> set1 = driver.getWindowHandles();
		for(String s : set1)
		{
			System.out.println(s);
		}*/
//		driver.quit();
	}
}
