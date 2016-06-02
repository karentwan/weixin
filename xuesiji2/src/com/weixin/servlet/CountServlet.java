package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.SignInDao;
import com.weixin.util.JSONUtil;

/**
 * 统计所有结果
 * @author wan
 */
public class CountServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String time = request.getParameter("time");
System.out.println("name:" + name);
System.out.println("time:" + time);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Map<String,Integer> map = SignInDao.getInstance().count(name, time);
			String result = JSONUtil.count2JSON(map, time);
			out.print(result);
		} catch (IOException e) {
			out.print(-1);
			e.printStackTrace();
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
