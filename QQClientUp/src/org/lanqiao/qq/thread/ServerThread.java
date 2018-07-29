package org.lanqiao.qq.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.lanqiao.qq.biz.SysBiz;
import org.lanqiao.qq.biz.UserBiz;
import org.lanqiao.qq.entity.AddFriendMsg;
import org.lanqiao.qq.entity.AddFriendResultMsg;
import org.lanqiao.qq.entity.FrameDistroy;
import org.lanqiao.qq.entity.GetPwdResult;
import org.lanqiao.qq.entity.Message;
import org.lanqiao.qq.entity.SendFileMsg;
import org.lanqiao.qq.entity.SendFileResultMsg;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.ui.FindResultJFrame;
import org.lanqiao.qq.ui.MainFrame;
import org.lanqiao.qq.util.DialogUtil;
import org.lanqiao.qq.util.ObjectUtil;

/**
 * 客户端用来接收服务器端各种消息的线程
 * @author dhc
 *
 */
public class ServerThread extends Thread{
	private Socket s;
	private SysBiz sBiz;
	private Usr u;
	private UserBiz uBiz;
	private MainFrame mf;
	public ServerThread(Socket s,SysBiz sBiz,Usr u,MainFrame mf) {
		this.mf = mf;
		this.u = u;
		uBiz = new UserBiz(s);
		this.sBiz = sBiz;
		this.s= s;
	}
	public void run() {
		try {
			while(true) {
				Object o = ObjectUtil.readObject(s);
				if(o instanceof List) { //如果接受到查询用户结果
					List<Usr> flist = (List<Usr>)o;
					FindResultJFrame frf = new FindResultJFrame(flist,u,s);
					frf.frame.setLocationRelativeTo(null);
					frf.frame.setVisible(true);
				} else if(o instanceof AddFriendMsg) { //如果接受到添加好友信息
					AddFriendMsg msg = (AddFriendMsg)o;
					sBiz.showAddFriendMsg(msg,mf);  
				} else if(o instanceof AddFriendResultMsg) { //如果查询到时添加好友结果信息
					AddFriendResultMsg afrm = (AddFriendResultMsg)o;
					sBiz.showAddFriendResultMsg(afrm, mf);
				} else if(o instanceof Message) {
					Message m = (Message)o;
					uBiz.showMsg(m, mf);
				} else if(o instanceof SendFileMsg) { 
					SendFileMsg msg = (SendFileMsg)o;
					boolean b = DialogUtil.showConfirm(msg.getFrom().getNickname()+"发送文件"+msg.getFilename()+",是否接受");
					if(b) { //接受文件,建立接受文件线程，将接受的消息发送回另一个客户端，
						//启动接受文件线程
						new FileReceiveThread(msg, s).start();
					} else { //不接收文件，将不接受文件消息传回另一个客户端
						SendFileResultMsg sfrm = new SendFileResultMsg();
						sfrm.setAgree(false);
						sfrm.setFrom(msg.getTo());
						sfrm.setTo(msg.getFrom());
						ObjectUtil.writeObject(s, sfrm);
					}
				} else if(o instanceof SendFileResultMsg) { //收到发送文件接受结果消息
					SendFileResultMsg sfrm = (SendFileResultMsg)o;
					if(sfrm.isAgree()) { //接受文件传送,启动发件发送线程
						new FileSendThread(sfrm).start();
					} else { //不接受文件传送
						DialogUtil.showInfo(sfrm.getTo().getUserAccount()+"不接受你的文件传送!");
					}
				} else if(o instanceof FrameDistroy) {
					FrameDistroy fd = (FrameDistroy)o;
					if(fd.isDistroy()) {
						this.mf.exitFrame();
					}
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
