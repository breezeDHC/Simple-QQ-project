package org.lanqiao.qq.entity;

import java.io.Serializable;

/**
 * 2018.7.10
 * 发送文件的消息类
 * @author dhc
 *
 */
public class SendFileMsg implements Serializable{
	private Usr from;
	private Usr to;
	private String filename;
	private long filesize;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
}
