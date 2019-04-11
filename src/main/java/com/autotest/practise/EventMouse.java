package com.autotest.practise;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autotest.common.ChromeDriverImpl;
import com.autotest.common.DriverFactory;
import com.autotest.common.DriverHelper;

public class EventMouse
{
	static String url = "https://www.jd.com";
	
	public static void main(String[] args)
	{
		try
		{
			mouseOver();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 5秒以内，没半秒去找一次元素，找到就返回，继续往下执行
	 * 
	 * ljh
	 * @throws InterruptedException 
	 */
	private static void mouseOver() throws InterruptedException  
	{
		DriverFactory driver = new ChromeDriverImpl();
		WebDriver dr = driver.getDriver(url);
		DriverHelper dh = new DriverHelper(dr);
		WebElement ele = dh.getElementByLinkText("手机");
		Actions action = new Actions(dr);
		action.moveToElement(ele).perform();

		SimpleDateFormat fmt = new SimpleDateFormat("mm:ss");
		System.out.println(fmt.format(new Date()));
		//5秒以内，没半秒去找一次元素，找到就返回，继续往下执行
		WebDriverWait wait = new WebDriverWait(dr, 5);
		// 等待，以防页面还未完全渲染就开始找元素，导致元素未找到，找到元素后往后执行
		try
		{
			WebElement ele1 = wait.until(new ExpectedCondition<WebElement>()
			{
				@Override
				public WebElement apply(WebDriver d)
				{
					return d.findElement(By.partialLinkText("老人机"));
				}
			});
			//找到就返回，两次时间相隔可能很短，可能网络原因没有在时间内找到元素，也有可能元素名称错误
			System.out.println(fmt.format(new Date()));
			ele1.click();
		}
		catch (Exception e1)
		{
			//等待5秒后，仍未找到元素，则抛出异常
			System.out.println(fmt.format(new Date()));
			System.out.println("异常，退出浏览器");
		}
		finally
		{
			//最终退出
			dh.wait(5);
			dh.close();
		}
	}
	
	private static void mouseOver1() throws InterruptedException
	{
		String url = "https://www.jd.com";
		DriverFactory driver = new ChromeDriverImpl();
		WebDriver dr = driver.getDriver(url);
		DriverHelper dh = new DriverHelper(dr);
		WebElement ele = dh.getElementByLinkText("手机");
		Actions action = new Actions(dr);
		action.moveToElement(ele).perform();

		//线程等待
		dh.wait(5);

		WebElement ele1 = dh.getElementByLinkText("老人机");
		ele1.click();
		
		//线程等待
		dh.wait(5);

		dh.close();
	}
	
	/**
	 * 没有等待时间，会报找不到老人机这个元素
	 * 
	 * ljh
	 */
	private static void mouseOver_Exception()
	{
		String url = "https://www.jd.com";
		DriverFactory driver = new ChromeDriverImpl();
		WebDriver dr = driver.getDriver(url);
		DriverHelper dh = new DriverHelper(dr);
		WebElement ele = dh.getElementByLinkText("手机");
		Actions action = new Actions(dr);
		action.moveToElement(ele).perform();

		WebElement ele1 = dh.getElementByLinkText("老人机");
		ele1.click();
		dh.close();
	}
	
	
}
