package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.weixin.dao.ManagerDao;

/**
 * 从数据库里面删除班级
 * @author wan
 */
public class DeleteClassServlet extends ManagerServlet{

	@Override
	public void processClass(JSONObject obj) {
		JSONArray array = obj.getJSONArray("class");
		int code = ManagerDao.getInstance().deleteClass(array);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		super.doPost(request, response);
		PrintWriter out = response.getWriter();
		out.print(200);
	}

	
	
}
