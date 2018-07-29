package org.lanqiao.qq.ui;
/**
 * 2018.7.17
 * 系统广告
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class AdvertisementFrame {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdvertisementFrame window = new AdvertisementFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdvertisementFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setLocation(1480, 790);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u7CFB\u7EDF\u5E7F\u544A");
		label.setFont(new Font("宋体", Font.PLAIN, 41));
		label.setBounds(101, 22, 164, 45);
		frame.getContentPane().add(label);
		
		JLabel lblvssxhp = new JLabel("\u770B\u7535\u5F71+V\u4FE1\uFF1Assxhp123");
		lblvssxhp.setFont(new Font("宋体", Font.PLAIN, 18));
		lblvssxhp.setBounds(101, 108, 245, 15);
		frame.getContentPane().add(lblvssxhp);
		
		JLabel lblzjzgdfz = new JLabel("\u7EC8\u7ED3\u8005\u8F85\u52A9\u52A0\u5A01\u4FE1\uFF1Azjzgdfz11");
		lblzjzgdfz.setFont(new Font("宋体", Font.PLAIN, 18));
		lblzjzgdfz.setBounds(101, 151, 278, 15);
		frame.getContentPane().add(lblzjzgdfz);
	}
}
