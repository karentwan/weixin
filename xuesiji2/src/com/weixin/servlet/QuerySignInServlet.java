package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.InstruDao;

/**
 * 辅导员查询寝室点到的servlet的入口
 * @author wan
 */
public class QuerySignInServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
    	PrintWriter out = null;
    	try {
//    		response.setCharacterEncoding("gbk");
    		response.setCharacterEncoding("UTF-8");
    		request.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			String time = request.getParameter("time");
	    	String name = request.getParameter("name");
			String result = InstruDao.getInstance().queryAllAndClazz(name, time);
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
	
	
}
