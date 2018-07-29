package org.lanqiao.qq.ui;
import java.awt.Component;
/**
 * �����棬��ʾ��ǰ�û��˺ţ��ͳ�ʼ�������б�
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
	// ��������к��ѵ����촰��
	private Map<String, ChatFrame> chatFrame;
	// ϵͳ����ͼ��
	private TrayIcon trayIcon;
	// ϵͳ����
	private SystemTray systemTray;
	 
	/**
	 * Launch the application.
	 * �����ɵ�¼������������˲���Ҫ������
	 */
	/*public static void main(String[] args) {
	}
	 */
	public void exitFrame() {
		System.exit(0);
	}
	/**
	 * ��ʼ��ϵͳ���̷���
	 */
	public void minimizeToTray() {
		try {
			if(SystemTray.isSupported()) { //�ж�ϵͳ�Ƿ�֧��ϵͳ����
				//��ʼ��ʵ��
				systemTray = SystemTray.getSystemTray();
				//ʵ��ͼ��
				trayIcon = new TrayIcon(ImageIO.read(new File("F:\\����\\workplace\\QQClientUp\\src\\img\\icon\\1.png")));
				//���ͼ��
				systemTray.add(trayIcon);
				//����˫����ʾ
				MouseAdapter iconAdap = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount() == 2) {
							frame.setExtendedState(Frame.NORMAL);
							frame.setVisible(true);
						}
					}
				};
				//��ͼ����¼�
				trayIcon.addMouseListener(iconAdap);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 /**
	  * ͨ���߼��㴩������Message�������û�����촰�ڴ������촰�ڣ�������Ϣ��ӵ����촰�ڣ����жϴ����Ƿ���ʾ�����û����ʾ�������û�ͷ�񶶶�
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
	 * ��ʼ��ϵͳ���
	 */
	public void initSysAd() {
		AdvertisementFrame af = new AdvertisementFrame();
		af.frame.setVisible(true);
	}
	/**
	 * ��ʼ�������б�
	*/
	public JLabel initFriendLabel(Usr f) {
		JLabel label = new JLabel();
		label.setToolTipText(f.getUserAccount());
		label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/icon/"+f.getImg()+".png")));
		label.setText(f.getNickname());
		//Ϊ����Label�콣˫����������洰��
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//�ж����������
				if(e.getClickCount() == 2) {
					//�ж��뵱ǰ�����Ƿ��Ѿ������˴���
					label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/icon/"+f.getImg()+".png")));
					if(chatFrame.get(f.getUserAccount()) == null) {
						ChatFrame newChatFrame = new ChatFrame(f,user,s);
						newChatFrame.frame.setLocationRelativeTo(null);
						newChatFrame.frame.setVisible(true);
						//��û�д��ڵ�ʱ�򣬽����������ӵ�map��
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
	 * ������µĺ���ʱ�����µĺ�����ӵ������б�
	 * @param u
	 */
	public void addNewFriend(Usr u) {
		panel.add(initFriendLabel(u));
		//ˢ��һ�º����б�
		frame.getContentPane().validate();
	}
	/**
	 * ��ʼ�������б�
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
		//������
		WindowAdapter winAdap = new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				frame.dispose();
			}
		};
		//��ӡ���С����ϵͳ���̵ļ���������ʵ�������ͷŴ��壩
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
