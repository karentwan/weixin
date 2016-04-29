package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.LeaveDao;

/**
 * 请假的Servlet
 * @author wan
 */
public class LeaveServlet {

	/**
	 * -1表示操作失败
	 * 200表示操作成功
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//获得学号
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String startTime = request.getParameter("startTime");
		int dateCount = Integer.parseInt(request.getParameter("dateCount"));
		String reason = request.getParameter("reason");
		//将2016-4-24这种类型的字符串分解成2016 4 24这三个字符串
		String[] tempStrs = startTime.split("-");
		String endTime = calDate(tempStrs, dateCount);
		String reasonDetail = null;
		if( reason.trim().equals("其他")) 
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
