package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.dao.SignInDao;

/**
 * ²éÑ¯ÇÞÊÒºÅµÄ servlet
 * @author wan
 */
public class DormitoryServlet extends HttpServlet{
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		SignInDao si = SignInDao.getInstance();
		Map<String, List<String>> map = si.queryDormitory();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(map.toString());
		} catch (Exception e) {
			out.print(-1);
		}
		
	}

}
