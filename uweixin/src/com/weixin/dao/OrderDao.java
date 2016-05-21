package com.weixin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.weixin.util.Db;

public class OrderDao {
	
	private static OrderDao od = null;
	
	public static OrderDao getInstance() {
		if( od == null) 
			od = new OrderDao();
		return od;
	}
	
	/**
	 * 插入预约信息
	 * @param name
	 * @param age
	 * @param tel
	 * @param time
	 * @return
	 */
	public int insert(String name, String age, String tel, String time) {
		int code = 200;
		Connection conn = Db.getConnection();
		String sql = "insert into tb_order values(null, '" + name +"', '" +age +"'"
				+ ", '" + tel + "', '" + time + "')";
		Statement stmt = null;
System.out.println("sql:" + sql);
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			code = -1;
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 
		return code;
	}
	
}
