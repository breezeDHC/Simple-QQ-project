package org.lanqiao.qq.entity;

import java.io.Serializable;
import java.net.Socket;

public class GetPwd implements Serializable{
	private String account;
	private String email;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
