package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.ManagerDao;
import com.weixin.util.JSONUtil;

public class QueryConfidomClassServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
		List<String> list = ManagerDao.getInstance().queryConfidomClass();
		try {
			PrintWriter out = resp.getWriter();
			String result = JSONUtil.class2JSON(list);
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}

	
}
