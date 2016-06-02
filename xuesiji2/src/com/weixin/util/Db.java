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

	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	
	static {
		try {
			ds.setDriverClass(WeixinUtil.prop.getProperty("c3p0.driverClass"));
			ds.setJdbcUrl(WeixinUtil.prop.getProperty("c3p0.jdbcUrl"));
			ds.setUser(WeixinUtil.prop.getProperty("c3p0.user"));
			ds.setPassword(WeixinUtil.prop.getProperty("c3p0.password"));
			ds.setMaxPoolSize(Integer.parseInt(WeixinUtil.prop.getProperty("c3p0.maxPoolSize")));
			ds.setMinPoolSize(Integer.parseInt(WeixinUtil.prop.getProperty("c3p0.minPoolSize")));
			ds.setInitialPoolSize(Integer.parseInt(WeixinUtil.prop.getProperty("c3p0.initialPoolSize")));
			ds.setMaxStatements(Integer.parseInt(WeixinUtil.prop.getProperty("c3p0.maxStatementSize")));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
