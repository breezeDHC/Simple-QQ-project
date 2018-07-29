package org.lanqiao.qq.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import org.lanqiao.qq.biz.UserBiz;
import org.lanqiao.qq.entity.Message;
import org.lanqiao.qq.entity.SendFileMsg;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.util.DialogUtil;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class ChatFrame {

	public JFrame frame;
	private Usr f;
	private Usr u;
	private JTextArea textArea_1;
	private Socket s;
	private UserBiz uBiz;
	public JTextArea textArea;
	private JButton button_3;
	private JButton button_4;

	/**
	 * Create the application.
	 */
	public ChatFrame(Usr f, Usr u, Socket s) {
		this.f = f;
		this.u = u;
		this.s = s;
		uBiz = new UserBiz(s);
		initialize();
		this.initChatFrame();
		frame.setTitle(f.getUserAccount());
	}

	public void appendMsg(Message msg) {
		textArea.append(msg.toString());
	}
	//初始化聊天窗口中的聊天记录
	public void initChatFrame() {
		String filepath = "F:\\蓝桥\\workplace\\QQClientUp\\ChatInfo\\" + u.getUserAccount()+"\\"+f.getUserAccount()+".txt";
		File f = new File(filepath);
		if(f.isFile() && f.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(new File(filepath)));
				String str = null;
					while((str = reader.readLine()) != null) {
						textArea.append(str+"\n");
					}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
			
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 425, 533);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ChatFrame.class.getResource("/img/chat/female.png")));
		label.setBounds(270, 10, 130, 199);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(ChatFrame.class.getResource("/img/chat/male.png")));
		label_1.setBounds(270, 253, 130, 199);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(ChatFrame.class.getResource("/img/chat/1.png")));
		label_2.setBounds(10, 217, 390, 26);
		frame.getContentPane().add(label_2);

		JButton button = new JButton("");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Message m = new Message();
				m.setFrom(u);
				m.setTo(f);
				m.setMsg(textArea_1.getText());
				m.setDate(new Date());
				try {
					uBiz.sendMsg(m);
					textArea.append(m.toString());
					textArea_1.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setIcon(new ImageIcon(ChatFrame.class.getResource("/img/chat/send1.png")));
		button.setBounds(97, 465, 70, 23);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
			}
		});
		button_1.setIcon(new ImageIcon(ChatFrame.class.getResource("/img/chat/close1.png")));
		button_1.setBounds(177, 462, 73, 26);
		frame.getContentPane().add(button_1);

		JButton button_2 = new JButton("");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File file = DialogUtil.sendFile();
				SendFileMsg msg = new SendFileMsg();
				msg.setFilename(file.getName());
				msg.setFilesize(file.length());
				msg.setFrom(u);
				msg.setTo(f);
				msg.setFilepath(file.getPath());
				try {
					uBiz.sendFileMsg(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_2.setIcon(new ImageIcon(ChatFrame.class.getResource("/img/chat/folder.png")));
		button_2.setBounds(20, 465, 38, 23);
		frame.getContentPane().add(button_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 207, 240, -195);
		frame.getContentPane().add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 250, 170);
		frame.getContentPane().add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 253, 250, 199);
		frame.getContentPane().add(scrollPane_2);

		textArea_1 = new JTextArea();
		scrollPane_2.setViewportView(textArea_1);

		button_3 = new JButton("\u6E05\u9664\u804A\u5929\u8BB0\u5F55");
		// 清除聊天记录事件
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearChatInfo(e);
			}
		});
		button_3.setBounds(10, 190, 118, 23);
		frame.getContentPane().add(button_3);

		button_4 = new JButton("\u4FDD\u5B58\u804A\u5929\u8BB0\u5F55");
		// 保存聊天记录事件
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveChatInfo(e);
			}
		});
		button_4.setBounds(142, 190, 118, 23);
		frame.getContentPane().add(button_4);
	}

	/**
	 * 用来保存用户和某个好友的聊天记录
	 * 
	 * @param e
	 */
	private void saveChatInfo(MouseEvent e) {
		String chatString = textArea.getText().trim();
		String filepath = "F:\\蓝桥\\workplace\\QQClientUp\\ChatInfo\\" + u.getUserAccount();
		File ff = new File(filepath);
		if (!ff.exists()) {
			ff.mkdir();
		}
		String filepath1 = filepath + "\\" + f.getUserAccount() + ".txt";
		File ff1 = new File(filepath1);
		if (!ff1.exists() || ff1.exists()) {
			BufferedReader reader = null;
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(new File(filepath1)));
				reader = new BufferedReader(new StringReader(chatString));
				String s = null;
				while ((s = reader.readLine()) != null) {
					writer.write(s);
					writer.newLine();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					writer.close();
					reader.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		}
	}
	private void clearChatInfo(MouseEvent e) {
		String filepath = "F:\\蓝桥\\workplace\\QQClientUp\\ChatInfo\\" + u.getUserAccount() + "\\"
				+ f.getUserAccount() + ".txt";
		File f = new File(filepath);
		if (f.isFile() && f.exists()) {
			f.delete();
		}
		textArea.setText("");
	}
}
