package org.lanqiao.qq.biz;

import java.util.ArrayList;
import java.util.List;

import org.lanqiao.qq.dao.UsrDao;
import org.lanqiao.qq.entity.FindFriends;
import org.lanqiao.qq.entity.GetPwd;
import org.lanqiao.qq.entity.GetPwdResult;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.entity.registerResult;
import org.lanqiao.qq.util.ObjectUtil;

/**
 * 2018.7.5
 * 处理用户的逻辑实现
 * @author dhc
 *
 */
public class UsrBiz {
	private UsrDao uDao;
	
	public UsrBiz() {
		uDao = new UsrDao();
	}
	/**
	 * 用户登录验证，如果用户名密码正确就返回用户的所用信息，包括好友信息
	 * @param u
	 * @return
	 */
	public Usr isLogin(Usr u1) {
		u1 = uDao.isLogin(u1.getUserAccount(), u1.getPassWord());
		//好友信息！注意用户名或密码错误时，没有返回信息
		if(u1 != null) {
			u1.setFriends(uDao.queryFriends(u1.getUserAccount()));
		}
		return u1;
	}
	
	/**
	 * 用户注册逻辑实现，返回一个registerResult
	 * 2018.7.6
	 */
	public registerResult register(Usr u) {
		registerResult rs = new registerResult();
		int id = uDao.getNextAccount();
		String account = String.format("java_%05d%n", id).trim();
		u.setUserAccount(account);
		int ra = uDao.addUser(u);
		if(ra == 1) {
			System.out.println("注册成功");
			rs.setResultMessage(account);
			rs.setSuccess(true);
			rs.setEmailFlag2(true);
		} else {
			System.out.println("注册失败！");
			rs.setResultMessage("注册失败！");
			rs.setSuccess(false);
			rs.setEmailFlag2(false);
		}
		return rs;
	}
	/**
	 * 用户查找逻辑实现,根据查找类对象的不同查找类型进行相应的查找，返回一个List<Usr>
	 */
	public List<Usr> findFriends(FindFriends ff){
		List<Usr> flist = new ArrayList<Usr>();
		if(ff.getType() == FindFriends.ONE) {
			Usr u = uDao.findByUsrAccount(ff.getAccount());
			if(u != null) {
				flist.add(u);
			}
		} else if(ff.getType() == FindFriends.ALL ){
			flist.addAll(uDao.findAllUsr(ff.getU()));
		} else {
			flist.addAll(uDao.finBySomeInfo(ff.getAccount(),ff.getU()));
		}
		return flist;
	}

	public void addFriend(Usr f, Usr t) {
		uDao.addFriend(f, t);
	}
	
	public GetPwdResult getPwd(GetPwd gp) {
		return uDao.getPwd(gp);
	}
}
