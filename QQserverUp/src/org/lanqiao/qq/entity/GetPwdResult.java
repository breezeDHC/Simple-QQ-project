package org.lanqiao.qq.entity;

import java.io.Serializable;

public class GetPwdResult implements Serializable{
	private boolean isSuccess;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	private String pwd;
}
