package com.weixin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
	public int manager(List<String> listClass, int id) {
		int code = 200;
		Connection conn = Db.getConnection();
		PreparedStatement prep = null;
		String sql = "update tb_class set instructor_id = ?";
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
	
	
	
	
	

}
