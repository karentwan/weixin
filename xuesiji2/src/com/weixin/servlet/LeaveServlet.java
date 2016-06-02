package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.LeaveDao;

/**
 * 请假的Servlet
 * @author wan
 */
public class LeaveServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * -1表示操作失败
	 * 200表示操作成功
	 */
	@SuppressWarnings("null")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		//获得学号
		String account = request.getParameter("account");
		//姓名
		String name = request.getParameter("name");
		//请假时间
		String startTime = request.getParameter("startTime");
		//请假天数
		String endTime = request.getParameter("endTime");
		//请假原因
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
	 * 计算请假结束时间段 
	 * 	注:请假时间按照每个月30天算
	 * @param strs 已经拆分的当前的请假时间
	 * @param dateCount 请假天数
	 * @return
	 */
	public String calDate(String[] strs, int dateCount) {
		String dateStr = null;
		//取第三个字符串并计算请假结束的日期
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
		//如果月份或这个天数小于10，应该在前面加上0
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
