package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.UserDao;

public class RechargeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String money = req.getParameter("money");
		String userId = req.getParameter("userId");
		int code = UserDao.getInstance().recharge(userId, money);
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.print(code);
		} catch (IOException e) {
			out.print(-1);
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	

}