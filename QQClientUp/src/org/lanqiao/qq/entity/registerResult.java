package org.lanqiao.qq.entity;

import java.io.Serializable;

/**
 * 2018.7.6 注册结果类
 * 
 * @author dhc
 *
 */
public class registerResult implements Serializable {
	private boolean isSuccess;
	private String resultMessage;

	// 用来注册验证的标志标量
	private boolean nicknameFlag;
	private boolean pwdFlag;
	private boolean ageFlag;
	private boolean emailFlag1; // 标志邮箱格式不对
	private boolean emailFlag2; // 标志有相同邮箱在数据库中

	public boolean isAgeFlag() {
		return ageFlag;
	}

	public void setAgeFlag(boolean ageFlag) {
		this.ageFlag = ageFlag;
	}

	public boolean isNicknameFlag() {
		return nicknameFlag;
	}

	public void setNicknameFlag(boolean nicknameFlag) {
		this.nicknameFlag = nicknameFlag;
	}

	public boolean isPwdFlag() {
		return pwdFlag;
	}

	public void setPwdFlag(boolean pwdFlag) {
		this.pwdFlag = pwdFlag;
	}

	public boolean isEmailFlag1() {
		return emailFlag1;
	}

	public void setEmailFlag1(boolean emailFlag1) {
		this.emailFlag1 = emailFlag1;
	}

	public boolean isEmailFlag2() {
		return emailFlag2;
	}

	public void setEmailFlag2(boolean emailFlag2) {
		this.emailFlag2 = emailFlag2;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
