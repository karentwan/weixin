package com.weixin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import com.weixin.util.Db;

/**
 * 辅导员管理学生的dao
 * @author wan
 */
public class ManagerDao {
	
	private static ManagerDao md = null;
	
	public static ManagerDao getInstance() {
		if( md == null)
			md = new ManagerDao();
		return md;
	}
	
	/**
	 * 辅导员将班级归为自己管理
	 * @param listClass 所有班级的集合
	 * @param id 辅导员id
	 * @return
	 */
	public int addClass(List<String> listClass, int id) {
		int code = 200;
		Connection conn = Db.getConnection();
		PreparedStatement prep = null;
		String sql = "update tb_class set instructor_id = " + id +" where num = ?";
		try {
			conn.setAutoCommit(false);
			prep = conn.prepareStatement(sql);
			for(String tempStr : listClass) {
				prep.setString(1, tempStr);
				prep.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			code = -1;
			try {//回滚
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				prep.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	
	public int deleteClass(List<String> list) {
		int code = 200;
		Connection conn = Db.getConnection();
		String sql = "update tb_class set instructor_id = 0 where num = ?";
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for(int i = 0; i < list.size(); i++) {
				pstmt.setString(1, list.get(i));
				pstmt.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			code = -1;
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	
	/**
	 * 查询所有班级
	 * @return
	 */
	public List<String> getAllClass() {
		List<String> list = new ArrayList<String>();
		String sql = "select num from tb_class";
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while( rs.next()) {
				list.add(rs.getString(1));
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
		return list;
	}
	
	public int queryId(String name) {
		int id = 0;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id from tb_instru where name = '" + name +"'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt(1);
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
		return id;
	}
	
	/**
	 * 查询所有没有被辅导员所管理的班级
	 * 也就是自由的班级
	 */
	public List<String> queryConfidomClass() {
		Connection conn = Db.getConnection();
		String sql = "select num from tb_class where instructor_id=0 order by num";
		Statement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString(1));
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
		return list;
	}
	
	/**
	 * 删除填错信息的学生
	 * @param account 学号
	 * @return
	 */
	public int deleteStudent(String account) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "delete from tb_student where account = '" + account + "'";
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
	 * 从数据库里面删除班级
	 * @return
	 */
	public int deleteClass(JSONArray array) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		String temp = null;
		String sql = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for(Object o : array) {
				temp = (String)o;
				//先删除所有学生表里面被外键关联的学生
				deleteStudentFromClass(stmt, temp);
				//删除班级表里面的班级
				sql = "delete from tb_class where num = '" + temp + "'";
				stmt.execute(sql);
			}
			conn.commit();
		} catch (SQLException e) {
			code = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
	 * 删除指定班级下面的学生
	 * @param clazz 班级号码
	 * @throws SQLException 
	 */
	private void deleteStudentFromClass(Statement stmt, String clazz) throws SQLException {
		String sql = "delete from tb_student where account in ( select o.account from"
				+ " (select s.account from tb_student as s right "
				+ "join tb_class as c on s.class_id = c.id where c.num='" + clazz + "') as o)";
		stmt.execute(sql);
	}
	
	
	

}
