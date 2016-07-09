package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.weixin.dao.InstruDao;
import com.weixin.pojo.HistoryResult;

/**
 * 查询历史记录
 * @author wan
 */
public class HistoryServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String dateStr = request.getParameter("dateCount");
		int d = 5;
		if( dateStr != null) {
			d = Integer.parseInt(dateStr);
		}
		InstruDao id = InstruDao.getInstance();
		HistoryResult result = id.historyQuery(name, d);
		JSONObject  json = JSONObject.fromObject(result);
		PrintWriter out = response.getWriter();
		out.print(json.toString());
	}

}
