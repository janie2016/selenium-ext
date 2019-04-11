package com.autotest.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openqa.selenium.Cookie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.autotest.utils.StrUtils;

public class MyCookie extends TreeMap
{
	static SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
	private static final long serialVersionUID = 1L;
	private static final String boolTrueValue = "true;1;yes;ok";
	private static final boolean boolNullValue = false;
	private static final boolean boolEmptyValue = false;
	private static ValueFilter filter = new ValueFilter()
	{
		public Object process(Object obj, String name, Object value)
		{
			if (value == null) return "";
			if ((value instanceof BigDecimal) || (value instanceof Double) || (value instanceof Float))
			{
				return new BigDecimal(value.toString());
			}
			return value;
		}
	};
	public MyCookie()
	{
	}

	public MyCookie(Map map)
	{
		if (map != null) putAll(map);
	}

	public Object put(Object key, Object value)
	{
		String k = String.valueOf(key);
		return super.put(k, value);
	}

	public Object get(Object key)
	{
		return super.get(key);
	}
	
	public boolean getBoolean(String key)
	{
		if(containsKey(key))
		{
			return Boolean.parseBoolean(key);
		}
		return false;
	}
	public String getString(String key)
	{
		String v = null;
		if (containsKey(key))
		{
			v = String.valueOf(get(key));
		}
		return v;
	}

	public Double getDouble(String key)
	{
		if (containsKey(key))
		{
			String v = getString(key);
			Double d = Double.valueOf(Double.parseDouble(v));
			return d;
		}
		return null;
	}

	public Double getDouble(String key, Double defaultValue)
	{
		Double d = getDouble(key);
		if (d != null)
		{
			return d;
		}
		return defaultValue;
	}

	public int getInt(String key, int defaultValue)
	{
		Integer v = getInteger(key);
		if (v == null)
		{
			return defaultValue;
		}
		return v.intValue();
	}

	public int getInt(String key)
	{
		return getInt(key, 0);
	}

	public Integer getInteger(String key)
	{
		if (containsKey(key))
		{
			String v = getString(key);
			try
			{
				int i = Integer.parseInt(v);
				return Integer.valueOf(i);
			}
			catch (Exception ex)
			{
				return null;
			}
		}
		return null;
	}

	public Long getLong(String key)
	{
		if (containsKey(key))
		{
			String v = getString(key);
			long l = Long.parseLong(v);
			return Long.valueOf(l);
		}
		return null;
	}


	public MyCookie clone()
	{
		MyCookie out = new MyCookie();
		out.putAll(this);
		return out;
	}

	public String toString()
	{
		return JSON.toJSONString(this, filter, new SerializerFeature[0]);
	}

	public static String cookies(Set<Cookie> cookies)
	{
		List<MyCookie> list = new ArrayList<MyCookie>();
		for(Cookie cookie : cookies)
		{
			MyCookie ck = new MyCookie();
			ck.put("name", cookie.getName());
			ck.put("value", cookie.getValue());
			ck.put("path", cookie.getPath());
			ck.put("domain", cookie.getDomain());
			ck.put("expiry", (cookie.getExpiry() != null) ? fmt.format(cookie.getExpiry()): "");
			ck.put("isSecure", cookie.isSecure());
			ck.put("isHttpOnly", cookie.isHttpOnly());
			list.add(ck);
		}
		return list.toString();
	}
	
	public static List<Cookie> strToCookies(String str) throws ParseException
	{
		List<Cookie> outlist = new ArrayList<Cookie>();
		List<MyCookie> list = strToList(str);
		for(MyCookie ck: list)
		{
			String name = ck.getString("name");
			String value = ck.getString("value");
			String domain = ck.getString("domain");
			String path = ck.getString("path");
			String expiry = ck.getString("expiry");
			boolean isSecure = ck.getBoolean("isSecure");
			boolean isHttpOnly = ck.getBoolean("isHttpOnly");
			
			Date date = StrUtils.isNull(expiry)? null : fmt.parse(expiry);
			
			Cookie cookie = new Cookie(name, value, domain, path,date,isSecure,isHttpOnly);
			outlist.add(cookie);
		}
		return outlist;
	}
	

	public static MyCookie jsonToCookie(JSONObject json)
	{
		if ((json == null) || (json.isEmpty())) return null;
		MyCookie result = new MyCookie();
		Iterator it = json.keySet().iterator();
		while (it.hasNext())
		{
			String key = (String) it.next();
			result.put(key, convertJson(json.get(key)));
		}
		return result;
	}
	
	public static MyCookie strToMap(String json)
	{
		if (StrUtils.isNull(json)) return null;
		try
		{
			return jsonToMap(JSONObject.parseObject(json));
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static List strToList(String json)
	{
		if (StrUtils.isNull(json)) return null;
		try
		{
			return jsonToList(JSONArray.parseArray(json));
		}
		catch (Exception e)
		{
		}
		return null;
	}
	
	private static Object convertJson(Object json)
	{
		if (json == null) return null;
		if (json instanceof JSONArray)
		{
			List list = jsonToList((JSONArray) json);
			return list;
		}
		if (json instanceof JSONObject)
		{
			MyCookie map = jsonToMap((JSONObject) json);
			return map;
		}
		return json;
	}

	public static List jsonToList(JSONArray json)
	{
		if (json == null) return null;
		List result = new ArrayList();
		for (int i = 0; i < json.size(); ++i)
		{
			result.add(convertJson(json.get(i)));
		}
		return result;
	}

	public static MyCookie jsonToMap(JSONObject json)
	{
		if ((json == null) || (json.isEmpty())) return null;
		MyCookie result = new MyCookie();
		Iterator it = json.keySet().iterator();
		while (it.hasNext())
		{
			String key = (String) it.next();
			result.put(key, convertJson(json.get(key)));
		}
		return result;
	}

	public static MyCookie strToCookie(String json)
	{
		if (StrUtils.isNull(json)) return null;
		try
		{
			return jsonToMap(JSONObject.parseObject(json));
		}
		catch (Exception e)
		{
		}
		return null;
	}
}
