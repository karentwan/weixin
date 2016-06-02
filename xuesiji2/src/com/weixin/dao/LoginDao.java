package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.weixin.util.Db;

/**
 * 登陆dao
 * @author wan
 */
public class LoginDao {
	
	private static LoginDao ld = null;
	
	public static LoginDao getInstance() {
		if( ld == null) 
			ld = new LoginDao();
		return ld;
	}
	
	/**
	 * 验证用户名和密码是否正确
	 * @param name
	 * @param psd
	 * @return
	 */
	public boolean verify(String name, String psd) {
		boolean flag = false;
		Connection conn = Db.getConnection();
		String sql = "select psd from tb_instru where name = '" + name + "'";
		Statement stmt = null;
		ResultSet rs = null;
		String tempStr = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				tempStr =rs.getString(1);
			}
			if( tempStr != null && psd.equals(tempStr))
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
	
	
}
