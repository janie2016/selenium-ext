package com.autotest.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils
{
	private static Map<String, String> map;
	static MyLog log = MyLog.log("config");
	
	public static synchronized void init()
	{
		if (map != null) return;
		try
		{
			map = new HashMap<String, String>();
			InputStream in = ConfigUtils.class.getResourceAsStream("/Config.properties");

			Properties appConfig = new Properties();
			appConfig.load(in);
			Iterator it = appConfig.keySet().iterator();
			while (it.hasNext())
			{
				String key = String.valueOf(it.next()).trim();
				String value = appConfig.getProperty(key).trim();
				map.put(key, value);
			}
			if (map.containsKey("driverClassName")) Class.forName(map.get("driverClassName"));
		}
		catch (Exception ex)
		{
			log.error(ex);
		}
	}

	public static boolean hasPro(String key)
	{
		init();
		if (key == null)
		{
			return false;
		}
		return map.containsKey(key);
	}

	public static boolean getBoolean(String key)
	{
		String value = getString(key);

		return ("true".equals(value));
	}

	public static String getString(String key)
	{
		if (hasPro(key))
		{
			return map.get(key);
		}
		return null;
	}

	public static String getString(String key, String defaultValue)
	{
		if (hasPro(key))
		{
			return map.get(key);
		}
		return defaultValue;
	}

	public static Integer getInt(String key)
	{
		if (hasPro(key))
		{
			return Integer.parseInt(map.get(key));
		}
		return null;
	}

	public static Map<String, String> getAll()
	{
		init();
		return new HashMap(map);
	}

	public static File getFile(String path)
	{
		init();
		String fileRoot = map.get("fileRoot");
		File file = new File(fileRoot + "/" + path);
		return file;
	}

	public static boolean release()
	{
		init();
		String version = map.get("versionMode").toLowerCase();

		return ("release".equals(version));
	}

	public static boolean localCache()
	{
		init();
		String localCache = map.get("localCache").toLowerCase();

		return ("true".equals(localCache));
	}

}
