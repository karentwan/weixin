package com.weixin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.weixin.exception.DuplicateBindException;
import com.weixin.exception.MatchFiledException;
import com.weixin.exception.NameNotFoundException;
import com.weixin.pojo.Instru;
import com.weixin.pojo.InstruInfo;
import com.weixin.pojo.Leave;
import com.weixin.pojo.Sign;
import com.weixin.util.Db;
import com.weixin.util.JSONUtil;

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
			String sql = "select m.name from tb_instru as m right join tb_instructor as f on m.id = f.tb_instru_id where f.openId = '" + openId + "'";
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
	 * ���ظ�����Ϣ��ȥ�����繤�ţ���������ѧԺ
	 * @param openId
	 * @return
	 */
	public InstruInfo getInstruInfo(String openId) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		InstruInfo i = new InstruInfo();
		try {
			stmt = conn.createStatement();
			String sql = "select m.name, ti.nick, ti.college"
					+ " from tb_instru as m right join tb_instructor as f on"
					+ " m.id=f.tb_instru_id left join tb_instruInfo as ti on m.id=ti.instru_id "
					+ "where f.openId = '"+ openId +"'"; 
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				i.setName(rs.getString(1));
				i.setNick(rs.getString(2));
				i.setCollege(rs.getString(3));
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
		return i;
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
			code = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch ( DuplicateBindException e) {
			code = -1;
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
	 * ����Ϣ
	 * @return
	 */
	public int bind(String openId, String name, String nick, String college, String psd) {
		int code = 200;
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id, name, psd from tb_instru where name = '" + name + "'";
		Instru i = new Instru();
		//����Ա��tb_instru���е�id
		int id = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt(1);
				i.setId(id);
				i.setName(rs.getString(2));
				i.setPsd(rs.getString(3));
			} else {
				throw new NameNotFoundException("����Ա����û���ҵ�");
			}
			if( !i.getName().equals(name) || !i.getPsd().equals(psd)) {
				throw new MatchFiledException("�˺��������벻ƥ�䣬��ʧ��!");
			}
			boolean flag = isBind(stmt, name);
			if( flag) {
				throw new DuplicateBindException("���΢�ź��Ѿ�������Ա���ˣ������ظ� ��");
			}
			String insertSql = "insert into tb_instructor values(null, '" + openId + "', " + id + ")";
			stmt.execute(insertSql);
			//���븨��Ա��Ϣ�����ݿ�
			String insertInfoSql = "insert into tb_instruInfo values(null, '" + nick + "', '" + college + "', " + id +")";
			stmt.execute(insertInfoSql);
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
			code = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch ( DuplicateBindException e) {
			code = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (MatchFiledException e) {
			code = -1;
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
System.out.println("dao code :" + code);
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
	
	public String queryAllAndClazz(String name, String time) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		JSONObject obj = new JSONObject();
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			Map<String, List<Sign>> sign = querySignInAndClazz2(stmt, name, time);
			Map<String, List<Sign>> disconnect = queryUnSignAndClazz2(stmt, name, time);
			Map<String, List<Leave>> leave = queryLeaveAndClazz2(stmt, name, time);
			JSONUtil.toJSON2(sign, "signIn", obj);
			JSONUtil.toJSON2(disconnect, "disconnect", obj);
			JSONUtil.leave2JSON2(leave, "leave", obj);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return obj.toString();
	}
	
	/**
	 * ���ϰ༶�Ĳ�ѯ
	 * @param name ����Ա����
	 */
	public Map<String, List<String>> queryUnSignAndClazz(Statement stmt, String name, String time) {
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String sql = "select s.account, s.name, s.id, o.c_id "
				+ "from tb_student as s right join (select c.num, c.id, "
				+ "i.name, c.num as c_id from tb_class as c left join tb_instru as i on "
				+ "c.instructor_id = i.id  where i.name='"
				+ name + "') as o on s.class_id = o.id where s.id not in "
						+ "(select id from tb_student where name in (select name "
						+ "from tb_sign_in where instructor_name = '"
						+ name + "' and time = '"+ time + "'))";
		ResultSet rs = null;
		List<String> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(4);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<String>());
				list = map.get(clazz);
				list.add("{\"account\" :\"" + rs.getString(1) + "\",\"name\" :\""+rs.getString(2) + "\"}");
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
		return map;
	}
	
	public Map<String, List<Sign>> queryUnSignAndClazz2(Statement stmt, String name, String time) {
		Map<String, List<Sign>> map = new HashMap<String, List<Sign>>();
		String sql = "select s.account, s.name, s.id, o.c_id "
				+ "from tb_student as s right join (select c.num, c.id, "
				+ "i.name, c.num as c_id from tb_class as c left join tb_instru as i on "
				+ "c.instructor_id = i.id  where i.name='"
				+ name + "') as o on s.class_id = o.id where s.id not in "
				+ "(select id from tb_student where name in (select name "
				+ "from tb_sign_in where instructor_name = '"
				+ name + "' and time = '"+ time + "' )) order by s.account";
		ResultSet rs = null;
		List<Sign> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(4);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<Sign>());
				list = map.get(clazz);
				Sign s = new Sign();
				s.setAccount(rs.getString(1));
				s.setName(rs.getString(2));
				list.add(s);
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
		return map;
	}
	
	/**
	 * ��ѯ�Ѿ�ǩ���˵�
	 */
	public Map<String, List<String>> querySignInAndClazz(Statement stmt, String name, String time) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String sql = "select oi.num, si.account, si.name from tb_sign_in as si right join "
				+ "( select s.account, o.num from tb_student as s right join (select c.num, "
				+ "c.id as c_id from tb_instru as i right join tb_class as c on i.id = c.instructor_id where i.name='"
				+ name + "') as o on o.c_id = s.class_id) as oi on oi.account = si.account where time = '"
				+ time + "' and si.instructor_name='" + name + "'";
		ResultSet rs = null;
		List<String> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(1);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<String>());
				list = map.get(clazz);
				list.add("{\"account\" :\""+rs.getString(2) + "\",\"name\" :\""+rs.getString(3)+"\"}");
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
		return map;
	}
	
	public Map<String, List<Sign>> querySignInAndClazz2(Statement stmt, String name, String time) {
		Map<String, List<Sign>> map = new HashMap<String, List<Sign>>();
		String sql = "select oi.num, si.account, si.name from tb_sign_in as si right join "
				+ "( select s.account, o.num from tb_student as s right join (select c.num, "
				+ "c.id as c_id from tb_instru as i right join tb_class as c on i.id = c.instructor_id where i.name='"
				+ name + "') as o on o.c_id = s.class_id) as oi on oi.account = si.account where time = '"
				+ time + "' and si.instructor_name='" + name + "' order by account";
System.out.println("signIn sql:" + sql);
		ResultSet rs = null;
		List<Sign> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(1);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<Sign>());
				list = map.get(clazz);
				Sign s = new Sign();
				s.setAccount(rs.getString(2));
				s.setName(rs.getString(3));
				list.add(s);
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
		return map;
	}
	
	/**
	 * ��ѯ��ٵ�����
	 */
	public Map<String, List<String>> queryLeaveAndClazz(Statement stmt, String name, String time) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String sql = "select l.account, l.name, l.reasonDetail, o.num from "
				+ "tb_leave as l left join ( select c.num, s.account from tb_class as c "
				+ "left join tb_student as s on s.class_id = c.id ) as o using (account) where l.instructor_name = '"
				+ name + "' and l.startTime <= '" + time +"' and l.endTime >= '" + time +"'";
		ResultSet rs = null;
		List<String> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(4);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<String>());
				list = map.get(clazz);
				list.add("{ \"account\" :\"" + rs.getString(1) + "\",\"name\" : \""+rs.getString(2) + "\", \"reason\":\"" + rs.getString(3) + "\"}");
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
		return map;
	}
	
	public Map<String, List<Leave>> queryLeaveAndClazz2(Statement stmt, String name, String time) {
		Map<String, List<Leave>> map = new HashMap<String, List<Leave>>();
		String sql = "select l.account, l.name, l.reasonDetail, o.num from "
				+ "tb_leave as l left join ( select c.num, s.account from tb_class as c "
				+ "left join tb_student as s on s.class_id = c.id ) as o using (account) where l.instructor_name = '"
				+ name + "' and l.startTime <= '" + time +"' and l.endTime >= '" + time +"' order by account";
		ResultSet rs = null;
		List<Leave> list = null;
		try {
			rs = stmt.executeQuery(sql);
			while( rs.next() ) {
				String clazz = rs.getString(4);
				if( map.get(clazz) == null) 
					map.put(clazz, new ArrayList<Leave>());
				list = map.get(clazz);
				Leave l = new Leave();
				l.setAccount(rs.getString(1));
				l.setName(rs.getString(2));
				l.setReason(rs.getString(3));
				list.add(l);
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
		return map;
	}
	
	/**
	 * ���ݹ��Ż�ȡ����Ա����Ϣ
	 * @param name ����Ա�Ĺ���
	 * @return
	 */
	public InstruInfo obtainInfoFromName(String name) {
		InstruInfo i = new InstruInfo();
		Connection conn = Db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select ti.nick, ti.college from "
					+ "tb_instru as i left join tb_instruInfo as ti on ti.instru_id = "
					+ "i.id where i.name='" + name + "'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				i.setNick(rs.getString(1));
				i.setCollege(rs.getString(2));
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
		return i;
	}
	
	
} 
