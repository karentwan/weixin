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
 * ����ѯ��Ϣ�ֿ���
 * ������ٲ�ѯ������
 * ǩ����Ϣ������
 * δǩ����Ϣ������
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
	 * ��ǩ������
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
	 * ���������
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
	 * ��δ�������
	 * @param time ʱ��
	 * @param name ����
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
