package org.lanqiao.qq.frame;
/**
 * ���ܣ���������UI���棬����������ť����Ϊ����ӵ���¼�
 * ʱ�䣺2018.7.4
 */
import java.awt.EventQueue;

import javax.swing.JFrame;

import org.lanqiao.qq.biz.ServerBiz;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class QQServerUI {

	private JFrame frame;
	private JButton button;
	private JButton button_1;
	
	private ServerBiz sbiz;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QQServerUI window = new QQServerUI();
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
	public QQServerUI() {
		sbiz = new ServerBiz();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 619, 237);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		button = new JButton("\u542F\u52A8\u670D\u52A1");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start(e);
			}
		});
		button.setFont(new Font("����", Font.PLAIN, 40));
		button.setBounds(50, 53, 221, 82);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("\u5173\u95ED\u670D\u52A1");
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop(e);
			}
		});
		button_1.setFont(new Font("����", Font.PLAIN, 40));
		button_1.setBounds(321, 53, 221, 82);
		frame.getContentPane().add(button_1);
	}
	
	/**
	 * ������������ť����¼�������ServerSocket����
	 * @param evt
	 * @throws IOException 
	 */
	private void start(ActionEvent evt){
		new Thread() {
			public void run() {
				button.setEnabled(false);
				button_1.setEnabled(true);
				try {
					sbiz.startServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	/**
	 * ������ֹͣ��ť����¼����ر�ServerSocket����
	 * @param evt
	 * @throws IOException 
	 */
	private void stop(ActionEvent evt){
		new Thread() {
			public void run() {
				button.setEnabled(true);
				button_1.setEnabled(false);
				try {
					sbiz.stopServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}
}
