package com.weixin.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 创建数据库连接类
 * @author wan
 */
public class Db {

	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(WeixinUtil.prop.getProperty("driver"));
			conn = DriverManager.getConnection(WeixinUtil.prop.getProperty("url"),
					WeixinUtil.prop.getProperty("user"), WeixinUtil.prop.getProperty("psd"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
}
