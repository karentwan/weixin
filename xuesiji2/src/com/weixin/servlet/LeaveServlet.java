package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.LeaveDao;

/**
 * ��ٵ�Servlet
 * @author wan
 */
public class LeaveServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * -1��ʾ����ʧ��
	 * 200��ʾ�����ɹ�
	 */
	@SuppressWarnings("null")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		//���ѧ��
		String account = request.getParameter("account");
		//����
		String name = request.getParameter("name");
		//���ʱ��
		String startTime = request.getParameter("startTime");
		//�������
		String endTime = request.getParameter("endTime");
		//���ԭ��
		String reasonDetail = request.getParameter("reason");
		boolean flag = LeaveDao.getInstance().isLeave(account, name, startTime);
		int code = -1;
		if( flag ) {
			code = LeaveDao.getInstance().insert(account, name, startTime, endTime, reasonDetail);
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(code);
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
		String monthStr = null;
		if(date == 0)
			date = tempDate;
		tempMonth = tempDate / 30;
		month += tempMonth;
		if( month > 12) {
			year += 1;
			month -= 12;
		}
		//����·ݻ��������С��10��Ӧ����ǰ�����0
		if( month > 0 && month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = String.valueOf(month);
		}
		if( date > 0 && date < 10) {
			dateStr = "0" + date;
		} else {
			dateStr = String.valueOf(date);
		}
		dateStr = year + "-" + monthStr + "-" + dateStr;
		return dateStr;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
	
}
