package org.lanqiao.qq.util;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
/**
 * 2018.7.5
 * ����������
 */
import javax.swing.JOptionPane;

public class DialogUtil {
	/**
	 * ��ʾ���浯��
	 * @param str
	 */
	public static void showAlarm(String str) {
		JOptionPane.showMessageDialog(null, str, "ϵͳ����",JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * ��ʾ��Ϣ����
	 * @param str
	 */
	public static void showInfo(String str) {
		JOptionPane.showMessageDialog(null, str, "ϵͳ��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * ��ʾȷ�ϵ���
	 * @param str
	 * @return
	 */
	public static boolean showConfirm(String str) {
		int a = JOptionPane.showConfirmDialog(null, str, "ȷ����Ϣ" + "", JOptionPane.YES_NO_OPTION);
		if(a == JOptionPane.OK_OPTION) {
			return true;
		} else {
			return false;
		}
		
	}
	/**
	 * ѡ�����ļ�����
	 * @return
	 */
	public static File sendFile() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		return file;
	}
	/**
	 * ��������ļ�
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
