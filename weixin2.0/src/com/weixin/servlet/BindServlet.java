package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.dao.BindDao;

/**
 * ��ѧ����Ϣ��servlet
 * @author wan
 */
public class BindServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String account = request.getParameter("account");
			String name = request.getParameter("name");
			String clazz = request.getParameter("class");
			String openId = request.getParameter("openId");
			//��ѧ�Ÿ�΢�ź�
			int code = BindDao.getInstance().bind(account, name, clazz, openId);
			out = response.getWriter();
			out.println(code);
		} catch (UnsupportedEncodingException e) {
			out.println(-1);
			e.printStackTrace();
		} catch (IOException e) {
			out.println(-1);
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
