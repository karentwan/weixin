package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.weixin.util.Db;

/**
 * µÇÂ½dao
 * @author wan
 */
public class LoginDao {
	
	private static LoginDao ld = null;
	
	public static LoginDao getInstance() {
		if(ld == null)
			ld = new LoginDao();
		return ld;
	}
	
	public boolean verify(String name, String psd) {
		boolean flag = false;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from tb_admin where user = '" + name + "'"
				+ " and psd = '" + psd + "'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
}
