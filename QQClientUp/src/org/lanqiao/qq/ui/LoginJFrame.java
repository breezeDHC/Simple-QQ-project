package org.lanqiao.qq.ui;
/**
 * 2018.7.5
 * 登录UI界面
 */
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import org.lanqiao.qq.biz.SysBiz;
import org.lanqiao.qq.biz.UserBiz;
import org.lanqiao.qq.entity.GetPwdResult;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.util.DialogUtil;
import org.lanqiao.qq.util.ObjectUtil;
import org.lanqiao.qq.util.PropertiesUtil;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.SynchronousQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPasswordField;

public class LoginJFrame {

	public JFrame frame;
	private JTextField textField;
	private static Socket s = null;
	private static SysBiz sBiz = null;
	private JPasswordField passwordField;
	private static UserBiz uBiz;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginJFrame window = new LoginJFrame();
					window.frame.setLocationRelativeTo(null);
					
					window.frame.setVisible(true);
					//在UI界面显示时连接服务器，连接失败时弹出弹窗，报连接失败
					try {
						s = new Socket(PropertiesUtil.readPro("ip"),Integer.parseInt(PropertiesUtil.readPro("port")));
						sBiz = new SysBiz(s);
						uBiz = new UserBiz(s);
					} catch (IOException e) {
						DialogUtil.showAlarm("连接服务器失败！");
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginJFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 255, 255));
		frame.setBounds(100, 100, 331, 298);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/qq.PNG")));
		label.setBounds(0, 0, 322, 45);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/id.png")));
		label_1.setBounds(29, 80, 38, 28);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/pw.png")));
		label_2.setBounds(29, 118, 50, 28);
		frame.getContentPane().add(label_2);
		
		textField = new JTextField();
		textField.setBounds(77, 80, 136, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label_3 = new JLabel("");
		label_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				register(e);
			}
		});
		
		label_3.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/reg.png")));
		label_3.setBounds(223, 86, 65, 22);
		frame.getContentPane().add(label_3);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getPwd(e);
			}
		});
		lblNewLabel.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/qh.png")));
		lblNewLabel.setBounds(223, 125, 65, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/2-2.png")));
		lblNewLabel_1.setBounds(49, 173, 223, 17);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(e);
			}
		});
		
		button.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/login.png")));
		
		button.setBounds(49, 212, 72, 22);
		frame.getContentPane().add(button);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(LoginJFrame.class.getResource("/img/login/sz.png")));
		btnNewButton.setBounds(188, 212, 72, 22);
		frame.getContentPane().add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(77, 118, 136, 28);
		frame.getContentPane().add(passwordField);
	}
	/**
	 * 注册新用户
	 * @param e
	 */
	public void register(MouseEvent e) {
		RegisterFrame win = new RegisterFrame(this,s);
		win.frame.setLocationRelativeTo(null);
		win.frame.setVisible(true);
	}
	/**
	 * 点击登录按钮时处理，
	 * @param e
	 */
	private void login(ActionEvent e) {
		new Thread() {
			public void run() {
				String name = textField.getText().trim();
				String passWord = String.valueOf(passwordField.getPassword());
				//此处传递对个值，使用对象流，即通过socket传一个对象
				//使用对象序列化时：注意此处的usr需要和服务器的Usr类完全相同的包和类名，并且都需要实现序列化接口
				Usr u = new Usr();
				u.setUserAccount(name);
				u.setPassWord(passWord);
				try {
					Usr u1 = sBiz.login(u);
					if(u1 == null) {
						DialogUtil.showAlarm("登录失败！用户名或密码错误");
					} else {
						//此处应弹出主界面
						System.out.println(u1.getAge());
						//DialogUtil.showAlarm("登录成功！");
						frame.dispose();
						MainFrame window = new MainFrame(u1,s);
						window.frame.setLocationRelativeTo(null);
						window.frame.setVisible(true);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					DialogUtil.showAlarm("服务器异常");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					DialogUtil.showAlarm("服务器异常");
					e.printStackTrace();
				}
				
			}
		}.start();
	}
	/**
	 * 取回密码，通过账号和邮箱
	 * @param e
	 */
	public void getPwd(MouseEvent e) {
		GetPwdFrame gpf = new GetPwdFrame(uBiz);
		gpf.frame.setLocationRelativeTo(null);
		gpf.frame.setVisible(true);
		new Thread() {
			public void run() {
				try {
					while(true) {
						Object o = ObjectUtil.readObject(s);
						if (o instanceof GetPwdResult) {
							GetPwdResult gpr = (GetPwdResult) o;
							System.out.println(gpr.isSuccess());
							System.out.println(gpr.getPwd());
							if (!gpr.isSuccess()) {
								DialogUtil.showAlarm("账号或者邮箱错误！！！");
							} else {
								DialogUtil.showInfo("你的密码为" + gpr.getPwd());
							}
						}
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}.start();
		
	}
}
