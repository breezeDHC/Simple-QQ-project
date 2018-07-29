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
 * �����û����߼�ʵ��
 * @author dhc
 *
 */
public class UsrBiz {
	private UsrDao uDao;
	
	public UsrBiz() {
		uDao = new UsrDao();
	}
	/**
	 * �û���¼��֤������û���������ȷ�ͷ����û���������Ϣ������������Ϣ
	 * @param u
	 * @return
	 */
	public Usr isLogin(Usr u1) {
		u1 = uDao.isLogin(u1.getUserAccount(), u1.getPassWord());
		//������Ϣ��ע���û������������ʱ��û�з�����Ϣ
		if(u1 != null) {
			u1.setFriends(uDao.queryFriends(u1.getUserAccount()));
		}
		return u1;
	}
	
	/**
	 * �û�ע���߼�ʵ�֣�����һ��registerResult
	 * 2018.7.6
	 */
	public registerResult register(Usr u) {
		registerResult rs = new registerResult();
		int id = uDao.getNextAccount();
		String account = String.format("java_%05d%n", id).trim();
		u.setUserAccount(account);
		int ra = uDao.addUser(u);
		if(ra == 1) {
			System.out.println("ע��ɹ�");
			rs.setResultMessage(account);
			rs.setSuccess(true);
			rs.setEmailFlag2(true);
		} else {
			System.out.println("ע��ʧ�ܣ�");
			rs.setResultMessage("ע��ʧ�ܣ�");
			rs.setSuccess(false);
			rs.setEmailFlag2(false);
		}
		return rs;
	}
	/**
	 * �û������߼�ʵ��,���ݲ��������Ĳ�ͬ�������ͽ�����Ӧ�Ĳ��ң�����һ��List<Usr>
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
