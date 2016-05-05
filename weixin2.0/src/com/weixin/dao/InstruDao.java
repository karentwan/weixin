package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.weixin.exception.NameNotFoundException;
import com.weixin.util.Db;

public class InstruDao {
	
	private static InstruDao instru = null;
	
	public static InstruDao getInstance() {
		if(instru == null)
			instru = new InstruDao();
		return instru;
	}
	
	/**
	 * 验证辅导员是否已经绑定
	 * @param openId 辅导员的openId
	 * @return
	 */
	public String verify(String openId) {
		String name = null;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
//			String sql = "select name from tb_instructor where openId = '" + openId + "'";
			String sql = "select m.name from tb_instru as m right join tb_instructor as f on m.id = f.tb_instru_id where f.openId = '" + openId + "'";
System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				name = rs.getString(1);
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
		return name;
	}
	
	/**
	 * 将辅导员的openId跟姓名绑定起来
	 * @param openId 辅导员的openId
	 * @param name 辅导员的姓名
	 * @return
	 */
	public int bind(String openId, String name) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id from tb_instru where name = '" + name + "'";
		//辅导员在tb_instru表中的id
		int id = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt(1);
			} else {
				throw new NameNotFoundException("辅导员姓名没有找到");
			}
			String insertSql = "insert into tb_instructor values(null, '" + openId + "', " + id;
			stmt.execute(insertSql);
			conn.commit();
		} catch (SQLException e) {
			try {//回滚
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			code = -1;
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			code = 404;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
		return code;
	}
	
	
	/**
	 * 否则根据辅导员的姓名查询签到的学生
	 * @param name 辅导员姓名
	 * @return
	 */
	public Map<String, List<String>> querySign(String name, String time) {
		List<String> listName = new ArrayList<String>();
		List<String> listAccount = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("account", listAccount);
		map.put("name", listName);
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select account, name from tb_sign_in where instructor_name "
				+ "='" + name + "' and time = '" + time + "'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while( rs.next()) {
				String account = rs.getString(1);
				String stuName = rs.getString(2);
				listAccount.add(account);
				listName.add(stuName);
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
	 * 查询未签到的人数
	 * @param name 辅导员的姓名
	 * @param time 时间
	 * @return
	 */
	public Map<String, List<String>> queryUnSign(String name, String time) {
		List<String> listName = new ArrayList<String>();
		List<String> listAccount = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("account", listAccount);
		map.put("name", listName);
		Connection conn = Db.getConnection();
		String sql = "select account, name from tb_sign_in where id not in ("
				+ "select id from tb_sign_in where instructor_name = '" + name + "' "
						+ "and time = '" + time + "')";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String tempAcc = rs.getString(1);
				String tempName = rs.getString(2);
				listName.add(tempName);
				listAccount.add(tempAcc);
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
	 * 根据辅导员的姓名查询请假的人数
	 * @param name 辅导员的姓名 
	 * @param time 时间
	 */
	public Map<String, List<String>> queryLeave(String name, String time) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> accountList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		map.put("account", accountList);
		map.put("name", nameList);
		Connection conn = Db.getConnection();
		String sql = "select account, name from tb_leave where startTime <= '" +time+"' and endTime >= '" + time +""
				+ "' and instructor_name = '" + name + "'";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String tempAcc = rs.getString(1);
				String tempName = rs.getString(2);
				accountList.add(tempName);
				nameList.add(tempAcc);
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
