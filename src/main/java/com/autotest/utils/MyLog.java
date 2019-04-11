package com.autotest.utils;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;

public class MyLog
{

	private Logger logger;
	private String name;
	private static Hashtable<String, Logger> loggerMap = new Hashtable();

	private MyLog(String name)
	{
		this.name = name;
		this.logger = ((Logger) loggerMap.get(name));
		if (this.logger == null)
		{
			this.logger = Logger.getLogger(name);
			loggerMap.put(name, this.logger);
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static MyLog log(String name)
	{
		return new MyLog(name);
	}

	public void debug(String message)
	{
		internal("DEBUG", message);
	}

	public void error(String message)
	{
		internal("ERROR", message);
	}

	public void fatal(String message)
	{
		internal("FATAL", message);
	}

	public void info(String message)
	{
		internal("INFO", message);
	}

	private void internal(String level, String message)
	{
		String logType = ConfigUtils.getString("logType");
		if (StrUtils.isNull(logType)) logType = "log4j";
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		Map<String, Object> log = new HashMap<String, Object>();
		log.put("level", level);
		if (stack.length >= 3)
		{
			log.put("rpath", stack[3].getClassName() + "." + stack[3].getMethodName());

			log.put("rline", Integer.valueOf(stack[3].getLineNumber()));
		}
		log.put("catalog", this.name);
		log.put("ts", Long.valueOf(System.currentTimeMillis()));
		log.put("content", message);
		try
		{
			appendToFile(log.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Map<String, String> log(Map<String, String> inMap) throws Exception
	{
		Map<String, String>  outMap = new HashMap<String, String> ();
		String catalog = inMap.get("catalog");
		String content = inMap.get("content");
		String filePath = getFilePath(catalog);
		File file = new File(filePath);
		if (!(file.exists())) FileUtils.writeStringToFile(file, "");
		FileWriter fw = new FileWriter(filePath, true);
		fw.write(content + "\n");
		fw.close();
		return outMap;
	}

	public void appendToFile(String content) throws Exception
	{
		JSONObject json = JSONObject.parseObject(content);
		String catalog = json.getString("catalog");
		String frameworkLogEnable = ConfigUtils.getString("frameworkLogEnable", "false");

		if ((catalog.startsWith("framework")) && (!("true".equals(frameworkLogEnable.toLowerCase()))))
		{
			return;
		}
		String filePath = getFilePath(catalog);
		File file = new File(filePath);
		if (!(file.exists())) FileUtils.writeStringToFile(file, "");
		FileWriter fw = new FileWriter(filePath, true);
		fw.write(formatContent(json) + "\n");
		fw.close();
	}

	public String formatContent(JSONObject json)
	{
		StringBuffer logs = new StringBuffer();

		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss,SSS");
		logs.append(df.format(new Date(json.getLong("ts").longValue())));
		logs.append(" " + json.getString("rid"));
		logs.append(" [" + json.getString("rpath") + ":" + json.getString("rline") + "]");

		logs.append(" " + json.getString("level"));
		logs.append(" " + json.getString("content"));

		return logs.toString();
	}

	public String getFilePath(String catalog)
	{
		String fileRoot = ConfigUtils.getString("fileRoot");
		if (StrUtils.isNull(fileRoot)) fileRoot = "/app/fileRoot/logs";
		DateFormat df = new SimpleDateFormat("YYYYMMdd");
		String ymd = df.format(new Date());
		String file = fileRoot + "/logs/" + ymd + "/" + catalog + ".txt";
		return file;
	}

	public void error(Exception e)
	{
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] sts = e.getStackTrace();
		for (StackTraceElement st : sts) {
			String linec = st.getClassName() + ":" + st.getLineNumber() + " " + st.getMethodName() + "()";
			error(linec);
			sb.append(linec).append(";");
		}
	}
}
