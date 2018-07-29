package org.lanqiao.qq.biz;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lanqiao.qq.entity.AddFriendMsg;
import org.lanqiao.qq.entity.AddFriendResultMsg;
import org.lanqiao.qq.entity.FindFriends;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.entity.registerResult;
import org.lanqiao.qq.ui.MainFrame;
import org.lanqiao.qq.util.DialogUtil;
import org.lanqiao.qq.util.ObjectUtil;

/**
 * 2018.7.5
 * �Ϳͻ�����ص�ϵͳ��ҵ���߼�ʵ��
 * @author dhc
 *
 */
public class SysBiz {
	private Socket s;
	//������Ҫ���õ��Ǹ�socket������
	public SysBiz(Socket s){
		this.s = s;
	}
	/**
	 * ��¼��֤����֤�û����������Ƿ���ȷ
	 * @param u
	 * @return ������ؿձ�ʾ��¼ʧ�ܣ����������Usr�������ʾ�ɹ�
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Usr login(Usr u) throws IOException, ClassNotFoundException {
		//������д�������
		ObjectUtil.writeObject(s,u);
		//��ȡ����˵���֤����
		return (Usr) ObjectUtil.readObject(s);
	}
	/**
	 * ע�ᣬ����ע����Ϣ��֤��ͬʱ��Usr����д������ˣ�����һ��ע��������
	 * @param u
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public registerResult register(Usr u) throws IOException, ClassNotFoundException {
		registerResult rr = new registerResult();
		//ע����֤
		//�û��������볤��(6-12)
		//����(0-150)
		// ������֤(��ʽ)
		int nicknameLength = u.getNickname().length();
		int pwdLength = u.getPassWord().length();
		
		if(nicknameLength >= 2 && nicknameLength <= 12) {
			rr.setNicknameFlag(true);
			if(pwdLength >=6 && pwdLength <= 12) {
				rr.setPwdFlag(true);
				if(u.getAge() >=0 && u.getAge() <= 150) {
					rr.setAgeFlag(true);
					if(SysBiz.emailFormat(u.getEmail())) {
						ObjectUtil.writeObject(s, u);
						rr = (registerResult) ObjectUtil.readObject(s);
						rr.setNicknameFlag(true);
						rr.setPwdFlag(true);
						rr.setAgeFlag(true);
						rr.setEmailFlag1(true);
					} else {
						rr.setEmailFlag1(false);
						rr.setSuccess(false);
					}	
				} else {
					rr.setAgeFlag(false);
					rr.setSuccess(false);
				}
			} else {
				rr.setPwdFlag(false);
				rr.setSuccess(false);
			}
		} else {
			rr.setNicknameFlag(false);
			rr.setSuccess(false);
		}
		return rr;
	}
	@SuppressWarnings("unchecked")
	/**
	 * ֻ�����Ͳ�����Ϣ
	 * @param ff
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void findFriends(FindFriends ff) throws ClassNotFoundException, IOException{
		ObjectUtil.writeObject(s, ff);
	}
	/**
	 * ��ʾ������ѵ���Ϣ
	 * @param msg
	 * @param mf
	 * @throws IOException
	 */
	public void showAddFriendMsg(AddFriendMsg msg,MainFrame mf) throws IOException {
		boolean a = DialogUtil.showConfirm(msg.getFrom().getNickname()+"���������Ϊ���ѣ��Ƿ�ͬ�⣡");
		if(a) { //ͬ�����Ϊ����ʱ����Ҫ���µĺ���ͷ����ӵ��������У�����Ҫ��������Ϣ�����ݿ��н��и���
			mf.addNewFriend(msg.getFrom());
		} else {
			System.out.println("bu ͬ��");
		}
		//��ͬ��ʲô��������
		AddFriendResultMsg afrm = new AddFriendResultMsg();
		afrm.setAgree(a);
		afrm.setFrom(msg.getTo());
		afrm.setTo(msg.getFrom());
		ObjectUtil.writeObject(s, afrm);
		
	}
	/**
	 * ��ʾ��Ӻ��ѽ��
	 * @param afrm
	 * @param mf
	 */
	public void showAddFriendResultMsg(AddFriendResultMsg afrm,MainFrame mf) {
		if(afrm.isAgree()) {
			DialogUtil.showInfo(afrm.getFrom().getUserAccount()+"ͬ�������Ϊ����");
			mf.addNewFriend(afrm.getFrom());
		} else {
			DialogUtil.showInfo(afrm.getFrom().getUserAccount()+"��ͬ�������Ϊ����");
		}
	}
	/**
	 * �����ʽ��֤
	 * @param email
	 * @return
	 */
	public static boolean emailFormat(String email) 
    { 
        boolean tag = true; 
        final String pattern1 = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}"; 
        final Pattern pattern = Pattern.compile(pattern1); 
        final Matcher mat = pattern.matcher(email); 
        if (!mat.find()) { 
            tag = false; 
        } 
        return tag; 
    } 
}
