package com.weixin.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作cookie的工具类
 * @author wan
 */
public class CookieUtil {
	
	/**
	 * 向浏览器添加cookie
	 * @param response 响应
	 * @param name Cookie的nme
	 * @param value Cookie的value
	 * @param maxAge Cookie的生命周期
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if( maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	public static Map<String, Cookie> readCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Map<String, Cookie> map = new HashMap<String, Cookie>();
		if( cookies != null) {
			for( Cookie c : cookies) {
				map.put(c.getName(), c);
			}
		}
		return map;
	}
	
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> map = readCookie(request);
		return map.get(name);
	}
	
	
}
