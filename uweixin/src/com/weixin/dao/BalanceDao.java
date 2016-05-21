package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.weixin.util.Db;

/**
 * 查余额
 * @author wan
 */
public class BalanceDao {

	private static BalanceDao bd = null;
	
	public static BalanceDao getInstance() {
		if( bd == null)
			bd = new BalanceDao();
		return bd;
	}
	
	/**
	 * 查询余额和姓名
	 * @param openId
	 * @return
	 */
	public List<String> queryBalance(String openId) {
		Connection conn = Db.getConnection();
		String sql = "select b.balance, o.name, o.userName from tb_balance as b right join (select u.id, "
				+ "u.userName, bi.name from tb_user as u left join" 
				+ " tb_bind as bi on u.id = bi.user_id) as o on o.id = b.user_id where o.userName = (select userName "
				+ "from tb_user as u1 left join tb_bind as b1 on u1.id =" 
				+ "b1.user_id where b1.openId = '" + openId +"')";
System.out.println("sql:" + sql);
		Statement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add(rs.getString(3));
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
	
	public List<String> queryConsum(String user) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select c.consum, c.time from tb_consum as c left join tb_user as u on u.id = c.user_id where u.userName = '" + user + "'";
		List<String> list = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while( rs.next()) {
//				list.add("{\"" + rs.getString(1)+"\",\"" + rs.getString(2)+"\"}");
				list.add(rs.getString(1));
				list.add(rs.getString(2));
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
	
	
	
}
