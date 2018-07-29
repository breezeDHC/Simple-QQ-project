package org.lanqiao.qq.ui;
/**
 * 2018.7.7
 * 查看用户信息时，详细信息界面
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.lanqiao.qq.entity.Usr;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInfoFrame {

	JFrame frame;
	private Usr user;

	/**
	 * Create the application.
	 */
	public UserInfoFrame(Usr u) {
		this.user = u;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 375, 255);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7\uFF1A");
		label.setBounds(29, 23, 44, 15);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u6635\u79F0\uFF1A");
		label_1.setBounds(29, 60, 44, 15);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5E74\u9F84\uFF1A");
		label_2.setBounds(29, 101, 44, 15);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u90AE\u7BB1\uFF1A");
		label_3.setBounds(29, 146, 44, 15);
		frame.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("\u5934\u50CF\uFF1A");
		label_4.setBounds(242, 23, 44, 15);
		frame.getContentPane().add(label_4);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(72, 23, 130, 15);
		lblNewLabel.setText(user.getUserAccount());
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label_5 = new JLabel("");
		label_5.setText(user.getNickname());
		label_5.setBounds(72, 60, 130, 15);
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("");
		label_6.setText(String.valueOf(user.getAge()));
		label_6.setBounds(72, 101, 130, 15);
		frame.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("");
		label_7.setText(user.getEmail());
		label_7.setBounds(72, 146, 130, 15);
		frame.getContentPane().add(label_7);
		
		JLabel label_8 = new JLabel("");
		label_8.setIcon(new ImageIcon(UserInfoFrame.class.getResource("/img/icon/"+user.getImg()+".png")));
		label_8.setBounds(271, 60, 54, 45);
		frame.getContentPane().add(label_8);
		
		JButton button = new JButton("\u5173\u95ED");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		button.setBounds(242, 142, 93, 23);
		frame.getContentPane().add(button);
	}
}
