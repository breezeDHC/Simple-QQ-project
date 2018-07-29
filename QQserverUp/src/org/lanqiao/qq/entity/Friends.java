package org.lanqiao.qq.entity;
/**
 * 
 * 2018.7.4
 * 功能：实体类，对应于数据库中的Friends表，包括与表列对应的属性名和getter和setter方法
 * @author dhc
 *
 */
public class Friends {
	private String userAccount;
	private String friendAccount;
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getFriendAccount() {
		return friendAccount;
	}
	public void setFriendAccount(String friendAccount) {
		this.friendAccount = friendAccount;
	}
}
