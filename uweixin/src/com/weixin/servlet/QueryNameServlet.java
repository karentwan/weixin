package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.dao.UserDao;
import com.weixin.util.JSONUtil;

/**
 * ¸ù¾ÝuserId²éÑ¯userName
 * @author wan
 */
public class QueryNameServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String name = UserDao.getInstance().queryNameFromUserId(userId);
		String result = null;
		if( name != null) {
			result =JSONUtil.name2JSON(name);
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if( result != null) {
				out.print(result);
			} else {
				out.print(-1);;
			}
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
