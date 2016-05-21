package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.UserDao;

/**
 * 管理员计算消费的情况
 * @author wan
 *
 */
public class CalConsumeServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String userId = req.getParameter("userId");
		String money = req.getParameter("money");
		String time = req.getParameter("time");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			int code = UserDao.getInstance().processConsume(userId, money, time);
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
