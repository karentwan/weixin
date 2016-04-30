package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.weixin.util.Db;

public class BindDao {
	
	private static BindDao bind = null;
	
	private BindDao() {
		
	}
	
	public static BindDao getInstance() {
		if(  bind == null)
			bind = new BindDao();
		return bind;
	}
	
	/**
	 * ��ѧ�ź�΢�źŰ�
	 * code ��0��ʾ�󶨳ɹ� 1��ʾʧ��2��ʾ�������
	 * @return
	 */
	public int bind(String account, String name, String psd, String clazz, String openId) {
		int code = 0;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			int clazzId = getClassId(stmt, clazz);
			if( clazzId == 0) {
				clazzId = insertClass(stmt, clazz);
			}
			//����ѧ�Ÿ�openId����Ϣ�����ݿ�
			String sql = "insert into tb_student values(null, '" + account + "', '" + name +"', "
					+ "'" + openId + "', " + clazzId +", null";
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
	 * ���ݸ���Ա�������id
	 * @param stmt
	 * @param name
	 * @return
	 */
	private int getInstructorId(Statement stmt, String name) {
		int id = 0;
		String sql = "select id from tb_instructor where name = '" + name +"'";
		ResultSet rs = null;
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
	
	private int getClassId(Statement stmt, String clazz) {
		int id = 0;
		String sql = "select id from tb_class where num = '" + clazz + "'";
		ResultSet rs = null;
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
	 * ����༶�����ݿ�
	 * @param stmt Statement
	 * @param clazz �༶
	 * @return
	 */
	private int insertClass(Statement stmt, String clazz) {
		String sql = "insert into tb_class values(null, '" + clazz + "', null)";
		String querySql = "select id from tb_class where clazz='" + clazz + "'";
		int id = 0;
		ResultSet rs = null;
		try {
			stmt.execute(sql);
			rs = stmt.executeQuery(querySql);
			if( rs.next()) {
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
	
	
}
