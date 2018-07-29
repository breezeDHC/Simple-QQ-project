package org.lanqiao.qq.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ProcessJFrame {

	public JFrame frame;
	JLabel lblNewLabel;
	JProgressBar progressBar;
	private long filesize;
	private long writesize;

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public void setWritesize(int writesize) {
		this.writesize = writesize;
	}
	
	public void setLabelText(String text) {
		lblNewLabel.setText(text);
	}
	public void UpdateWritesize(long size) {
		writesize += size;
		long value = (100*writesize/filesize);
		progressBar.setValue((int) value);
		progressBar.setString(value+"%");
	}
	/**
	 * Create the application.
	 */
	public ProcessJFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(39, 38, 280, 28);
		frame.getContentPane().add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(39, 79, 351, 34);
		frame.getContentPane().add(progressBar);
	}
}
