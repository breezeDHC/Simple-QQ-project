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
 * 功能：用来处理服务器的业务逻辑
 * @author dhc
 *
 */
public class ServerBiz {
	
	ServerSocket ss;
	int port;
	//用来保存用户的Socket信息，方便根据用户的账号来找到Socket，相当于保存了所有的在线用户，因为在其他地方用，因此设置为静态属性
	private static Map<String,Socket> userInfo;
	
	public static Map<String, Socket> getUserInfo() {
		return userInfo;
	}
	/**
	 * 启动服务器
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
			System.out.println("客户端连接上了");
			new clientThread(s).start();
		}
	}
	/**
	 * 关闭服务器
	 * @throws IOException 
	 */
	public void stopServer() throws IOException {
		ss.close();
	}
}
