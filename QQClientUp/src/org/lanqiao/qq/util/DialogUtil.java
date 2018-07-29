package org.lanqiao.qq.util;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
/**
 * 2018.7.5
 * 弹窗工具类
 */
import javax.swing.JOptionPane;

public class DialogUtil {
	/**
	 * 显示警告弹窗
	 * @param str
	 */
	public static void showAlarm(String str) {
		JOptionPane.showMessageDialog(null, str, "系统警告",JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * 显示消息弹窗
	 * @param str
	 */
	public static void showInfo(String str) {
		JOptionPane.showMessageDialog(null, str, "系统消息",JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * 显示确认弹窗
	 * @param str
	 * @return
	 */
	public static boolean showConfirm(String str) {
		int a = JOptionPane.showConfirmDialog(null, str, "确认消息" + "", JOptionPane.YES_NO_OPTION);
		if(a == JOptionPane.OK_OPTION) {
			return true;
		} else {
			return false;
		}
		
	}
	/**
	 * 选择发送文件弹窗
	 * @return
	 */
	public static File sendFile() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		return file;
	}
	/**
	 * 保存接受文件
	 * @param filename
	 * @return
	 */
	public static File saveFile(String filename) {
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File(filename));
		fc.showSaveDialog(null);
		return fc.getSelectedFile();
	}
	
}
