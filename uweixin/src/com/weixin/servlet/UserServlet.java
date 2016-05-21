package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.BalanceDao;
import com.weixin.util.JSONUtil;

public class UserServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String openId = request.getParameter("openId");
		List<String> list = BalanceDao.getInstance().queryBalance(openId);
		PrintWriter out = response.getWriter();
		if( !list.isEmpty() ) {
			String result = JSONUtil.balance2JSON(list);	
			out.print(result);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

}
