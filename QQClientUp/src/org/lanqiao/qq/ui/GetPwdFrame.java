package org.lanqiao.qq.ui;
/**
 * 2018.7.17
 * 找回密码界面
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.lanqiao.qq.biz.UserBiz;
import org.lanqiao.qq.entity.GetPwd;
import org.lanqiao.qq.entity.Usr;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

public class GetPwdFrame {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	
	private UserBiz uBiz;
	/**
	 * Launch the application.
	 * 在登录界面打开，不需要主函数
	 */

	/**
	 * Create the application.
	 */
	public GetPwdFrame(UserBiz uBiz) {
		this.uBiz = uBiz;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 313, 220);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7\uFF1A");
		label.setBounds(29, 34, 36, 15);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u90AE\u7BB1\uFF1A");
		label_1.setBounds(29, 77, 36, 15);
		frame.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setBounds(75, 32, 188, 18);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(75, 74, 188, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton button = new JButton("\u627E\u56DE\u5BC6\u7801");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GetPwd gp = new GetPwd();
				gp.setAccount(textField.getText().trim());
				gp.setEmail(textField_1.getText().trim());
				try {
					uBiz.getPwd(gp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(111, 130, 93, 23);
		frame.getContentPane().add(button);
	}
}
