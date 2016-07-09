package com.weixin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import com.weixin.pojo.Leave;
import com.weixin.pojo.Sign;
import com.weixin.util.Db;

/**
 * 将查询信息分开来
 * 例如请假查询单独查
 * 签到信息单独查
 * 未签到信息单独查
 * @author wan
 */
public class SubQueryDao {

	private static SubQueryDao sqd = null;
	
	public static SubQueryDao getInstance() {
		if( sqd == null)
			sqd = new SubQueryDao();
		return sqd;
	}
	
	/**
	 * 查签到数据
	 * @param time
	 * @param name
	 * @return
	 */
	public Map<String, List<Sign>> querySign(String time, String name) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		Map<String, List<Sign>> map = null;
		try {
			stmt = conn.createStatement();
			map = InstruDao.getInstance().querySignInAndClazz2(stmt, name, time);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 查请假数据
	 * @param time
	 * @param name
	 * @return
	 */
	public Map<String, List<Leave>> queryLeave(String time, String name) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		Map<String, List<Leave>> map = null;
		try {
			stmt = conn.createStatement();
			map = InstruDao.getInstance().queryLeaveAndClazz2(stmt, name, time);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 查未请假人数
	 * @param time 时间
	 * @param name 工号
	 * @return
	 */
	public Map<String, List<Sign>> queryDisconnect(String time, String name) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		Map<String, List<Sign>> map = null;
		try {
			stmt = conn.createStatement();
			map = InstruDao.getInstance().queryUnSignAndClazz2(stmt, name, time);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
}
