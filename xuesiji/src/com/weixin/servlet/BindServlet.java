package com.weixin.servlet;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.BindDao;

/**
 * 绑定学号信息的servlet
 * @author wan
 */
public class BindServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String account = request.getParameter("account");
		String psd = request.getParameter("psd");
		String name = request.getParameter("name");
		String instructor = request.getParameter("instructor");
		String clazz = request.getParameter("class");
		String openId = request.getParameter("openId");
		//绑定学号跟微信号
		BindDao.getInstance().bind(account, name, psd, instructor, clazz, openId);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
