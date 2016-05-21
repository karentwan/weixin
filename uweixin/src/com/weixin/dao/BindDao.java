package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.weixin.util.Db;

public class BindDao {
	
	private static BindDao bd = null;
	
	public static BindDao getInstance() {
		if( bd == null) 
			bd = new BindDao();
		return bd;
	}
	
	public int bind(String name, String openId) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
System.out.println("openId:" + openId);
		try {
			stmt = conn.createStatement();
			int id = UserDao.getInstance().generateUser(stmt);
			String sql = "insert into tb_bind values(null, '" + openId + "', '"+name +"', "+id + ")";
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
	
	/**
	 * 根据openId判断是否已经绑定
	 * @param openId
	 * @return
	 */
	public boolean isBind(String openId) {
		boolean flag = false;
		Connection  conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from tb_bind where openId = '" + openId +"'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
		
	}
	
	
}
