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
import com.weixin.exception.NullIdException;
import com.weixin.util.Db;

/**
 * 学生签到的信息记录
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
	 * 签到记录的插入
	 * @param account
	 * @param name
	 * @param time
	 * @param instruId
	 * @return
	 */
	public int insert(String account, String name, String time, String instruId) {
		int code = 0;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String in = queryInstructor(account, stmt, instruId);
			String accAndTime = account+time;
			//插入当天的签到信息到数据库里面
			String sql = "insert into tb_sign_in(account, name, time, instructor_name, accAndTime, core) values('"
					+ account +"','" + name+ "','" + time + "','" +in + "', '" + accAndTime + "', 1)";
			//查询所拥有的积分
			String querySql = "select count(core) from tb_sign_in where account = '" + account + "'";
			stmt.execute(sql);
			rs = stmt.executeQuery(querySql);
			if(rs.next()) {
				code = rs.getInt(1);
			}
			conn.commit();
		} catch( NullIdException e) {
			code = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
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
				if( rs != null)
					rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	
	@Deprecated
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
	 * 查询空调的总数
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
			//删除最后一个字符
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
	 * 查询所有已经预约空调的寝室
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
				//楼栋
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
	 * 查询当前的openId是否已经绑定了学生微信号
	 * @param openId 
	 * @return 
	 */
	public List<String> query(String openId) {
		Connection conn = Db.getConn();
		String sql = "select s.account, s.name, c.instructor_id from tb_student as s left join tb_class as c on s.class_id = c.id where openId = "
				+ "'" + openId + "'";
		List<String> rl = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				rl.add(rs.getString(1));
				rl.add(rs.getString(2));
				rl.add(rs.getString(3));
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
	 * 根据学号查询辅导员姓名
	 */
	@Deprecated
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
	
	/**
	 * 根据辅导员Id查询辅导员姓名
	 * @param account
	 * @param stmt
	 * @param instruId
	 * @return
	 */
	public String queryInstructor(String account, Statement stmt, String instruId) throws NullIdException {
		String name = null;
		//如果班级没有被辅导员管理，那么将不能签到
		if( instruId == null) {
			throw new NullIdException("instruId为空");
		} 
		if( Integer.parseInt(instruId) == 0) {
			throw new NullIdException("instruId不能为0");
		}
		int id = Integer.parseInt(instruId);
		String sql = "select name from tb_instru where id = " + id;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				name = rs.getString(1);
			}
		} catch (Exception e) {
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
	/**
	 * 统计所有的结果
	 * @param name 辅导员姓名
	 * @param time 要查看的时间
	 */
	public Map<String, Integer> count(String name, String time) {
		Connection conn = Db.getConnection();
		//查询签到的sql语句
		String signSql = "select count(account) from tb_sign_in where instructor_name="
				+ "'" + name +"' and time='"+ time +"'";
		//查询请假的sql语句
		String leaveSql = "select count(account) from tb_leave where instructor_name='"
				+ ""+ name +"' and startTime <= '"+time +"' and endTime >= '"+time+"'";
		//查询未签到的sql语句
		String unSignSql = "select count(account) from tb_student where account not in "
				+ "(select account from tb_sign_in where instructor_name='"+name +"' and time='"+time+"')";
		Statement stmt = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery(signSql);
			if(rs1.next()) {
				map.put("signIn", rs1.getInt(1));
			}
			rs2 = stmt.executeQuery(leaveSql);
			if(rs2.next()) {
				map.put("leave", rs2.getInt(1));
			}
			rs3 = stmt.executeQuery(unSignSql);
			if(rs3.next()) {
				map.put("disconnect", rs3.getInt(1));
			}
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
				rs1.close();
				rs2.close();
				rs3.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return  map;
	}
	
	
}
