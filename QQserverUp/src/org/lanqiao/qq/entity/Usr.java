package org.lanqiao.qq.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 2018.7.4
 * ���ܣ�ʵ���࣬��Ӧ�����ݿ��е�Usr����������ж�Ӧ����������getter��setter����
 * @author dhc
 *
 */
public class Usr implements Serializable{
	private String userAccount;
	private String passWord;
	private String nickname;
	private int age;
	private String email;
	private String img;
	
	//=========================���ݿ���û�е��е�����
	//�û�����
	private List<Usr> friends;
	
	public List<Usr> getFriends() {
		return friends;
	}
	public void setFriends(List<Usr> friends) {
		this.friends = friends;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
}
