package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weixin.pojo.Leave;
import com.weixin.pojo.Sign;
import com.weixin.util.Db;

/**
 * 学工查询信息
 * @author wan
 */
public class StudentDepartDao {

	private static StudentDepartDao sdd = null;
	
	public static StudentDepartDao getInstance() {
		if(sdd == null)
			sdd = new StudentDepartDao();
		return sdd;
	}
	
	/**
	 * 验证是否是学工处的老师
	 * @param openId
	 * @return true表示是学工处的老师，false表是不是
	 */
	public boolean verify(String openId) {
		boolean flag = false;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select * from tb_stuDepartInfo where openId = '" + openId + "'";
			rs = stmt.executeQuery(sql);
			if(rs.next())
				flag = true;
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
		return flag;
	}
	
	/**
	 * 查签到
	 * @param time 时间
	 * @param name 工号
	 * @return
	 */
	public Map<String, List<Sign>> querySignIn(String time, String name) {
		Map<String, List<Sign>> map = new HashMap<String, List<Sign>>();
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Sign> list = null;
		String clazzStr = null;
		try {
			stmt = conn.createStatement();
			String sql = "select s.account, s.name, c.num from "
					+ "tb_sign_in as si right join tb_student as "
					+ "s using(account) left join tb_class as "
					+ "c on s.class_id=c.id where si.time='" + time + "' order by account";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				clazzStr = rs.getString(3);
				if( null == map.get(clazzStr)) {
					list = new ArrayList<Sign>();
					map.put(clazzStr, list);
				} else {
					list = map.get(clazzStr);
				}
				Sign  s = new Sign();
				s.setAccount(rs.getString(1));
				s.setName(rs.getString(2));
				list.add(s);
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
		return map;
	}
	
	/**
	 * 查未签到
	 * @param time 时间
	 * @param name 工号
	 * @return
	 */
	public Map<String, List<Sign>> queryDisconnect(String time, String name) {
		Map<String, List<Sign>> map = new HashMap<String, List<Sign>>();
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Sign> list = null;
		String clazzStr = null;
		try {
			conn.createStatement();
			String sql = "select s.account, s.name, c.num  from tb_student as s left "
					+ "join tb_class as c on s.class_id=c.id where account not "
					+ "in (select s.account from tb_sign_in as si right join tb_student as "
					+ "s using(account) left join tb_class as c on s.class_id=c.id where "
					+ "si.time='" + time+ "') order by account";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				clazzStr = rs.getString(3);
				if( null == map.get(clazzStr)) {
					list = new ArrayList<Sign>();
					map.put(clazzStr, list);
				} else {
					list = map.get(clazzStr);
				}
				Sign s = new Sign();
				s.setAccount(rs.getString(1));
				s.setName(rs.getString(2));
				list.add(s);
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
		
		return map;
		
	}
	
	
	/**
	 * 查请假
	 * @param time 时间
	 * @param name 工号
	 * @return
	 */
	public Map<String, List<Leave>> queryLeave(String time, String name) {
		Map<String, List<Leave>> map = new HashMap<String, List<Leave>>();
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Leave> list = null;
		String clazzStr = null;
		try {
			stmt = conn.createStatement();
			String sql = "select l.account, l.name, l.reasonDetail, c.num from tb_leave as l "
					+ "left join tb_student as s using(account) right join tb_class as "
					+ "c on s.class_id=c.id where l.startTime <= '" + time + "' and l.endTime >= '"+time+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				clazzStr = rs.getString(4);
				if( null == map.get(clazzStr)) {
					list = new ArrayList<Leave>();
					map.put(clazzStr, list);
				} else {
					list = map.get(clazzStr);
				}
				Leave l = new Leave();
				l.setAccount(rs.getString(1));
				l.setName(rs.getString(2));
				l.setReason(rs.getString(3));
				list.add(l);
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
		return map;
		
	}
	
	
}
