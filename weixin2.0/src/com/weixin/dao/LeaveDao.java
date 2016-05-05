package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.weixin.exception.NameNotFoundException;
import com.weixin.util.Db;

/**
 * @author wan
 */
public class LeaveDao {
	
	private static LeaveDao leave = null;
	
	public static LeaveDao getInstance() {
		if(leave == null)
			leave = new LeaveDao();
		return leave;
	}
	
	private LeaveDao() {
		
	}
	
	/**
	 * 插入请假信息到数据库里面
	 * @return
	 */
	public int insert(String account, String name, String startTime, String endTime, String reasonDetail) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instructor = queryName(stmt, account);
			if( instructor == null) {
				throw new NameNotFoundException("您的辅导员未知，不能帮你请假，sorry!");
			}
			String sql = "insert into tb_leave values(null, '" + account + "', '" + name + "', '" + startTime + "', '" + endTime + "', "
					+ "'" + reasonDetail + "', '" + instructor + "')";
			//将请假信息插入到数据库
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) { 
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			code = 406;
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			code = 404;
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
	 * 根据reason查询reasonId
	 * @param reason
	 * @return
	 */
	@Deprecated
	public int queryReasonId(Statement stmt, String reason) {
		String sql = "select id tb_reason where content = '" + reason +"'";
		ResultSet rs = null;
		String id = null;
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				id = rs.getString(1);
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
		return Integer.parseInt(id);
	}
	
	/*
	 * 根据学号查询辅导员姓名
	 */
	public String queryName(Statement stmt, String account) {
		String name = null;
		//左连接查询查询辅导员姓名
		String sql = "select name from tb_instru where id in ( select c.instructor_id from tb_student as s left join tb_class "
				+ "as c on s.class_id = c.id where s.account = '" + account +"')";
System.out.println("sql:" + sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if( rs.next()) {
				name = rs.getString(1);
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
		return name;
	}
	
	
}
