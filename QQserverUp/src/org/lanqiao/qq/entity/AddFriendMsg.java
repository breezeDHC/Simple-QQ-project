package org.lanqiao.qq.entity;

import java.io.Serializable;

/**
 * 添加好友时的消息类，包括添加好友的用户和添加的人
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
