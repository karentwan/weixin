package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.LeaveDao;

/**
 * ��ٵ�Servlet
 * @author wan
 */
public class LeaveServlet {

	/**
	 * -1��ʾ����ʧ��
	 * 200��ʾ�����ɹ�
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//���ѧ��
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String startTime = request.getParameter("startTime");
		int dateCount = Integer.parseInt(request.getParameter("dateCount"));
		String reason = request.getParameter("reason");
		//��2016-4-24�������͵��ַ����ֽ��2016 4 24�������ַ���
		String[] tempStrs = startTime.split("-");
		String endTime = calDate(tempStrs, dateCount);
		String reasonDetail = null;
		if( reason.trim().equals("����")) 
			reasonDetail = request.getParameter("reasonDetail");
		int code = LeaveDao.getInstance().insert(account, name, startTime, endTime, reasonDetail, reason);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if( code == 0) {
				out.println(200);
			} else if(code == 1) {
				out.println(-1);
			}
		} catch (IOException e) {
			out.print(-1);
			e.printStackTrace();
		}
	}
	
	/**
	 * ������ٽ���ʱ��� 
	 * 	ע:���ʱ�䰴��ÿ����30����
	 * @param strs �Ѿ���ֵĵ�ǰ�����ʱ��
	 * @param dateCount �������
	 * @return
	 */
	public String calDate(String[] strs, int dateCount) {
		String dateStr = null;
		//ȡ�������ַ�����������ٽ���������
		int date = Integer.parseInt(strs[2]);
		int month = Integer.parseInt(strs[1]);
		int year = Integer.parseInt(strs[0]);
		int tempDate = date+dateCount;
		int tempMonth = 0;
		date = tempDate % 30;
		tempMonth = tempDate / 30;
		month += tempMonth;
		if( month > 12) {
			year += 1;
			month -= 12;
		}
		dateStr = year + "-" + month + "-" + date;
		return dateStr;
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
	
}
