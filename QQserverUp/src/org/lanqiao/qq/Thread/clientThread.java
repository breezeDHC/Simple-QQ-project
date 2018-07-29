package org.lanqiao.qq.Thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.lanqiao.qq.biz.ServerBiz;
import org.lanqiao.qq.biz.UsrBiz;
import org.lanqiao.qq.entity.AddFriendMsg;
import org.lanqiao.qq.entity.AddFriendResultMsg;
import org.lanqiao.qq.entity.FindFriends;
import org.lanqiao.qq.entity.FrameDistroy;
import org.lanqiao.qq.entity.GetPwd;
import org.lanqiao.qq.entity.GetPwdResult;
import org.lanqiao.qq.entity.Message;
import org.lanqiao.qq.entity.SendFileMsg;
import org.lanqiao.qq.entity.SendFileResultMsg;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.util.ObjectUtil;

/**
 * 2018.7.5 给每一个客户端启动一个线程，分别处理各自的事情
 * 
 * @author dhc
 *
 */
public class clientThread extends Thread {
	private Socket s;
	private UsrBiz uBiz;

	public clientThread(Socket s) {
		this.s = s;
		uBiz = new UsrBiz();
	}
	/**
	 * 备注：if-else if中的逻辑应该放在逻辑层，此处调用函数即可，想客户端的线程那样
	 */
	public void run() {
	
			try {
				while (true) {
				Object o = ObjectUtil.readObject(s);
				if (o instanceof Usr) {
					Usr u = (Usr) o;
					// 判断是登录还是注册
					if (u.getUserAccount() != null) {
						// 登录验证
						u = uBiz.isLogin(u);
						if(u != null) { //如果登录成功，就将用户的账号和socket添加到map中
							//判断当前用户是否已经登录
							Socket st = ServerBiz.getUserInfo().get(u.getUserAccount());
							if(st != null) { //当用户已经登录时
								FrameDistroy fd = new FrameDistroy();
								fd.setDistroy(true);
								ServerBiz.getUserInfo().replace(u.getUserAccount(), st, s);  //替换socket
								//给之前登录窗口发送窗口销毁消息
								ObjectUtil.writeObject(st, fd);
							} else {
								ServerBiz.getUserInfo().put(u.getUserAccount(), s);
							}
							// 返回验证结果给客户端
							ObjectUtil.writeObject(s, u);
						}
						
					} else {
						ObjectUtil.writeObject(s, uBiz.register(u));
					}
				} else if (o instanceof FindFriends) { // 如果是查找类,进行查找
					FindFriends ff = (FindFriends) o;
					System.out.println(ff.getAccount()+"--"+ff.getType()+"--"+ff.getU().getUserAccount());
					List<Usr> flist = new ArrayList<Usr>();
					flist = uBiz.findFriends(ff);
					System.out.println("长度"+flist.size());
					for(Usr u:flist) {
						System.out.println(u.getUserAccount());
					}
					ObjectUtil.writeObject(s, flist);
				} else if(o instanceof AddFriendMsg) {
					AddFriendMsg msg = (AddFriendMsg)o;
					System.out.println(msg.getFrom().getNickname()+"发送好友请求给"+msg.getTo().getNickname());
					//根据msg中的to查找到toUsr的socket信息，并将msg通过这个socket写到客户端
					Socket toUsr = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
					ObjectUtil.writeObject(toUsr, msg);
				} else if(o instanceof AddFriendResultMsg) {
					AddFriendResultMsg afrm = (AddFriendResultMsg)o;
					Socket tem = ServerBiz.getUserInfo().get(afrm.getTo().getUserAccount());
					if(afrm.isAgree()) {
						uBiz.addFriend(afrm.getFrom(), afrm.getTo());
					}
					if(tem !=null) { //用户在线
						ObjectUtil.writeObject(tem, afrm);
					} else { //用户不在线,保存消息到数据库，离线消息
						
					}
					
				} else if(o instanceof Message) {
					Message msg = (Message)o;
					Socket tem = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
					if(tem !=null) { //用户在线
						ObjectUtil.writeObject(tem, msg);
					} else { //用户不在线,保存消息到数据库，离线消息
						
					}
				} else if(o instanceof SendFileMsg) {
						SendFileMsg msg = (SendFileMsg)o;
						Socket tem = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
						if(tem !=null) { //用户在线
							ObjectUtil.writeObject(tem, msg);
						} else { //用户不在线,保存消息到数据库，离线消息
							
						}
					} else if(o instanceof SendFileResultMsg) {
						SendFileResultMsg msg = (SendFileResultMsg)o;
						Socket tem = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
						if(tem !=null) { //用户在线
							ObjectUtil.writeObject(tem, msg);
						} else { //用户不在线,保存消息到数据库，离线消息
							
						}
					} else if(o instanceof GetPwd) {
						GetPwd gp = (GetPwd)o;
						GetPwdResult gpr = uBiz.getPwd(gp);
						System.out.println(gpr.isSuccess());
						System.out.println(gpr.getPwd());
						ObjectUtil.writeObject(s, gpr);
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
