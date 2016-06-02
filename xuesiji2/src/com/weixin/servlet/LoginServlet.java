package com.weixin.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.weixin.dao.LoginDao;
import com.weixin.model.User;
import com.weixin.util.CookieUtil;
import com.weixin.util.WeixinUtil;

/**
 * 辅导员登陆
 * @author wan
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String name = request.getParameter("name");
			String psd = request.getParameter("psd");
			if(name == null || psd == null) {
				response.sendRedirect(WeixinUtil.prop.getProperty("route") + "/Login/login.html?status=error");
				return;
			}
			boolean flag = LoginDao.getInstance().verify(name, psd);
			if( flag ) {
				User u = new User();
				u.setName(name);
				u.setPsd(psd);
				int maxAge = 7 * 24 * 60 * 60;
				CookieUtil.addCookie(response, "name", name, maxAge);
				CookieUtil.addCookie(response, "psd", psd, maxAge);
				//将user信息保存到session里面
				HttpSession session = request.getSession();
				session.setAttribute("user", u);
				response.sendRedirect(WeixinUtil.prop.getProperty("route") + "/pc/index.html");
			} else {
				response.sendRedirect(WeixinUtil.prop.getProperty("route") + "/Login/login.html?status=error");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
