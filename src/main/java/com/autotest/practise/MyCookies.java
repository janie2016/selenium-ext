package com.autotest.practise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autotest.common.ChromeDriverImpl;
import com.autotest.common.DriverFactory;
import com.autotest.common.DriverHelper;
import com.autotest.common.MyCookie;


/**
 * TODO 保存下了Cookie但是，没能绕过登录
 * @author u
 *
 */
public class MyCookies
{
	static String url = "https://www.jd.com";
	public static void main(String[] args)
	{
		sendCookies();
//		try
//		{
//			DriverHelper dh = getDriver();
//			login(dh);
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private static void sendCookies()
	{
		DriverFactory driver = new ChromeDriverImpl();
		List<Cookie> cookies = loadCookies();
		WebDriver dr = driver.getDriver("https://order.jd.com/center/list.action");
		for(Cookie cookie : cookies)
		{
			System.out.println(cookie);
			dr.manage().addCookie(cookie);
		}
		dr.navigate().refresh();
	}
	private static DriverHelper getDriver()
	{
		DriverFactory driver = new ChromeDriverImpl();
		WebDriver dr = driver.getDriver(url);
		DriverHelper dh = new DriverHelper(dr);
		
		return dh;
	}
	
	public static  void login(DriverHelper dh) throws IOException
	{
		WebElement tologin = dh.getElementByClass("link-login");
		tologin.click();
		
		dh.getElementByLinkText("账户登录").click();
		dh.getElementById("loginname").sendKeys("17603055339");
		dh.getElementById("nloginpwd").sendKeys("ljh1991");
		dh.getElementById("loginsubmit").click();
		
		saveCookies(dh.getDriver());
	}

	private static void saveCookies(WebDriver driver) throws IOException
	{
		// TODO Auto-generated method stub
		Set<Cookie> cookies = driver.manage().getCookies();
		
		String str = MyCookie.cookies(cookies);
		System.out.println(str);
		
		String filepath = "D:\\selenium\\cookie\\mycookie.txt";
		File file = new File(filepath);
		if (!(file.exists())) FileUtils.writeStringToFile(file, "");
		FileWriter fw = new FileWriter(filepath, true);
		fw.write(str + "\n");
		fw.close();
		
//		Cookie cookie = new Cookie("","");
	}
	
	public static List<Cookie> loadCookies()
	{
		String filepath = "D:\\selenium\\cookie\\mycookie.txt";
		return loadCookies(filepath);
	}
	
	
	public static List<Cookie> loadCookies(String filePath)
	{
		try
		{
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists())
			{
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String str1 = null;
				while ((str1 = bufferedReader.readLine()) != null)
				{
					return MyCookie.strToCookies(str1);
				}
				read.close();
			}
			else
			{
				System.out.println("找不到指定的文件");
			}
		}
		catch (Exception e)
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}
}
