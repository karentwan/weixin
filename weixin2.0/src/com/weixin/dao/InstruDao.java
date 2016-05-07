package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weixin.exception.DuplicateBindException;
import com.weixin.exception.NameNotFoundException;
import com.weixin.util.Db;

public class InstruDao {
	
	private static InstruDao instru = null;
	
	public static InstruDao getInstance() {
		if(instru == null)
			instru = new InstruDao();
		return instru;
	}
	
	/**
	 * ��֤����Ա�Ƿ��Ѿ���
	 * @param openId ����Ա��openId
	 * @return
	 */
	public String verify(String openId) {
		String name = null;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
//			String sql = "select name from tb_instructor where openId = '" + openId + "'";
			String sql = "select m.name from tb_instru as m right join tb_instructor as f on m.id = f.tb_instru_id where f.openId = '" + openId + "'";
System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				name = rs.getString(1);
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
		return name;
	}
	
	/**
	 * ������Ա��openId������������
	 * @param openId ����Ա��openId
	 * @param name ����Ա������
	 * @return
	 */
	public int bind(String openId, String name) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id from tb_instru where name = '" + name + "'";
		//����Ա��tb_instru���е�id
		int id = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt(1);
			} else {
				throw new NameNotFoundException("����Ա����û���ҵ�");
			}
			boolean flag = isBind(stmt, name);
			if( flag) {
				throw new DuplicateBindException("���΢�ź��Ѿ�������Ա���ˣ������ظ� ��");
			}
			String insertSql = "insert into tb_instructor values(null, '" + openId + "', " + id + ")";
			stmt.execute(insertSql);
			conn.commit();
		} catch (SQLException e) {
			try {//�ع�
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			code = -1;
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			code = 404;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch ( DuplicateBindException e) {
			code = 406;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
		return code;
	}
	
	
	/**
	 * ������ݸ���Ա��������ѯǩ����ѧ��
	 * @param name ����Ա����
	 * @return
	 */
	public Map<String, List<String>> querySign(String name, String time) {
		List<String> listName = new ArrayList<String>();
		List<String> listAccount = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("account", listAccount);
		map.put("name", listName);
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select account, name from tb_sign_in where instructor_name "
				+ "='" + name + "' and time = '" + time + "'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while( rs.next()) {
				String account = rs.getString(1);
				String stuName = rs.getString(2);
				listAccount.add(account);
				listName.add(stuName);
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
	 * ��ѯδǩ��������
	 * @param name ����Ա������
	 * @param time ʱ��
	 * @return
	 */
	public Map<String, List<String>> queryUnSign(String name, String time) {
		List<String> listName = new ArrayList<String>();
		List<String> listAccount = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("account", listAccount);
		map.put("name", listName);
		Connection conn = Db.getConnection();
		String sql ="select s.account, s.name, s.id from tb_student as s right join (select c.num, c.id, i.name from "
				+ "tb_class as c left join tb_instru as i on c.instructor_id = i.id  where i.name='"
				+name+"') as o on s.class_id = o.id where s.id not in (select id from tb_student where name in (select name from tb_sign_in where instructor_name = '"
						+ name +"' and time = '" + time +"'))";
System.out.println("sql:" + sql);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String tempAcc = rs.getString(1);
				String tempName = rs.getString(2);
				listName.add(tempName);
				listAccount.add(tempAcc);
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
	 * ���ݸ���Ա��������ѯ��ٵ�����
	 * @param name ����Ա������ 
	 * @param time ʱ��
	 */
	public Map<String, List<String>> queryLeave(String name, String time) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> accountList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		map.put("account", accountList);
		map.put("name", nameList);
		Connection conn = Db.getConnection();
		String sql = "select account, name from tb_leave where startTime <= '" +time+"' and endTime >= '" + time +""
				+ "' and instructor_name = '" + name + "'";
System.out.println("leave sql:" + sql);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String tempAcc = rs.getString(1);
				String tempName = rs.getString(2);
				accountList.add(tempName);
				nameList.add(tempAcc);
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
	 * �жϸ���Ա�Ƿ��Ѿ�����΢�ź�
	 * @param name ����Ա����
	 * @return true ��ʾ�Ѿ�����΢�ź�
	 * 	false ��ʾ��û�а�΢�ź�
	 */
	private boolean isBind(Statement stmt, String name) {
		boolean flag = false;
		String sql = "select * from tb_instructor as d left join tb_instru as b on d.tb_instru_id = b.id where b.name = "
				+ "'" + name + "'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				flag = true;
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
		return flag;
	}
	
}
