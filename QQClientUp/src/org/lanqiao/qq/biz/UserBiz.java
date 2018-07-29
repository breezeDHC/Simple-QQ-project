package org.lanqiao.qq.biz;

import java.io.IOException;
import java.net.Socket;

import org.lanqiao.qq.entity.AddFriendMsg;
import org.lanqiao.qq.entity.GetPwd;
import org.lanqiao.qq.entity.Message;
import org.lanqiao.qq.entity.SendFileMsg;
import org.lanqiao.qq.ui.MainFrame;
import org.lanqiao.qq.util.ObjectUtil;

/**
 * 2018.7.8
 * ʵ�ֿͻ������û���ص��߼�ʵ��
 * @author dhc
 *
 */
public class UserBiz {
	private Socket s;
	
	public UserBiz(Socket s) {
		this.s = s;
	}
	/**
	 * ��Ӻ��ѣ������˷��ͺ�������
	 * @throws IOException 
	 */
	public void addFriend(AddFriendMsg msg,Socket s) throws IOException {
		ObjectUtil.writeObject(s, msg);
	}
	/**
	 * ������Ϣ������������
	 * @param m
	 * @throws IOException 
	 */
	public void sendMsg(Message m) throws IOException {
		ObjectUtil.writeObject(s, m);
	}
	/**
	 * ��ʾ�û���Ϣ�����û�ͷ�񶶶����˴���Message���󴫸�MainFrame����������
	 * @param m
	 * @param mf
	 */
	public void showMsg(Message m,MainFrame mf) {
		mf.showMsg(m);
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void sendFileMsg(SendFileMsg msg) throws IOException {
		ObjectUtil.writeObject(s, msg);
	}
	
	/**
	 * �һ����룬���һ��������д�������
	 * @param gp
	 * @throws IOException
	 */
	public void getPwd(GetPwd gp) throws IOException {
		ObjectUtil.writeObject(s, gp);
	}
}
