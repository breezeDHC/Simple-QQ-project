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
 * �ͻ����������շ������˸�����Ϣ���߳�
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
				if(o instanceof List) { //������ܵ���ѯ�û����
					List<Usr> flist = (List<Usr>)o;
					FindResultJFrame frf = new FindResultJFrame(flist,u,s);
					frf.frame.setLocationRelativeTo(null);
					frf.frame.setVisible(true);
				} else if(o instanceof AddFriendMsg) { //������ܵ���Ӻ�����Ϣ
					AddFriendMsg msg = (AddFriendMsg)o;
					sBiz.showAddFriendMsg(msg,mf);  
				} else if(o instanceof AddFriendResultMsg) { //�����ѯ��ʱ��Ӻ��ѽ����Ϣ
					AddFriendResultMsg afrm = (AddFriendResultMsg)o;
					sBiz.showAddFriendResultMsg(afrm, mf);
				} else if(o instanceof Message) {
					Message m = (Message)o;
					uBiz.showMsg(m, mf);
				} else if(o instanceof SendFileMsg) { 
					SendFileMsg msg = (SendFileMsg)o;
					boolean b = DialogUtil.showConfirm(msg.getFrom().getNickname()+"�����ļ�"+msg.getFilename()+",�Ƿ����");
					if(b) { //�����ļ�,���������ļ��̣߳������ܵ���Ϣ���ͻ���һ���ͻ��ˣ�
						//���������ļ��߳�
						new FileReceiveThread(msg, s).start();
					} else { //�������ļ������������ļ���Ϣ������һ���ͻ���
						SendFileResultMsg sfrm = new SendFileResultMsg();
						sfrm.setAgree(false);
						sfrm.setFrom(msg.getTo());
						sfrm.setTo(msg.getFrom());
						ObjectUtil.writeObject(s, sfrm);
					}
				} else if(o instanceof SendFileResultMsg) { //�յ������ļ����ܽ����Ϣ
					SendFileResultMsg sfrm = (SendFileResultMsg)o;
					if(sfrm.isAgree()) { //�����ļ�����,�������������߳�
						new FileSendThread(sfrm).start();
					} else { //�������ļ�����
						DialogUtil.showInfo(sfrm.getTo().getUserAccount()+"����������ļ�����!");
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
