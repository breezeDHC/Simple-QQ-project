package org.lanqiao.qq.entity;

import java.io.Serializable;

public class AddFriendResultMsg implements Serializable{
	private Usr from;
	private Usr to;
	private boolean agree;
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
}
