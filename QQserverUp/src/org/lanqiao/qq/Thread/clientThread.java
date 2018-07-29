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
 * 2018.7.5 ��ÿһ���ͻ�������һ���̣߳��ֱ�����Ե�����
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
	 * ��ע��if-else if�е��߼�Ӧ�÷����߼��㣬�˴����ú������ɣ���ͻ��˵��߳�����
	 */
	public void run() {
	
			try {
				while (true) {
				Object o = ObjectUtil.readObject(s);
				if (o instanceof Usr) {
					Usr u = (Usr) o;
					// �ж��ǵ�¼����ע��
					if (u.getUserAccount() != null) {
						// ��¼��֤
						u = uBiz.isLogin(u);
						if(u != null) { //�����¼�ɹ����ͽ��û����˺ź�socket��ӵ�map��
							//�жϵ�ǰ�û��Ƿ��Ѿ���¼
							Socket st = ServerBiz.getUserInfo().get(u.getUserAccount());
							if(st != null) { //���û��Ѿ���¼ʱ
								FrameDistroy fd = new FrameDistroy();
								fd.setDistroy(true);
								ServerBiz.getUserInfo().replace(u.getUserAccount(), st, s);  //�滻socket
								//��֮ǰ��¼���ڷ��ʹ���������Ϣ
								ObjectUtil.writeObject(st, fd);
							} else {
								ServerBiz.getUserInfo().put(u.getUserAccount(), s);
							}
							// ������֤������ͻ���
							ObjectUtil.writeObject(s, u);
						}
						
					} else {
						ObjectUtil.writeObject(s, uBiz.register(u));
					}
				} else if (o instanceof FindFriends) { // ����ǲ�����,���в���
					FindFriends ff = (FindFriends) o;
					System.out.println(ff.getAccount()+"--"+ff.getType()+"--"+ff.getU().getUserAccount());
					List<Usr> flist = new ArrayList<Usr>();
					flist = uBiz.findFriends(ff);
					System.out.println("����"+flist.size());
					for(Usr u:flist) {
						System.out.println(u.getUserAccount());
					}
					ObjectUtil.writeObject(s, flist);
				} else if(o instanceof AddFriendMsg) {
					AddFriendMsg msg = (AddFriendMsg)o;
					System.out.println(msg.getFrom().getNickname()+"���ͺ��������"+msg.getTo().getNickname());
					//����msg�е�to���ҵ�toUsr��socket��Ϣ������msgͨ�����socketд���ͻ���
					Socket toUsr = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
					ObjectUtil.writeObject(toUsr, msg);
				} else if(o instanceof AddFriendResultMsg) {
					AddFriendResultMsg afrm = (AddFriendResultMsg)o;
					Socket tem = ServerBiz.getUserInfo().get(afrm.getTo().getUserAccount());
					if(afrm.isAgree()) {
						uBiz.addFriend(afrm.getFrom(), afrm.getTo());
					}
					if(tem !=null) { //�û�����
						ObjectUtil.writeObject(tem, afrm);
					} else { //�û�������,������Ϣ�����ݿ⣬������Ϣ
						
					}
					
				} else if(o instanceof Message) {
					Message msg = (Message)o;
					Socket tem = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
					if(tem !=null) { //�û�����
						ObjectUtil.writeObject(tem, msg);
					} else { //�û�������,������Ϣ�����ݿ⣬������Ϣ
						
					}
				} else if(o instanceof SendFileMsg) {
						SendFileMsg msg = (SendFileMsg)o;
						Socket tem = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
						if(tem !=null) { //�û�����
							ObjectUtil.writeObject(tem, msg);
						} else { //�û�������,������Ϣ�����ݿ⣬������Ϣ
							
						}
					} else if(o instanceof SendFileResultMsg) {
						SendFileResultMsg msg = (SendFileResultMsg)o;
						Socket tem = ServerBiz.getUserInfo().get(msg.getTo().getUserAccount());
						if(tem !=null) { //�û�����
							ObjectUtil.writeObject(tem, msg);
						} else { //�û�������,������Ϣ�����ݿ⣬������Ϣ
							
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
