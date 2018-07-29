package org.lanqiao.qq.biz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.lanqiao.qq.Thread.clientThread;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.util.ObjectUtil;
import org.lanqiao.qq.util.PropertiesUtil;

/**
 * 2018.7.4
 * ���ܣ����������������ҵ���߼�
 * @author dhc
 *
 */
public class ServerBiz {
	
	ServerSocket ss;
	int port;
	//���������û���Socket��Ϣ����������û����˺����ҵ�Socket���൱�ڱ��������е������û�����Ϊ�������ط��ã��������Ϊ��̬����
	private static Map<String,Socket> userInfo;
	
	public static Map<String, Socket> getUserInfo() {
		return userInfo;
	}
	/**
	 * ����������
	 * @throws IOException 
	 */
	public void startServer() throws IOException {
		userInfo = new HashMap<String, Socket>();
		String sport = PropertiesUtil.readPro("PORT");
		if(sport != null) {
			port = Integer.parseInt(sport);
		} else {
			port = 6000;
		}
		ss = new ServerSocket(port);
		while(true) {
			Socket s = ss.accept();
			System.out.println("�ͻ�����������");
			new clientThread(s).start();
		}
	}
	/**
	 * �رշ�����
	 * @throws IOException 
	 */
	public void stopServer() throws IOException {
		ss.close();
	}
}
