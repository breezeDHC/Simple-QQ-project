package org.lanqiao.qq.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.lanqiao.qq.entity.SendFileResultMsg;
import org.lanqiao.qq.ui.ProcessJFrame;
import org.lanqiao.qq.util.DialogUtil;

/**
 * 2018.7.10
 * 文件发送线程
 * @author dhc
 *
 */
public class FileSendThread extends Thread {
	private SendFileResultMsg sfrm;
	public FileSendThread(SendFileResultMsg sfrm){
		this.sfrm = sfrm;
	}
	public void run() {
		Socket s = null;
		FileInputStream fis = null;
		try {
			File file = new File(sfrm.getFilepath());
			s = new Socket(sfrm.getIp(), sfrm.getPort());
			fis = new FileInputStream(file);
			OutputStream os = s.getOutputStream();
			byte bs[] = new byte[1024*5];
			int len = 0;
			//发送进度UI
			ProcessJFrame pfg = new ProcessJFrame();
			pfg.setFilesize(file.length());
			pfg.setLabelText("文件发送中......");
			pfg.frame.setLocationRelativeTo(null);
			pfg.frame.setVisible(true);
			while((len=fis.read(bs))!=-1) {
				os.write(bs,0,len);
				pfg.UpdateWritesize(len);
			}
			DialogUtil.showInfo("文件发送完毕");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
