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
 * �����࣬�ӿͻ��˻�ȡJSON���ݣ�������
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
		//��������
		processJSON(obj);
		//�õ�״̬��
		String code = getCode();
		PrintWriter out = response.getWriter();
		//��״̬����Ӧ���ͻ���
		out.print(code);
	}
	
	/**
	 * �������Կͻ��˵�JSON����
	 * @param obj JSON���ݷ�װ�ɵ�obj����
	 */
	public abstract void processJSON(JSONObject obj);
	
	/**
	 * �õ�״̬�룬�� �����������ظ��ͻ���
	 * @return
	 */
	public abstract String getCode();
	
	
	

}
