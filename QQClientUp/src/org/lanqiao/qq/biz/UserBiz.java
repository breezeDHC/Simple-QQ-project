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
 * 实现客户端与用户相关的逻辑实现
 * @author dhc
 *
 */
public class UserBiz {
	private Socket s;
	
	public UserBiz(Socket s) {
		this.s = s;
	}
	/**
	 * 添加好友，向服务端发送好友请求
	 * @throws IOException 
	 */
	public void addFriend(AddFriendMsg msg,Socket s) throws IOException {
		ObjectUtil.writeObject(s, msg);
	}
	/**
	 * 发送消息类对象给服务器
	 * @param m
	 * @throws IOException 
	 */
	public void sendMsg(Message m) throws IOException {
		ObjectUtil.writeObject(s, m);
	}
	/**
	 * 显示用户消息，让用户头像抖动，此处将Message对象传给MainFrame，让它处理
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
	 * 找回密码，将找回密码对象写到服务端
	 * @param gp
	 * @throws IOException
	 */
	public void getPwd(GetPwd gp) throws IOException {
		ObjectUtil.writeObject(s, gp);
	}
}
