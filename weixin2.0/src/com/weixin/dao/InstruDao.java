package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.weixin.exception.DuplicateBindException;
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
			boolean flag = isBind(stmt, name);
			if( flag) {
				throw new DuplicateBindException("这个微信号已经跟辅导员绑定了，不能重复 绑定");
			}
			String insertSql = "insert into tb_instructor values(null, '" + openId + "', " + id + ")";
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
		} catch ( DuplicateBindException e) {
			code = 406;
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
		String sql ="select s.account, s.name, s.id from tb_student as s right join (select c.num, c.id, i.name from "
				+ "tb_class as c left join tb_instru as i on c.instructor_id = i.id  where i.name='"
				+name+"') as o on s.class_id = o.id where s.id not in (select id from tb_student where name in (select name from tb_sign_in where instructor_name = '"
						+ name +"' and time = '" + time +"'))";
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
System.out.println("leave sql:" + sql);
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
	
	/**
	 * 判断辅导员是否已经绑定了微信号
	 * @param name 辅导员姓名
	 * @return true 表示已经绑定了微信号
	 * 	false 表示还没有绑定微信号
	 */
	private boolean isBind(Statement stmt, String name) {
		boolean flag = false;
		String sql = "select * from tb_instructor as d left join tb_instru as b on d.tb_instru_id = b.id where b.name = "
				+ "'" + name + "'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				flag = true;
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
		return flag;
	}
	
	public String queryAll(String name, String time) {
		Map<String, List<String>> signMap = querySign(name, time);
		Map<String, List<String>> unSignMap = queryUnSign(name, time);
		Map<String, List<String>> leaveMap = queryLeave(name, time);
		StringBuffer buffer = new StringBuffer();
		List<String> accountList = signMap.get("account");
		List<String> nameList = signMap.get("name");
		buffer.append("{ \"signIn\": { \r\n");
		for(int i = 0; i < accountList.size(); i++) {
			buffer.append("\"" + accountList.get(i) + "\":");
			buffer.append("\"" + nameList.get(i) + "\"");
			buffer.append(",\r\n");
		}
		buffer.deleteCharAt(buffer.length()-3);
		accountList = unSignMap.get("account");
		nameList = unSignMap.get("name");
		buffer.append("},\r\n\"disconnect\": {\r\n");
		for( int i = 0; i < accountList.size(); i++) {
			buffer.append("\"" + accountList.get(i) + "\":");
			buffer.append("\"" + nameList.get(i) + "\"");
			buffer.append(",\r\n");
		}
		buffer.deleteCharAt(buffer.length()-3);
		accountList = leaveMap.get("account");
		nameList = leaveMap.get("name");
		buffer.append("},\r\n\"leave\": {\r\n");
		for( int i = 0; i < accountList.size(); i++) {
			buffer.append("\"" + accountList.get(i) + "\":");
			buffer.append("\"" + nameList.get(i) + "\"");
			buffer.append(",\r\n");
		}
		buffer.append("}\r\n}");
		return buffer.toString();
	}
	
	public String queryAllAndClazz(String name, String time) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		StringBuffer sb = new StringBuffer();
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sign = querySignInAndClazz(stmt, name, time);
			String unSign = queryUnSignAndClazz(stmt, name, time);
			String leave = queryLeaveAndClazz(stmt, name, time);
			sb.append("{ signIn: ");
			sb.append(sign);
			sb.append(", disconnect :").append(unSign);
			sb.append(", leave :").append(leave);
			sb.append("}");
			conn.commit();
		} catch (SQLException e) {
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
		return sb.toString();
	}
	
	/**
	 * 带上班级的查询
	 * @param name 辅导员姓名
	 */
	public String queryUnSignAndClazz(Statement stmt, String name, String time) {
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String sql = "select s.account, s.name, s.id, o.c_id "
				+ "from tb_student as s right join (select c.num, c.id, "
				+ "i.name, c.num as c_id from tb_class as c left join tb_instru as i on "
				+ "c.instructor_id = i.id  where i.name='"
				+ name + "') as o on s.class_id = o.id where s.id not in "
						+ "(select id from tb_student where name in (select name "
						+ "from tb_sign_in where instructor_name = '"
						+ name + "' and time = '"+ time + "'))";
		ResultSet rs = null;
		List<String> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(4);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<String>());
				list = map.get(clazz);
				list.add("{account :\"" + rs.getString(1) + "\",name :\""+rs.getString(2) + "\"}");
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
		String str = map.toString();
		str = str.replace("=", ":");
		return str;
	}
	
	/**
	 * 查询已经签到了的
	 */
	public String querySignInAndClazz(Statement stmt, String name, String time) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String sql = "select oi.num, si.account, si.name from tb_sign_in as si right join "
				+ "( select s.account, o.num from tb_student as s right join (select c.num, "
				+ "c.id as c_id from tb_instru as i right join tb_class as c on i.id = c.instructor_id where i.name='"
				+ name + "') as o on o.c_id = s.class_id) as oi on oi.account = si.account where time = '"
						+ time + "' ";
		
		ResultSet rs = null;
		List<String> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(1);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<String>());
				list = map.get(clazz);
				list.add("{account :\""+rs.getString(2) + "\", name :\""+rs.getString(3)+"\"}");
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
		String str = map.toString();
		str = str.replace("=", ":");
		return str;
	}
	
	/**
	 * 查询请假的人数
	 */
	public String queryLeaveAndClazz(Statement stmt, String name, String time) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String sql = "select l.account, l.name, l.reasonDetail, o.num from "
				+ "tb_leave as l left join ( select c.num, s.account from tb_class as c "
				+ "left join tb_student as s on s.class_id = c.id ) as o using (account) where l.instructor_name = '"
				+ name + "' and l.startTime <= '" + time +"' and l.endTime >= '" + time +"'";
		ResultSet rs = null;
		List<String> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(4);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<String>());
				list = map.get(clazz);
				list.add("{ account :\"" + rs.getString(1) + "\",name : \""+rs.getString(2) + "\", reason:\"" + rs.getString(3) + "\"}");
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
		String str = map.toString();
		str = str.replace("=", ":");
		return str;
	}
	
	
	
}
