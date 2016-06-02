package com.weixin.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * 辅导员管理班级
 * @author wan
 */
public class ManagerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		StringBuffer jsonBuffer = new StringBuffer();
		BufferedReader reader = request.getReader();
		String str = null;
		while( (str = reader.readLine()) != null) {
			jsonBuffer.append(str);
		}
		reader.close();
		JSONObject obj = new JSONObject(jsonBuffer.toString());
		processClass(obj);
	}
	/**
	 * 处理班级
	 * @param obj
	 */
	public void processClass(JSONObject obj) {
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		StringBuffer jsonBuffer = new StringBuffer();
		BufferedReader reader = request.getReader();
		String str = null;
		while( (str = reader.readLine()) != null) {
			jsonBuffer.append(str);
		}
		reader.close();
		JSONObject obj = new JSONObject(jsonBuffer.toString());
		processClass(obj);
	}
	
}
