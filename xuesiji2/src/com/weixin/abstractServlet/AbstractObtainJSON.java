package com.weixin.abstractServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * 抽象类，从客户端获取JSON数据，并处理
 * @author wan
 */
public abstract class AbstractObtainJSON extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		//处理数据
		processJSON(obj);
		//得到状态码
		String code = getCode();
		PrintWriter out = response.getWriter();
		//将状态码响应给客户端
		out.print(code);
	}
	
	/**
	 * 处理来自客户端的JSON数据
	 * @param obj JSON数据封装成的obj对象
	 */
	public abstract void processJSON(JSONObject obj);
	
	/**
	 * 得到状态码，以 供服务器返回给客户端
	 * @return
	 */
	public abstract String getCode();
	
	
	

}
