package com.weixin.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����cookie�Ĺ�����
 * @author wan
 */
public class CookieUtil {
	
	/**
	 * ����������cookie
	 * @param response ��Ӧ
	 * @param name Cookie��nme
	 * @param value Cookie��value
	 * @param maxAge Cookie����������
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
