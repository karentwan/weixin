package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.LeaveDao;

/**
 * ��ٵ�Servlet
 * @author wan
 */
public class LeaveServlet extends HttpServlet{

	/**
	 * -1��ʾ����ʧ��
	 * 200��ʾ�����ɹ�
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
System.out.println("leaveStart doGet.........................");
		//���ѧ��
		String account = request.getParameter("account");
System.out.println("account:" + account);
		//����
		String name = request.getParameter("name");
		//���ʱ��
		String startTime = request.getParameter("startTime");
		//�������
		int dateCount = Integer.parseInt(request.getParameter("dateCount"));
		//���ԭ��
		String reasonDetail = request.getParameter("reason");
		//��2016-4-24�������͵��ַ����ֽ��2016 4 24�������ַ���
		String[] tempStrs = startTime.split("-");
		String endTime = calDate(tempStrs, dateCount);
		int code = LeaveDao.getInstance().insert(account, name, startTime, endTime, reasonDetail);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if( code == 200) {
				out.print(200);
			} else if(code == 1) {
				out.print(-1);
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
		if(date == 0)
			date = tempDate;
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
