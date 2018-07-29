package org.lanqiao.qq.ui;
/**
 * 2018.7.7
 * 查找用户结果界面
 */
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.lanqiao.qq.biz.UserBiz;
import org.lanqiao.qq.entity.AddFriendMsg;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.util.DialogUtil;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

public class FindResultJFrame {

	public JFrame frame;
	private JButton button;
	private JButton button_1;
	private JScrollPane scrollPane;
	private JTable table;
	private List<Usr> flist;
	private JButton button_2;
	
	private Usr user;
	private Socket s;
	private UserBiz uBiz;
	/**
	 * Create the application.
	 */
	public FindResultJFrame(List<Usr> list,Usr u, Socket s) {
		this.s = s;
		this.user = u;
		this.flist = list;
		this.uBiz = new UserBiz(s);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		button = new JButton("\u67E5\u770B\u4FE1\u606F");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//如果选中某行
				if(table.getSelectedRow() != -1) { 
					Usr u = flist.get(table.getSelectedRow());
					UserInfoFrame uif = new UserInfoFrame(u);
					uif.frame.setLocationRelativeTo(null);
					uif.frame.setVisible(true);
				} else {
					DialogUtil.showAlarm("请选择一个用户！！！");
				}
			}
		});
		button.setBounds(59, 208, 93, 23);
		frame.getContentPane().add(button);

		button_1 = new JButton("\u6DFB\u52A0\u597D\u53CB");
		//添加添加好友的鼠标点击事件
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//如果选中某行
				if(table.getSelectedRow() != -1) { 
					Usr t = flist.get(table.getSelectedRow());
					AddFriendMsg msg = new AddFriendMsg();
					msg.setFrom(user);
					msg.setTo(t);
					try {
						uBiz.addFriend(msg, s);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					DialogUtil.showAlarm("请选择一个用户！！！");
				}
			}
		});
		button_1.setBounds(176, 208, 93, 23);
		frame.getContentPane().add(button_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 26, 373, 160);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(getTwoArray(), new String[] { "账号", "昵称" }));

		scrollPane.setViewportView(table);

		button_2 = new JButton("\u5173\u95ED\u9762\u677F");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		button_2.setBounds(299, 208, 93, 23);
		frame.getContentPane().add(button_2);
	}

	private String[][] getTwoArray() {
		String[][] list = new String[this.flist.size()][2];
		// 如果返回的有用户
		if (flist != null) {
			for (int i = 0; i < flist.size(); i++) {
				Usr u = flist.get(i);
				list[i][0] = u.getUserAccount();
				list[i][1] = u.getNickname();
			}
		}
		return list;
	}
}
