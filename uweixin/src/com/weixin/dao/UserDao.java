package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.weixin.util.Db;

public class UserDao {
	
	private static UserDao ud = null;
	
	public static UserDao getInstance() {
		if(ud == null)
			ud = new UserDao();
		return ud;
	}
	
	private static Random random = new Random();
	
	public int queryId(Statement stmt, String user) {
		String sql = "select id from tb_user where userName = '" + user + "'";
		ResultSet rs = null;
		int id = -1;
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
		
	}
	
	/**
	 * 生成用户id五位数
	 * @return
	 */
	public synchronized int generateUser(Statement stmt) {
		String user = "";
		for(int i = 0; i < 5; i++) {
			user += random.nextInt(10);
		}
		String sql = "insert into tb_user values(null, '" + user + "', '" + user + "')";
		String querySql = "select id from tb_user where userName = '" + user + "'";
		ResultSet rs = null;
		int id = -1;
		try {
			stmt.execute(sql);
			rs = stmt.executeQuery(querySql);
			if( rs.next() ) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	public void queryInfo(String openId) {
		
	}
	
	/**
	 * 根据userId查询userName
	 * @param userId
	 */
	public String queryNameFromUserId(String userId) {
		Connection conn = Db.getConnection();
		String sql = "select name from tb_user as u left join tb_bind as b on u.id = b.user_id where u.userName = '"
				+ userId + "'";
		Statement stmt = null;
		ResultSet rs = null;
		String name = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next()) {
				name = rs.getString(1);
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
		return name;
	}
	
	public int processConsume(String userId, String money, String time) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String querySql = "select id from tb_user where userName = '" +  userId + "'";
			rs = stmt.executeQuery(querySql);
			int id = 0;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			int m = Integer.parseInt(money);
			String insertSql = "insert into tb_consum values(null, '" + time + "', '" + money +"', " + id + ")";
			stmt.execute(insertSql);
			String updateSql = "update tb_balance set balance = balance - " + m + " where user_id =" + id;
			stmt.execute(updateSql);
			conn.commit();
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
	
	public int recharge(String userId, String money) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		int id = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String querySql = "select id from tb_user where userName = '" +  userId + "'";
			rs = stmt.executeQuery(querySql);
			if(rs.next()) {
				id = rs.getInt(1);
			}
			int m = Integer.parseInt(money);
			String sql1 = "select * from tb_balance where user_id = " + id;
			rs1 = stmt.executeQuery(sql1);
			//里面有数据，只要更新
			if(rs1.next()) {
				String updateSql = "update tb_balance set balance = balance + " + m + " where user_id = " + id;
				stmt.execute(updateSql);
				//没有的时候直接插入
			} else {
				String s = "insert into tb_balance values(null, " + m + ", " + id + ")";
				stmt.execute(s);
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			code = -1;
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
		
		
		
		
		
		return code;
	}
	
	
	
	
	
}
