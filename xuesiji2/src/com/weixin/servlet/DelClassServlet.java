package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.weixin.dao.ManagerDao;

public class DelClassServlet extends ManagerServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.doGet(request, response);
			PrintWriter out = response.getWriter();
			out.print(200);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processClass(JSONObject obj) {
		JSONArray array = obj.getJSONArray("class");
		String name = obj.getString("name");
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length(); i++) {
			list.add(array.getString(i));
		}
		ManagerDao.getInstance().deleteClass(list);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
	
	
}
