package org.lanqiao.qq.entity;

import java.io.Serializable;

/**
 * ��Ӻ���ʱ����Ϣ�࣬������Ӻ��ѵ��û�����ӵ���
 * @author dhc
 *
 */
public class AddFriendMsg implements Serializable{
	private Usr from;
	private Usr to;
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
}
