package com.weixin.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����Ա�����Ľ���
 * @author wan
 */
public class InstrucorServlet extends HttpServlet{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//��ȡcookie
		Cookie[] cookies = request.getCookies();
		String name = null;
		String psd = null;
		
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
	
}
