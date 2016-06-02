package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.weixin.dao.InstruDao;
import com.weixin.pojo.InstruInfo;

/**
 * 获得辅导员信息
 * @author wan
 */
public class ObtainInfoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		InstruInfo i = InstruDao.getInstance().obtainInfoFromName(name);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject obj = JSONObject.fromObject(i);
			out.print(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	

}
