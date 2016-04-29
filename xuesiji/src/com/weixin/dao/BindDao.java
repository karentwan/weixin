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
	 * 将学号和微信号绑定
	 * code ：0表示绑定成功 1表示失败2表示密码错误
	 * @return
	 */
	public int bind(String account, String name, String psd, String instructor, String clazz, String openId) {
		int code = 0;
		Connection conn = Db.getConn();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			int instruId = getInstructorId(stmt, instructor);
			int clazzId = getClassId(stmt, clazz);
			if( clazzId == 0) {
				clazzId = insertClass(stmt, clazz, instruId);
			}
			///验证失败的情况
			if(!verify(account, psd)) {
				return 2;
			}
			//插入学号跟openId绑定信息到数据库
			String sql = "insert into tb_student values(null, '" + account + "', '" + name +"', "
					+ "'" + openId + "', " + clazzId +"," + instruId;
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
	 * 根据辅导员姓名获得id
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
	
	private int insertClass(Statement stmt, String clazz, int instruId) {
		String sql = "insert into tb_class values(null, '" + clazz + "', '" + instruId + "')";
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
	
	/**
	 * 验证成功返回true,失败返回false
	 * @param account 学号
	 * @param psd 密码
	 * @return
	 */
	private boolean verify(String account, String psd) {
		boolean flag = false;
		
		return flag;
	}
	
	
}
