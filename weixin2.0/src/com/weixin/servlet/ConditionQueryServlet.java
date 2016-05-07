package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.dao.SignInDao;

public class ConditionQueryServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("null")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = null;
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("utf8");
			response.setCharacterEncoding("utf8");
			out = response.getWriter();
			sb =SignInDao.getInstance().queryCondition();
			out.print(sb.toString());
		} catch (IOException e) {
			out.print("·þÎñÆ÷³ö´í£¡sorry!");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

}
