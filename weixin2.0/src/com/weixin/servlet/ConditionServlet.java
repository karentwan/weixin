package com.weixin.servlet;

import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.SignInDao;

public class ConditionServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			String floor = request.getParameter("floor");
			Integer.parseInt(floor);
			String dormitory = request.getParameter("dormitory");
			Integer.parseInt(dormitory);
			int personCount = Integer.parseInt(request.getParameter("personCount"));
			String contacts = request.getParameter("contacts");
			contacts =URLDecoder.decode( contacts ,"utf-8");
System.out.println("contacts:" + contacts);
			String tel = request.getParameter("tel");
			int code = SignInDao.getInstance().insertDormitory(floor, dormitory, personCount, contacts, tel);
			out.print(code);
		} catch (Exception e) {
			out.print("-1");
		} 
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
