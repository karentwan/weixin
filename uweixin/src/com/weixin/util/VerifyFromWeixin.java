package com.weixin.util;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 验证来自服务器的数据，成为开发者
 * @author wan
 */
public class VerifyFromWeixin {
	
	public static Logger log = Logger.getLogger("verify");
	
	/**
	 * @param request 请求
	 * @param response 回应
	 * @param token 微信的token
	 */
	public static void verify(HttpServletRequest request, HttpServletResponse response, String token)throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if(checkSignature(signature, timestamp, nonce, token)) {
			out.print(echostr);
		}
		out.close();
		out = null;
		
	}
	
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		String[] arrs = {token, timestamp, nonce};
		//将这三个按照字典序排序
		Arrays.sort(arrs);
		//将这三个字符串拼接成一个长字符串
		StringBuilder content = new StringBuilder();
		for(int i = 0; i < arrs.length; i++) {
			content.append(arrs[i]);
		}
		//将拼接好了的字符串按照sha1算法加密
		MessageDigest md = null;
		String tempStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tempStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		return tempStr != null ? tempStr.equals(signature.toUpperCase()):false;
	}
	/**
	 * 将bytes数组中的每个字符都转换成16进制
	 * @param bytes
	 * @return
	 */
	public static String byteToStr(byte[] bytes) {
		String strDigest = "";
		for(int i = 0; i < bytes.length; i++) {
			strDigest += byteToHexStr(bytes[i]);
		}
		return strDigest;
	}
	
	public static String byteToHexStr(byte mByte) {
		char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F'};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0x0F];
		tempArr[1] = Digit[mByte & 0x0F];
		String s = new String(tempArr);
		return s;
	}
}
