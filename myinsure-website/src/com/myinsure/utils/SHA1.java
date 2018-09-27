package com.myinsure.utils;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SHA1
{
    /** 
    * SHA1 安全加密算法 
    * @param maps 参数key-value map集合 
    * @return 
    * @throws DigestException  
    */
    public static String sha1(Map<String, Object> maps) throws DigestException
    {
	// 获取信息摘要 - 参数字典排序后字符串
	String decrypt = getOrderByLexicographic(maps);
	System.out.println(decrypt);
	try
	{
	    // 指定sha1算法
	    MessageDigest digest = MessageDigest.getInstance("SHA-1");
	    digest.update(decrypt.getBytes());
	    // 获取字节数组
	    byte messageDigest[] = digest.digest();
	    // Create Hex String
	    StringBuffer hexString = new StringBuffer();
	    // 字节数组转换为 十六进制 数
	    for (int i = 0; i < messageDigest.length; i++)
	    {
		String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
		if (shaHex.length() < 2)
		{
		    hexString.append(0);
		}
		hexString.append(shaHex);
	    }
	    return hexString.toString().toUpperCase();

	} catch (NoSuchAlgorithmException e)
	{
	    e.printStackTrace();
	    throw new DigestException("签名错误！");
	}
    }

    /** 
     * 获取参数的字典排序 
     * @param maps 参数key-value map集合 
     * @return String 排序后的字符串 
     */
    private static String getOrderByLexicographic(Map<String, Object> maps)
    {
	System.out.println(splitParams(lexicographicOrder(getParamsName(maps)), maps));
	// jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
	// jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
	return splitParams(lexicographicOrder(getParamsName(maps)), maps);
    }

    /** 
     * 获取参数名称 key 
     * @param maps 参数key-value map集合 
     * @return 
     */
    private static List<String> getParamsName(Map<String, Object> maps)
    {
	List<String> paramNames = new ArrayList<String>();
	for (Map.Entry<String, Object> entry : maps.entrySet())
	{
	    paramNames.add(entry.getKey());
	}
	return paramNames;
    }

    /** 
     * 参数名称按字典排序 
     * @param paramNames 参数名称List集合 
     * @return 排序后的参数名称List集合 
     */
    private static List<String> lexicographicOrder(List<String> paramNames)
    {
	Collections.sort(paramNames);
	return paramNames;
    }

    /** 
     * 拼接排序好的参数名称和参数值 
     * @param paramNames 排序后的参数名称集合 
     * @param maps 参数key-value map集合 
     * @return String 拼接后的字符串 
     */
    private static String splitParams(List<String> paramNames, Map<String, Object> maps)
    {
	StringBuilder paramStr = new StringBuilder();
	for (String paramName : paramNames)
	{
	    paramStr.append(paramName);
	    for (Map.Entry<String, Object> entry : maps.entrySet())
	    {
		if (paramName.equals(entry.getKey()))
		{
		    paramStr.append("=" + String.valueOf(entry.getValue()) + "&");
		}
	    }
	}
	paramStr.deleteCharAt(paramStr.length() - 1);
	return paramStr.toString();
    }

    public static void main(String [] args)
    {
	String decrypt = "jsapi_ticket=HoagFKDcsGMVCIY2vOjf9jYw9tyPTeDS5KueO76fGDqOI-Sl__mlDznVLsYxDJ1DeVOv-WzhOb9Ygh43lXMy9Q&noncestr=sss&timestamp=1523850435000&url=http://10.1.9.21:8080/mybsc/wxuser/setup?openid=o9l-B0sJ3JNBjvtjiHTYPP7F7Zts";
	try
	{
	    // 指定sha1算法
	    MessageDigest digest = MessageDigest.getInstance("SHA-1");
	    digest.update(decrypt.getBytes());
	    // 获取字节数组
	    byte messageDigest[] = digest.digest();
	    // Create Hex String
	    StringBuffer hexString = new StringBuffer();
	    // 字节数组转换为 十六进制 数
	    for (int i = 0; i < messageDigest.length; i++)
	    {
		String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
		if (shaHex.length() < 2)
		{
		    hexString.append(0);
		}
		hexString.append(shaHex);
	    }
	    String upperCase = hexString.toString().toUpperCase();
	    System.out.println(upperCase);

	} catch (NoSuchAlgorithmException e)
	{
	    e.printStackTrace();
	    System.out.println("签名错误！");
	}
    }

}
