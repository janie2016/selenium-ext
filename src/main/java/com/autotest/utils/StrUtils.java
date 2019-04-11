package com.autotest.utils;

public class StrUtils
{

	public static boolean isNull(String s)
	{
		return ((s == null) || (s.equals("")) || (s.equalsIgnoreCase("null")) || (s.equalsIgnoreCase("undefined")));
	}

	public static boolean isNotNull(String s)
	{
		return (!(isNull(s)));
	}

	public static String trim(String s)
	{
		if (s == null)
		{
			return s;
		}
		return s.trim();
	}

	public static boolean isNumber(String s)
	{
		if (isNull(s))
		{
			return false;
		}

		return (s.matches("^(-?\\d+)(\\.\\d+)?$"));
	}

	public static boolean isInteger(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^-?\\d+$");
		}
		return false;
	}

	public static boolean isDate(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");
		}
		return false;
	}

	public static boolean isDateTime(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");
		}

		return false;
	}

	public static boolean isPhoneNo(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");
		}
		return false;
	}

	public static boolean isMobilePhoneNo(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^13\\d{9}$");
		}
		return false;
	}

	public static boolean isEMail(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		}
		return false;
	}

	public static boolean isURL(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$");
		}
		return false;
	}

	public static boolean isIP(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$");
		}
		return false;
	}

	public static boolean isVariableName(String s)
	{
		if (isNotNull(s))
		{
			return s.matches("^([a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*)");
		}
		return false;
	}

	private static boolean isEqualsIgnoreCase(char c1, char c2)
	{
		if ((('a' <= c1) && (c1 <= 'z')) || (('A' <= c1) && (c1 <= 'Z') && (((('a' <= c2) && (c2 <= 'z')) || (('A' <= c2) && (c2 <= 'Z') && (((c1 - c2 == 32) || (c2 - c1 == 32))))))))
		{
			return true;
		}
		return (c1 == c2);
	}

	public static int indexOfIgnoreCase(String parent, String child)
	{
		return indexOfIgnoreCase(parent, child, -1);
	}

	public static int indexOfIgnoreCase(String parent, String child, int fromIndex)
	{
		if ((parent == null) || (child == null))
		{
			return -1;
		}
		fromIndex = (fromIndex < 0) ? 0 : fromIndex;
		if (child.equals(""))
		{
			return ((fromIndex >= parent.length()) ? parent.length() : fromIndex);
		}
		int index1 = fromIndex;
		int index2 = 0;

		while (index1 < parent.length())
		{
			char c1 = parent.charAt(index1);
			char c2 = child.charAt(index2);
			while (true)
			{
				if (!(isEqualsIgnoreCase(c1, c2))) break ;;
				if ((index1 >= parent.length() - 1) || (index2 >= child.length() - 1)) break;
				c1 = parent.charAt(++index1);
				c2 = child.charAt(++index2);
			}
			if (index2 != child.length() - 1) break;
			return fromIndex;

		}
		return -1;
	}

	public static int lastIndexOfIgnoreCase(String parent, String child)
	{
		if (parent == null)
		{
			return -1;
		}
		return lastIndexOfIgnoreCase(parent, child, parent.length());
	}

	public static int lastIndexOfIgnoreCase(String parent, String child, int fromIndex)
	{
		if ((parent == null) || (child == null))
		{
			throw new NullPointerException("输入的参数为空");
		}
		if (child.equals(""))
		{
			return ((fromIndex >= parent.length()) ? parent.length() : fromIndex);
		}
		fromIndex = (fromIndex >= parent.length()) ? parent.length() - 1 : fromIndex;

		int index1 = fromIndex;
		int index2 = 0;

		while (index1 >= 0)
		{
			char c1 = parent.charAt(index1);
			char c2 = child.charAt(index2);
			while (true)
			{
				if (!(isEqualsIgnoreCase(c1, c2))) break ;
				if ((index1 >= parent.length() - 1) || (index2 >= child.length() - 1)) break;
				c1 = parent.charAt(++index1);
				c2 = child.charAt(++index2);
			}
			if (index2 != child.length() - 1) break;
			return fromIndex;

		}
		return -1;
	}


}
