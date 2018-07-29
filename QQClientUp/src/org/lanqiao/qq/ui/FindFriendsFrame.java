package org.lanqiao.qq.ui;

/**
 * 2018.7.7
 * 查找好友界面
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.lanqiao.qq.biz.SysBiz;
import org.lanqiao.qq.entity.FindFriends;
import org.lanqiao.qq.entity.Usr;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class FindFriendsFrame {

	JFrame frame;
	private JTextField textField;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	Socket s;
	SysBiz sBiz;
	private Usr user;
	// 单选按钮需要在同一个组中，因此需要创建一个组
	private ButtonGroup jbg;
	private JTextField textField_1;
	/**
	 * Launch the application. 由主界面的查找打开这个界面，因此不需要主函数
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { FindFriendsFrame window = new
	 * FindFriendsFrame(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 */
	public FindFriendsFrame(Socket s,Usr u) {
		this.s = s;
		this.user = u;
		sBiz = new SysBiz(s);
		jbg = new ButtonGroup();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 395, 282);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		radioButton = new JRadioButton("\u7CBE\u786E\u67E5\u627E");
		radioButton.setBackground(Color.CYAN);
		radioButton.setBounds(23, 31, 79, 23);
		frame.getContentPane().add(radioButton);

		textField = new JTextField();
		textField.setBounds(121, 32, 232, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		radioButton_2 = new JRadioButton("\u6A21\u7CCA\u67E5\u627E");
		radioButton_2.setBackground(Color.CYAN);
		radioButton_2.setBounds(23, 85, 79, 23);
		frame.getContentPane().add(radioButton_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(121, 86, 232, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		// 将单选按钮添加到同一个组中
		jbg.add(radioButton);
		jbg.add(radioButton_2);
		
				radioButton_1 = new JRadioButton("\u67E5\u627E\u5168\u90E8");
				radioButton_1.setSelected(true);
				radioButton_1.setBackground(Color.CYAN);
				radioButton_1.setBounds(23, 137, 79, 23);
				frame.getContentPane().add(radioButton_1);
				jbg.add(radioButton_1);
				
						JButton button = new JButton("\u67E5\u627E");
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								new Thread() {
									public void run() {
										FindFriends ff = new FindFriends();
										ff.setU(user);
										// 判断查找类型
										if (radioButton.isSelected()) {
											ff.setType(FindFriends.ONE);
											ff.setAccount(textField.getText().trim());
										} else if(radioButton_2.isSelected()){
											ff.setType(FindFriends.SOME);
											ff.setAccount(textField_1.getText().trim());
										} else {
											ff.setType(FindFriends.ALL);
										}
										try {
											sBiz.findFriends(ff);
										} catch (ClassNotFoundException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}.start();
							}
						});
						button.setBounds(55, 194, 93, 23);
						frame.getContentPane().add(button);
						
								JButton button_1 = new JButton("\u5173\u95ED");
								button_1.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										// 直接销毁窗口
										frame.dispose();
									}
								});
								button_1.setBounds(184, 194, 93, 23);
								frame.getContentPane().add(button_1);
	}
}
