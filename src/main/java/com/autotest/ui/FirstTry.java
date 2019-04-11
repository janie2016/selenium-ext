package com.autotest.ui;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTry
{

	public static void main(String[] args)
	{

		System.setProperty("webdriver.chrome.driver", "D:\\Google\\chromedriver.exe");
		// 实例化一个Chrome浏览器的实例
		WebDriver driver = new ChromeDriver();
		// 设置打开的浏览器窗口最大化
		driver.manage().window().maximize();
		// 设置隐性的等待时间
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		// 使用get()打开一个网站
		driver.get("https://www.baidu.com");
		// getTitle()获取当前页面的title，用System.out.println()打印在控制台
		System.out.println("当前打开页面的标题是： " + driver.getTitle());
//		WebElement els = driver.findElement(By.xpath("//*[@id=\"register\"]/form/div[2]/div/div/input"));
		WebElement els = driver.findElement(By.tagName("input"));
		System.out.println(els.getText());
		System.out.println(els.getTagName());
		// 关闭浏览器
		driver.quit();
	}
	
	@Test
	public void test()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Google\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.baidu.com");
		List<WebElement> links = driver.findElements(By.cssSelector("#u1 a"));
		// 获取集合中元素的总数量并转为String格式（下方assertEquals方法参数格式要求）
		String size = String.valueOf(links.size());
		// String size = Integer.toString(links.size());
		// String size = links.size()+"";
		// 验证链接数量
		// SeleneseTestNgHelper.assertEquals("8", size);

		// 打印href属性
		// 通过for循环获得list中的所有元素，再调用getAttribute()方法得到元素的属性
		for (int i = 0; i < links.size(); i++)
		{
			System.out.println(links.get(i).getAttribute("href"));
		}
		driver.quit();
	}
}
