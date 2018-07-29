package org.lanqiao.qq.dao;
/**
 * 时间：2018.7.4
 * 功能：实体类Usr的接口类，用来处理与数据库相关的操作
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
	 * 用户登录验证，判断账号密码是否错误
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
			//如果查询出来结果，则表明一定有这个用户
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
	 * 如果返回1表示注册成功，如果返回0表示有相同的邮箱，注册失败
	 * @param u
	 * @return
	 */
	public int addUser(Usr u) {
		int result = 1;
		//判断是否有相同的邮箱
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
		//增加用户
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
	 * 功能：获取下一个账号，根据account表获取一个id，用来拼接成java_xxxx类似的账号名，
	 * 实现；从account表获取当前的id+1，并且将id+1更新到account表中，同时返回id+1
	 * 注意：此处的查询和更新操作需要是一个事物，如果某个操作错误需要回滚
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
			//设置非自动提交后，需要手动提交
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			//当出现异常时需要回滚当前事物
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			//重新设置为自动提交
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
	 * 根据账号名，返回一个好友列表，
	 * @param account
	 * @return
	 * @throws SQLException 
	 */
	public List<Usr> queryFriends(String account){
		List<Usr> ulist = new ArrayList<Usr>();
		//用sql语句直接在数据库中查询出好用信息，并组装成Usr对象，依次添加到ulist中
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
	 * 根据账号返回一个Usr对象
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
	 * 返回一个List<Usr>，返回所有用户
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
	 * 添加好友时更新好友表信息
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
			//设置非自动提交后，需要手动提交
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			//当出现异常时需要回滚当前事物
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			//重新设置为自动提交
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
