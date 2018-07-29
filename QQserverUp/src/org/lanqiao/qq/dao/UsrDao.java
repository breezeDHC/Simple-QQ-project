package org.lanqiao.qq.dao;
/**
 * ʱ�䣺2018.7.4
 * ���ܣ�ʵ����Usr�Ľӿ��࣬�������������ݿ���صĲ���
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lanqiao.qq.entity.GetPwd;
import org.lanqiao.qq.entity.GetPwdResult;
import org.lanqiao.qq.entity.Usr;

public class UsrDao extends BaseDao{
	private Connection conn = null;
	private PreparedStatement stat = null;
	private ResultSet rs = null;
	private List<Usr> ulist;
	private String sql;
	/**
	 * �û���¼��֤���ж��˺������Ƿ����
	 * @param account
	 * @param password
	 * @return
	 */
	public Usr isLogin(String account, String password) {
		String sql = "select * from Usr where useraccount = ? and userpassword = ?";
		try {
			conn = getConn();
			stat = conn.prepareStatement(sql);
			stat.setString(1, account);
			stat.setString(2, password);
			rs = stat.executeQuery();
			//�����ѯ��������������һ��������û�
			if(rs.next()) {
				Usr u = new Usr();
				u.setUserAccount(rs.getString("userAccount"));
				u.setPassWord(rs.getString("userpassWord"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
			
		}
		return null;
		
	}
	/**
	 * �������1��ʾע��ɹ����������0��ʾ����ͬ�����䣬ע��ʧ��
	 * @param u
	 * @return
	 */
	public int addUser(Usr u) {
		int result = 1;
		//�ж��Ƿ�����ͬ������
		String sql = "select * from usr where email = ?";
		try {
			conn = getConn();
			stat = conn.prepareStatement(sql);
			stat.setString(1, u.getEmail());
			rs = stat.executeQuery();
			if(rs.next()) {
				result = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
		}
		//�����û�
		if(result == 1) {
			String sql1 = "insert into usr values(?,?,?,?,?,?)";
			try {
				conn = getConn();
				stat = conn.prepareStatement(sql1);
				stat.setString(1, u.getUserAccount());
				stat.setString(2, u.getPassWord());
				stat.setString(3, u.getNickname());
				stat.setInt(4, u.getAge());
				stat.setString(5, u.getEmail());
				stat.setString(6, u.getImg());
				stat.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeAll(conn,stat,rs);
			}
		}
		return result;
		
	}

	/**
	 * ���ܣ���ȡ��һ���˺ţ�����account���ȡһ��id������ƴ�ӳ�java_xxxx���Ƶ��˺�����
	 * ʵ�֣���account���ȡ��ǰ��id+1�����ҽ�id+1���µ�account���У�ͬʱ����id+1
	 * ע�⣺�˴��Ĳ�ѯ�͸��²�����Ҫ��һ��������ĳ������������Ҫ�ع�
	 * @return
	 */
	public int getNextAccount() {
		int id = -1;
		String sql = "select * from Account";
		try {
			conn = getConn();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id") + 1;
				String sql1 = "update account set id = ?";
				stat = conn.prepareStatement(sql1);
				stat.setInt(1, id);
				stat.executeUpdate();
			}
			//���÷��Զ��ύ����Ҫ�ֶ��ύ
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			//�������쳣ʱ��Ҫ�ع���ǰ����
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			//��������Ϊ�Զ��ύ
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeAll(conn,stat,rs);
		}
		return id;
		
	}
	/**
	 * �����˺���������һ�������б�
	 * @param account
	 * @return
	 * @throws SQLException 
	 */
	public List<Usr> queryFriends(String account){
		List<Usr> ulist = new ArrayList<Usr>();
		//��sql���ֱ�������ݿ��в�ѯ��������Ϣ������װ��Usr����������ӵ�ulist��
		sql = "select * from usr where userAccount in (select friendAccount from friends where userAccount = ?)";
		conn = getConn();
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, account);
			rs = stat.executeQuery();
			while(rs.next()) {
				Usr u = new Usr();
				u.setUserAccount(rs.getString("userAccount"));
				u.setPassWord(rs.getString("userpassWord"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				ulist.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
		}
		
		return ulist;
	}
	/**
	 * �����˺ŷ���һ��Usr����
	 */
	public Usr findByUsrAccount(String nickname) {
		String sql = "select * from usr where nickname = ?";
		try {
			conn = getConn();
			stat = conn.prepareStatement(sql);
			stat.setString(1, nickname);
			rs = stat.executeQuery();
			if(rs.next()) {
				Usr u = new Usr();
				u.setUserAccount(rs.getString("userAccount"));
				u.setPassWord(rs.getString("userpassWord"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
		}
		return null;
	}
	
	/**
	 * ����һ��List<Usr>�����������û�
	 */
	public List<Usr> findAllUsr(Usr u1){
		List<Usr> flist = new ArrayList<Usr>();
		String sql = "select * from usr where useraccount != ?";
		try {
			conn = getConn();
			stat = conn.prepareStatement(sql);
			stat.setString(1, u1.getUserAccount());
			rs = stat.executeQuery();
			while(rs.next()) {
				Usr u = new Usr();
				u.setUserAccount(rs.getString("useraccount"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				u.setPassWord(rs.getString("userpassword"));
				flist.add(u);
			}
		} catch( SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
		}
		return flist;
	}
	
	public List<Usr> finBySomeInfo(String account, Usr u1) {
		List<Usr> flist = new ArrayList<Usr>();
		String sql = "select * from usr where nickname like ? and useraccount != ?";
		try {
			conn = getConn();
			stat = conn.prepareStatement(sql);
			stat.setString(1, "%"+account+"%");
			stat.setString(2, u1.getUserAccount());
			rs = stat.executeQuery();
			while(rs.next()) {
				Usr u = new Usr();
				u.setUserAccount(rs.getString("useraccount"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				u.setPassWord(rs.getString("userpassword"));
				flist.add(u);
			}
		} catch( SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
		}
		return flist;
	}
	/**
	 * ��Ӻ���ʱ���º��ѱ���Ϣ
	 * @param f
	 * @param t
	 */
	public void addFriend(Usr f, Usr t) {
		String sql = "insert into friends values(?,?)";
		try {
			conn = getConn();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(sql);
			stat.setString(1, f.getUserAccount());
			stat.setString(2, t.getUserAccount());
			stat.executeUpdate();
			stat.clearParameters();
			stat.setString(1, t.getUserAccount());
			stat.setString(2, f.getUserAccount());
			stat.executeUpdate();
			//���÷��Զ��ύ����Ҫ�ֶ��ύ
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			//�������쳣ʱ��Ҫ�ع���ǰ����
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			//��������Ϊ�Զ��ύ
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeAll(conn,stat,rs);
		}
	}
	public GetPwdResult getPwd(GetPwd gp) {
		GetPwdResult gpr = new GetPwdResult();
		String sql = "select * from usr where useraccount = ? and email = ?";
		try {
			conn = getConn();
			stat = conn.prepareStatement(sql);
			stat.setString(1, gp.getAccount());
			stat.setString(2, gp.getEmail());
			rs = stat.executeQuery();
			if(rs.next()) {
				gpr.setSuccess(true);
				gpr.setPwd(rs.getString("userpassword"));
			} else {
				gpr.setSuccess(false);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn,stat,rs);
		}
		return gpr;
	}
	
}
