package com.weixin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.weixin.util.Db;

/**
 * ����ǩ����dao��
 * @author wan
 */
public class SignInDao {
	
	private static SignInDao dao= null;
	
	private SignInDao() {
		
	}
	
	public static SignInDao getInstance() {
		if( dao == null)
			dao = new SignInDao();
		return dao;
	}
	
	/**
	 * 插入信息到数据库
	 * 0 表示插入成功
	 * 1表示插入失败
	 * @return
	 */
	public int insert(String account, String name, String time) {
		int code = 0;
		Connection conn = Db.getConn();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);
			String in = queryInstructor(account, stmt);
			String accAndTime = account+time;
			String sql = "insert into tb_sign_in(account, name, time, instructor_name, accAndTime) values('"
					+ account +"','" + name+ "','" + time + "','" +in + "', '" + accAndTime + "', 1)";
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
	 * @param floor
	 * @param dormitory
	 * @param personCount
	 * @param contacts
	 * @param tel
	 * @return
	 */
	public int insertDormitory(String floor, String dormitory, int personCount, String contacts, String tel) {
		int code = 200;
		String sql = "insert into tb_condition(floor, dormitory, personCount, contacts, tel, dorAndFlo) values(?, ?, ?, ?, ?, ?)";
		Connection conn = Db.getConn();
		PreparedStatement prep = null;
		try {
			conn.setAutoCommit(false);
			prep = conn.prepareStatement(sql);
			prep.setString(1, floor);
			prep.setString(2, dormitory);
			prep.setInt(3, personCount);
			prep.setString(4, contacts);
			prep.setString(5, tel);
			prep.setString(6, floor+dormitory);
			prep.execute();
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
				prep.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	
	/**
	 * 查询空调统计结果
	 */
	public StringBuffer queryCondition() {
		StringBuffer sb = new StringBuffer();
		Connection conn = Db.getConn();
		String sql = "select floor, count(dormitory) as count from tb_condition group by floor;";
		Statement stmt = null;;
		ResultSet rs = null;
		sb.append("{\"result\":[");
		try {
			stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String floor = rs.getString(1);
				String count = rs.getString(2);
				sb.append("{\"floor\":\"" + floor + "\",\"dormitoryCount\":\"" + count + "\"},");
			}
			//ɾ�����һ������
			sb.deleteCharAt(sb.length()-1);
		} catch (SQLException e) {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		sb.append("]}");
		
		return sb;
	}
	
	/**
	 * 查询所有的楼栋
	 * @return
	 */
	public Map<String, List<String>> queryDormitory() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Connection conn = Db.getConn();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select floor, dormitory from tb_condition";
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				//获取楼栋
				String floor = rs.getString(1);
				String dormitory = rs.getString(2);
				if( map.get(floor) == null) {
					List<String> tempList = new ArrayList<String>();
					map.put(floor, tempList);
				}
				map.get(floor).add(dormitory);
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
	 * 根据openId查询学生的学号和姓名
	 * @param openId 微信的唯一ID
	 * @return 一个含有学号和姓名的List集合
	 */
	public List<String> query(String openId) {
		Connection conn = Db.getConn();
		String sql = "select account, name from tb_student where openId = '" + openId + "'";
		List<String> rl = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				rl.add(rs.getString("account"));
				rl.add(rs.getString("name"));
			} else {
				rl.add("");
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
		return rl;
	}
	
	/**
	 * 查询指定学号的辅导员
	 * @param account 学号
	 * @param stmt Statement对象
	 * @return 辅导员姓名
	 */
	public String queryInstructor(String account, Statement stmt) {
		String name = null;
		String sql = "select name from tb_instructor where id in (select instructor_id from tb_student where account = '" + account + "')";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				name = rs.getString("name");
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
		return name;
	}
	
}
