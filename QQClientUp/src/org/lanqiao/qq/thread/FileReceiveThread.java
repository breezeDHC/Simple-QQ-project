package org.lanqiao.qq.thread;
import java.io.File;
import java.io.FileOutputStream;
/**
 * 2018.7.10
 * 文件接受线程
 * @author dhc
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

import org.lanqiao.qq.entity.SendFileMsg;
import org.lanqiao.qq.entity.SendFileResultMsg;
import org.lanqiao.qq.ui.ProcessJFrame;
import org.lanqiao.qq.util.DialogUtil;
import org.lanqiao.qq.util.ObjectUtil;

public class FileReceiveThread extends Thread{
	private SendFileMsg sfm;
	private Socket s;
	
	public FileReceiveThread(SendFileMsg sfm,Socket s) {
		this.sfm =sfm;
		this.s= s;
		
	}
	public void run() {
		System.out.println(sfm.getFilename());
		File f = DialogUtil.saveFile(sfm.getFilename());
		SendFileResultMsg sfrm = new SendFileResultMsg();
		sfrm.setAgree(true);
		sfrm.setFrom(sfm.getTo());
		sfrm.setTo(sfm.getFrom());
		sfrm.setFilepath(sfm.getFilepath());
		try {
			ServerSocket ss = new ServerSocket(0);
			//设置连接时间，如果超过三分钟没有连接，中断
			ss.setSoTimeout(1000*60*3);
			sfrm.setPort(ss.getLocalPort());
			//注意防火墙的问题
			sfrm.setIp(InetAddress.getLocalHost().getHostAddress().toString());
			//将同意的消息发送给服务端，让服务端转发
			ObjectUtil.writeObject(s, sfrm);
			System.out.println("端口号和ip："+ss.getLocalPort()+"===="+InetAddress.getLocalHost().getHostAddress().toString());
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			FileOutputStream fos = new FileOutputStream(f);
			byte[] bs = new byte[1024*5];
			int len = 0;
			//接受进度条UI
			ProcessJFrame pjf = new ProcessJFrame();
			pjf.setFilesize(sfm.getFilesize());
			pjf.setLabelText("文件接受中......");
			pjf.frame.setLocationRelativeTo(null);
			pjf.frame.setVisible(true);
			while((len=is.read(bs))!=-1) {
				fos.write(bs, 0, len);
				pjf.UpdateWritesize(len);
			}
			DialogUtil.showInfo("文件接受完毕");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
