package org.lanqiao.qq.entity;

import java.io.Serializable;

/**
 * 2018.7.7
 * ���Һ��ѵ��࣬������������(1Ϊ��ȷ���ң�2Ϊ����ȫ��)���Ͳ����˺�
 * @author dhc
 *
 */
public class FindFriends implements Serializable{
	private int type;
	private String account;
	public static final int ONE = 1;
	public static final int ALL = 2;
	public static final int SOME = 3;
	private Usr u;
	public Usr getU() {
		return u;
	}
	public void setU(Usr u) {
		this.u = u;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
