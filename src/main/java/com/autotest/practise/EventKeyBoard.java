package com.autotest.practise;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autotest.common.ChromeDriverImpl;
import com.autotest.common.DriverFactory;
import com.autotest.common.DriverHelper;

public class EventKeyBoard
{
	static String url = "https://www.jd.com";

	public static void main(String[] args)
	{
		try
		{
			keyEnter();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private static void keyEnter() throws InterruptedException
	{
		DriverFactory driver = new ChromeDriverImpl();
		WebDriver dr = driver.getDriver(url);
		DriverHelper dh = new DriverHelper(dr);
		try
		{
			WebElement ele = dh.getElementById("key");
			ele.sendKeys("床上用品");
			ele.sendKeys(Keys.RETURN);

		}
		catch (Exception e1)
		{
			// 等待5秒后，仍未找到元素，则抛出异常
			System.out.println("异常，退出浏览器");
		}
		finally
		{
			// 最终退出
			dh.wait(5);
			dh.close();
		}
	}
}
