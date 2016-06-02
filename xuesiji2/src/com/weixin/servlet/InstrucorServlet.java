package com.weixin.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 辅导员真正的界面
 * @author wan
 */
public class InstrucorServlet extends HttpServlet{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//读取cookie
		Cookie[] cookies = request.getCookies();
		String name = null;
		String psd = null;
		
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
	
}
