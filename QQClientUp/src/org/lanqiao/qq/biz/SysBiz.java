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
 * 和客户端相关的系统的业务逻辑实现
 * @author dhc
 *
 */
public class SysBiz {
	private Socket s;
	//这里需要将用的那个socket传过来
	public SysBiz(Socket s){
		this.s = s;
	}
	/**
	 * 登录验证，验证用户名和密码是否正确
	 * @param u
	 * @return 如果返回空表示登录失败，如果返回了Usr对象则表示成功
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Usr login(Usr u) throws IOException, ClassNotFoundException {
		//将对象写到服务端
		ObjectUtil.writeObject(s,u);
		//读取服务端的验证界面
		return (Usr) ObjectUtil.readObject(s);
	}
	/**
	 * 注册，包括注册信息验证，同时向Usr对象写到服务端，返回一个注册结果对象
	 * @param u
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public registerResult register(Usr u) throws IOException, ClassNotFoundException {
		registerResult rr = new registerResult();
		//注册验证
		//用户名和密码长度(6-12)
		//年龄(0-150)
		// 邮箱验证(格式)
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
	 * 只负责发送查找消息
	 * @param ff
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void findFriends(FindFriends ff) throws ClassNotFoundException, IOException{
		ObjectUtil.writeObject(s, ff);
	}
	/**
	 * 显示添加朋友的消息
	 * @param msg
	 * @param mf
	 * @throws IOException
	 */
	public void showAddFriendMsg(AddFriendMsg msg,MainFrame mf) throws IOException {
		boolean a = DialogUtil.showConfirm(msg.getFrom().getNickname()+"请求添加你为好友，是否同意！");
		if(a) { //同意添加为好友时，需要将新的好友头像添加到主界面中，还需要将好友信息在数据库中进行更新
			mf.addNewFriend(msg.getFrom());
		} else {
			System.out.println("bu 同意");
		}
		//不同意什么都不用做
		AddFriendResultMsg afrm = new AddFriendResultMsg();
		afrm.setAgree(a);
		afrm.setFrom(msg.getTo());
		afrm.setTo(msg.getFrom());
		ObjectUtil.writeObject(s, afrm);
		
	}
	/**
	 * 显示添加好友结果
	 * @param afrm
	 * @param mf
	 */
	public void showAddFriendResultMsg(AddFriendResultMsg afrm,MainFrame mf) {
		if(afrm.isAgree()) {
			DialogUtil.showInfo(afrm.getFrom().getUserAccount()+"同意添加你为好友");
			mf.addNewFriend(afrm.getFrom());
		} else {
			DialogUtil.showInfo(afrm.getFrom().getUserAccount()+"不同意添加你为好友");
		}
	}
	/**
	 * 邮箱格式验证
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
