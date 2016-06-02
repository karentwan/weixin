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
	 * @return
	 */
	public int bind(String account, String name, String clazz, String openId) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);//设置不自动提交
			stmt = conn.createStatement();
			int clazzId = getClassId(stmt, clazz);
			if( clazzId == 0) {
				clazzId = insertClass(stmt, clazz);
			}
			//插入学号跟openId绑定信息到数据库
			String sql = "insert into tb_student values(null, '" + account + "', '" + name +"', "
					+ "'" + openId + "', " + clazzId +")";
			stmt.execute(sql);
			conn.commit();//提交修改到数据库
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
	 * 	id:如果返回的id=0，那么说明在数据库里面没有查到这个辅导员
	 * @param stmt
	 * @param name
	 * @return
	 */
	@Deprecated
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
	
	/**
	 * 根据班级查询班级id
	 * @param stmt
	 * @param clazz
	 * @return
	 */
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
	 * 插入班级到数据库并获取插入之后的班级ID
	 * 	id:如果id为0的话，那么说明在数据库里面没有查到这个班级
	 * @param stmt Statement
	 * @param clazz 班级
	 * @return
	 */
	private int insertClass(Statement stmt, String clazz) {
		String sql = "insert into tb_class values(null, '" + clazz + "', 0)";
		String querySql = "select id from tb_class where num='" + clazz + "'";
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
