package org.lanqiao.qq.ui;
import java.awt.Component;
/**
 * 主界面，显示当前用户账号，和初始化好友列表
 */
import java.awt.EventQueue;
import java.awt.Frame;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.lanqiao.qq.biz.SysBiz;
import org.lanqiao.qq.entity.Message;
import org.lanqiao.qq.entity.Usr;
import org.lanqiao.qq.thread.ServerThread;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.Socket;

public class MainFrame {

	public JFrame frame;
	private Usr user;
	private Socket s;
	JPanel panel;
	private SysBiz sBiz;
	// 保存和所有好友的聊天窗口
	private Map<String, ChatFrame> chatFrame;
	// 系统托盘图标
	private TrayIcon trayIcon;
	// 系统托盘
	private SystemTray systemTray;
	 
	/**
	 * Launch the application.
	 * 界面由登录界面启动，因此不需要主方法
	 */
	/*public static void main(String[] args) {
	}
	 */
	public void exitFrame() {
		System.exit(0);
	}
	/**
	 * 初始化系统托盘方法
	 */
	public void minimizeToTray() {
		try {
			if(SystemTray.isSupported()) { //判断系统是否支持系统托盘
				//初始化实例
				systemTray = SystemTray.getSystemTray();
				//实例图标
				trayIcon = new TrayIcon(ImageIO.read(new File("F:\\蓝桥\\workplace\\QQClientUp\\src\\img\\icon\\1.png")));
				//添加图标
				systemTray.add(trayIcon);
				//监听双击显示
				MouseAdapter iconAdap = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount() == 2) {
							frame.setExtendedState(Frame.NORMAL);
							frame.setVisible(true);
						}
					}
				};
				//给图标加事件
				trayIcon.addMouseListener(iconAdap);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 /**
	  * 通过逻辑层穿过来的Message对象，如果没有聊天窗口创建聊天窗口，并将消息添加到聊天窗口，在判断窗口是否显示，如果没有显示，则让用户头像抖动
	  */
	 public void showMsg(Message msg) {
		 ChatFrame cf = chatFrame.get(msg.getFrom().getUserAccount());
		 if(cf == null) {
			 cf = new ChatFrame(msg.getFrom(),user,s);
			 cf.frame.setLocationRelativeTo(null);
			 cf.appendMsg(msg);
		 } else {
			 cf.appendMsg(msg);
		 }
		 if(!cf.frame.isVisible()) {
			 Component[] com = panel.getComponents();
			 for(Component c:com) {
				 JLabel label = (JLabel)c;
				 if(label.getToolTipText().equals(msg.getFrom().getUserAccount())) {
					 label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/icon/"+msg.getFrom().getImg()+".gif")));
					 break;
				 }
			 }
		 }
	 }
	/**
	 * Create the application.
	 */
	public MainFrame(Usr u,Socket s) {
		chatFrame = new HashMap<String,ChatFrame>();
		this.user = u;
		this.s = s;
		initialize();
		initFriends(user);
		sBiz = new SysBiz(s);
		frame.setTitle(user.getUserAccount());
		new ServerThread(s,sBiz,u,this).start();
		initSysAd();
		minimizeToTray();
	}
	/**
	 * 初始化系统广告
	 */
	public void initSysAd() {
		AdvertisementFrame af = new AdvertisementFrame();
		af.frame.setVisible(true);
	}
	/**
	 * 初始化好友列表
	*/
	public JLabel initFriendLabel(Usr f) {
		JLabel label = new JLabel();
		label.setToolTipText(f.getUserAccount());
		label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/icon/"+f.getImg()+".png")));
		label.setText(f.getNickname());
		//为好友Label天剑双击打开聊天界面窗口
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//判断鼠标点击次数
				if(e.getClickCount() == 2) {
					//判断与当前好友是否已经创建了窗口
					label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/icon/"+f.getImg()+".png")));
					if(chatFrame.get(f.getUserAccount()) == null) {
						ChatFrame newChatFrame = new ChatFrame(f,user,s);
						newChatFrame.frame.setLocationRelativeTo(null);
						newChatFrame.frame.setVisible(true);
						//当没有窗口的时候，将这个窗口添加到map中
						chatFrame.put(f.getUserAccount(), newChatFrame);
					} else {
						chatFrame.get(f.getUserAccount()).frame.setVisible(true);
					}
				}
			}
		});
		return label;
	}
	/**
	 * 当添加新的好友时，将新的好友添加到好友列表
	 * @param u
	 */
	public void addNewFriend(Usr u) {
		panel.add(initFriendLabel(u));
		//刷新一下好友列表
		frame.getContentPane().validate();
	}
	/**
	 * 初始化好友列表
	 * @param u
	 */
	public void initFriends(Usr u) {
		List<Usr> flist = u.getFriends();
		for(Usr f:flist) {
			panel.add(initFriendLabel(f));
		}
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setTitle(user.getUserAccount());
		frame.setBounds(100, 100, 242, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//监听器
		WindowAdapter winAdap = new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				frame.dispose();
			}
		};
		//添加“最小化到系统托盘的监听器”（实际上是释放窗体）
		frame.addWindowListener(winAdap);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/main/1.PNG")));
		label.setBounds(0, 0, 225, 56);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(MainFrame.class.getResource("/img/main/2.png")));
		label_1.setBounds(0, 55, 35, 423);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FindFriendsFrame ffframe = new FindFriendsFrame(s,user);
				ffframe.frame.setLocationRelativeTo(null);
				ffframe.frame.setVisible(true);
			}
		});
		label_2.setIcon(new ImageIcon(MainFrame.class.getResource("/img/main/3.png")));
		label_2.setBounds(0, 477, 225, 47);
		frame.getContentPane().add(label_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 56, 192, 421);
		frame.getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(50, 1, 5, 5));
	}
}
