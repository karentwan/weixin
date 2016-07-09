package com.weixin.abstractServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractQuery extends HttpServlet{ 
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String time = request.getParameter("time");
		String name = request.getParameter("name");
		query(name, time);
		String str = getResponse();
		PrintWriter out = response.getWriter();
		out.print(str);
	}

	/**
	 * 查询结果
	 * @param name 辅导员工号
	 * @param time 时间
	 */
	public abstract void query(String name, String time);
	
	/**
	 * 得到响应 ，如果是返回结果就返回结果
	 * 	否则就返回状态码
	 * @return
	 */
	public abstract String getResponse();
	
	
}
