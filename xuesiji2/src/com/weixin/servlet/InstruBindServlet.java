package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.InstruDao;

/**
 * 绑定辅导员信息到微信号
 * @author wan
 */
public class InstruBindServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("null")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			//辅导员工号
			String name = request.getParameter("name");
			//辅导员密码
			String psd = request.getParameter("psd");
			//辅导员微信
			String openId = request.getParameter("openId");
			//辅导员姓名
			String nick = request.getParameter("nick");
			//辅导员的学院
			String college = request.getParameter("college");
			//绑定辅导员的信息和学号
			int code = InstruDao.getInstance().bind(openId, name, nick, college, psd);
			out = response.getWriter();
System.out.println("code:" + code);
			out.print(code);
		} catch (UnsupportedEncodingException e) {
			out.print(-1);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
	
}
