package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.OrderDao;

/**
 * Ô¤Ô¼¹¦ÄÜ
 * @author wan
 */
public class OrderServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String tel = request.getParameter("tel");
		String time = request.getParameter("time");
		int code = OrderDao.getInstance().insert(name, age, tel, time);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(code);
		} catch (IOException e) {
			out.print(-1);
			e.printStackTrace();
		}
	}

}
