package org.lanqiao.qq.ui;
/**
 * 2018.7.6
 * 注册界面
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.lanqiao.qq.biz.SysBiz;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.entity.registerResult;
import org.lanqiao.qq.util.DialogUtil;

import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterFrame {

	public  JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox comboBox;
	private JLabel label_5;
	private LoginJFrame loginFrame;
	private SysBiz sBiz;
	/**
	 * Launch the application.
	 */
	//客户端只需要一个main方法，存于登录界面中，
	
	/**
	 * Create the application.
	 */
	public RegisterFrame(LoginJFrame loginFrame,Socket s) {
		this.loginFrame = loginFrame;
		sBiz = new SysBiz(s);
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 455, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u6635\u79F0\uFF1A");
		label.setBounds(31, 36, 41, 22);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(31, 83, 41, 15);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5E74\u9F84\uFF1A");
		label_2.setBounds(31, 125, 41, 15);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u90AE\u7BB1\uFF1A");
		label_3.setBounds(31, 166, 41, 15);
		frame.getContentPane().add(label_3);
		
		textField = new JTextField();
		textField.setBounds(82, 37, 163, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(82, 79, 163, 22);
		frame.getContentPane().add(passwordField);
		
		textField_1 = new JTextField();
		textField_1.setBounds(82, 122, 163, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(82, 163, 163, 21);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_4 = new JLabel("\u5934\u50CF\uFF1A");
		label_4.setBounds(292, 40, 54, 15);
		frame.getContentPane().add(label_4);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				changeHeadPicture(e);
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u7B11\u8138", "\u82B1\u6735", "\u7B14\u58A8", "\u8138\u8C31"}));
		comboBox.setSelectedIndex(0);
		comboBox.setToolTipText("");
		comboBox.setBounds(343, 37, 64, 21);
		frame.getContentPane().add(comboBox);
		
		label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/icon/1.png")));
		label_5.setBounds(343, 79, 50, 50);
		frame.getContentPane().add(label_5);
		
		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register(e);
			}
		});
		button.setBounds(82, 214, 69, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("\u5173\u95ED");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeRegisterPanel(e);
			}
		});
		button_1.setBounds(176, 214, 69, 23);
		frame.getContentPane().add(button_1);

	}
	/**
	 * 换头像事件
	 * @param e
	 */
	public void changeHeadPicture(ItemEvent e) {
		int index = comboBox.getSelectedIndex()+1;
		label_5.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/icon/"+index+".png")));
	}
	/**
	 * 关闭注册面板，回到登录界面
	 * @param e
	 */
	public void closeRegisterPanel(ActionEvent e) {
		this.frame.dispose();
		loginFrame.frame.setVisible(true);
	}
	/**
	 * 注册事件
	 */
	public void register(ActionEvent e) {
		String nickname = textField.getText().trim();
		String passWord = new String(passwordField.getPassword());
		int age = Integer.parseInt(textField_1.getText());
		String email = textField_2.getText().trim();
		String headPicture = String.valueOf(comboBox.getSelectedIndex()+1);
		Usr u = new Usr();
		u.setAge(age);
		u.setEmail(email);
		u.setImg(headPicture);
		u.setNickname(nickname);
		u.setPassWord(passWord);
		registerResult rs = new registerResult();
		try {
			rs = sBiz.register(u);
			if(rs.isSuccess()) {
				this.frame.dispose();
				this.loginFrame.frame.setVisible(true);
				DialogUtil.showInfo("你的账号是:"+rs.getResultMessage());
			} else {
				if(!rs.isNicknameFlag()) {
					DialogUtil.showAlarm("你的用户名昵称不对，请输入2-12个字符！");
				} else {
					if(!rs.isPwdFlag()) {
						DialogUtil.showAlarm("请输入6-12位的密码！");
					} else {
						if(!rs.isAgeFlag()) {
							DialogUtil.showAlarm("请输入0-150的年龄数字！");
						} else {
							if(!rs.isEmailFlag1()) {
								DialogUtil.showAlarm("邮箱格式不对！");
							} else {
								if(!rs.isEmailFlag2()) {
									DialogUtil.showAlarm("有相同邮箱，请更换邮箱！");
								}
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			DialogUtil.showAlarm("服务器异常，注册失败！");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			DialogUtil.showAlarm("服务器异常，注册失败！");
		}
	}
}
