package com.weixin.dao;

/**
 * 将查询信息分开来，例如请假查询单独查
 * 签到信息单独查
 * 未签到信息单独查
 * @author wan
 */
public class SubQueryDao {

	private static SubQueryDao sqd = null;
	
	public static SubQueryDao getInstance() {
		if( sqd == null)
			sqd = new SubQueryDao();
		return sqd;
	}
	
	public void querySign() {
		
	}
	
	
	
}
