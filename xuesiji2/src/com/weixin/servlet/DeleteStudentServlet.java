package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.dao.ManagerDao;

/**
 * 管理员删除填错班级的学生
 * @author wan
 */
public class DeleteStudentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String account = req.getParameter("account");
		int code = ManagerDao.getInstance().deleteStudent(account);
		PrintWriter out = resp.getWriter();
		out.print(code);
	}

	
	
	
}
