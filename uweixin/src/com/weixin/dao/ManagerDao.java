package com.weixin.dao;

/**
 * admin�����Ա��dao
 * @author wan
 */
public class ManagerDao {
	
	private static ManagerDao md = null;
	
	public static ManagerDao getInstance() {
		if( md == null)
			md = new ManagerDao();
		return md;
	}
	

	

}
