package org.lanqiao.qq.entity;

import java.io.Serializable;

/**
 * 发送文件结果消息类
 * @author dhc
 *
 */
public class SendFileResultMsg implements Serializable {
	private Usr from;
	private Usr to;
	private boolean agree;
	private String ip;
	private int port;
	private String filepath;
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Usr getFrom() {
		return from;
	}
	public void setFrom(Usr from) {
		this.from = from;
	}
	public Usr getTo() {
		return to;
	}
	public void setTo(Usr to) {
		this.to = to;
	}
	public boolean isAgree() {
		return agree;
	}
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
