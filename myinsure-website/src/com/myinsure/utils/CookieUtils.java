package com.myinsure.utils;

import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import java.math.BigInteger;

public class CookieUtils
{
	private static final String COOKIE_SEPARATOR = "#TK#";

	public static void put(Controller ctr, String key, String value)
	{
		put(ctr, key, value, 604800);
	}

	public static void put(Controller ctr, String key, BigInteger value)
	{
		put(ctr, key, value.toString());
	}

	public static void put(Controller ctr, String key, long value)
	{
		put(ctr, key, value + "");
	}

	public static void put(Controller ctr, String key, String value, int maxAgeInSeconds)
	{
		String encrypt_key = PropKit.get("encrypt_key");
		String saveTime = System.currentTimeMillis() + "";
		String encrypt_value = encrypt(encrypt_key, saveTime, maxAgeInSeconds + "", value);

		String cookieValue = encrypt_value + COOKIE_SEPARATOR + saveTime + COOKIE_SEPARATOR + maxAgeInSeconds + COOKIE_SEPARATOR + value;

		ctr.setCookie(key, cookieValue, maxAgeInSeconds);
	}

	private static String encrypt(String encrypt_key, String saveTime, String maxAgeInSeconds, String value)
	{
		return HashKit.md5(encrypt_key + saveTime + maxAgeInSeconds + value);
	}

	public static void remove(Controller ctr, String key)
	{
		ctr.removeCookie(key);
	}

	public static String get(Controller ctr, String key)
	{
		String encrypt_key = PropKit.get("encrypt_key");
		String cookieValue = ctr.getCookie(key);

		return getFromCookieInfo(encrypt_key, cookieValue);
	}

	public static String getFromCookieInfo(String encrypt_key, String cookieValue)
	{
		if (StringUtils.isNotBlank(cookieValue))
		{
			String[] cookieStrings = cookieValue.split(COOKIE_SEPARATOR);
			if ((null != cookieStrings) && (4 == cookieStrings.length))
			{
				String encrypt_value = cookieStrings[0];
				String saveTime = cookieStrings[1];
				String maxAgeInSeconds = cookieStrings[2];
				String value = cookieStrings[3];

				String encrypt = encrypt(encrypt_key, saveTime, maxAgeInSeconds, value);

				if ((encrypt_value != null) && (encrypt_value.equals(encrypt)))
				{
					long stime = Long.parseLong(saveTime);
					long maxtime = Long.parseLong(maxAgeInSeconds) * 1000L;

					if (stime + maxtime - System.currentTimeMillis() > 0L)
					{
						return value;
					}
				}
			}
		}
		return null;
	}

	public static Long getLong(Controller ctr, String key)
	{
		String value = get(ctr, key);
		return null == value ? null : Long.valueOf(Long.parseLong(value));
	}

	public static long getLong(Controller ctr, String key, long defalut)
	{
		String value = get(ctr, key);
		return null == value ? defalut : Long.parseLong(value);
	}

	public static Integer getInt(Controller ctr, String key)
	{
		String value = get(ctr, key);
		return null == value ? null : Integer.valueOf(Integer.parseInt(value));
	}

	public static int getLong(Controller ctr, String key, int defalut)
	{
		String value = get(ctr, key);
		return null == value ? defalut : Integer.parseInt(value);
	}
}