package com.weixin.dao;

/**
 * ����ѯ��Ϣ�ֿ�����������ٲ�ѯ������
 * ǩ����Ϣ������
 * δǩ����Ϣ������
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
