package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.BindDao;

/**
 * °ó¶¨Ò³Ãæ
 * @author wan
 */
public class BindServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("userName");
		String openId = request.getParameter("openId");
		int code = BindDao.getInstance().bind(name, openId);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(code);
		} catch (IOException e) {
			out.print(-1);
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		doGet(request, response);
	}

}
