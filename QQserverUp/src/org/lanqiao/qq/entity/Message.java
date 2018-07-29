package org.lanqiao.qq.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * ������Ϣ��
 * 2018.7.9
 * @author dhc
 *
 */
public class Message implements Serializable{
	private Usr from;
	private Usr to;
	private String msg;
	private Date date;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * ��ʽ����Ϣ����Ϣ���еĸ�ʽ
	 */
	@SuppressWarnings("deprecation")
	@Override
	public  String toString() {
		return from.getUserAccount()+"   "+ date.toLocaleString() +"\n" + msg + "\n\n";
	}
}
