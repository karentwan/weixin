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

/**
 * ����Ա��½
 * @author wan
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String name = request.getParameter("name");
			String psd = request.getParameter("psd");
			boolean flag = LoginDao.getInstance().verify(name, psd);
System.out.println("name:" + name);
System.out.println("psd:" + psd);
			if( name != null && psd != null && flag) {
				int maxAge = 7 * 24 * 60 * 60;
				CookieUtil.addCookie(response, "name", name, maxAge);
				CookieUtil.addCookie(response, "psd", psd, maxAge);
System.out.println("...............");
				//��֤�ɹ�֮����תҳ��
				request.getRequestDispatcher("/admin/consume.html").forward(request, response);
			} 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
