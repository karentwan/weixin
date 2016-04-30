package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	 * 0��ʾ����ɹ�
	 * 1��ʾ����ʧ��
	 * @return
	 */
	public int insert(String account, String name, String startTime, String endTime, String reasonDetail, String reason) {
		int code = 0;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instructor = queryName(stmt, account);
			if(reasonDetail == null)
				reasonDetail = reason;
			String sql = "insert into tb_leave values(null, '" + account + "', '" + name + "', '" + startTime + "', '" + endTime + "', "
					+ "'" + reasonDetail + "', '" + instructor + "'";
			//�������Ϣ���뵽���ݿ�
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) { 
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			code = 1;
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
	 * ����reason��ѯreasonId
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
	 * ����ѧ�Ų�ѯ����Ա����
	 */
	public String queryName(Statement stmt, String account) {
		String name = null;
		String sql = "select name from tb_instructor where id in (select instructor_id from tb_student where account = '" + account +"'";
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
