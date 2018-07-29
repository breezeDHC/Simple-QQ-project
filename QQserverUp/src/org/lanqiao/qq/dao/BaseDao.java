package org.lanqiao.qq.dao;
/**
 * ʱ�䣺2018.7.4
 * ���ܣ�ʵ����Ľӿ���ĸ��࣬�������������ݿ���صĲ�������Ҫ�ṩ�������������ȡ���Ӷ��� �ر���Դ
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.lanqiao.qq.util.PropertiesUtil;

public class BaseDao {
	private static String driver;
	private static String user;
	private static String url;
	private static String pwd;
	//��ʼ����Ϣ����
	static {
		driver = PropertiesUtil.readPro("DRIVER");
		user = PropertiesUtil.readPro("USER");
		url = PropertiesUtil.readPro("URL");
		pwd = PropertiesUtil.readPro("PASSWORD");
	}
	//��ȡ���ݿ�����
	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			System.out.println("��ȡ���ݿ�����ʧ��");
			e.printStackTrace();
		}
		return conn;
	}
	//�ر���Դ
	public void closeAll(Connection conn,Statement stat, ResultSet rs) {
		
		try{
			if (rs != null) {
				rs.close();
			}
			if (stat != null) {
				stat.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		BaseDao base = new BaseDao();
		Connection c = base.getConn();
		System.out.println(c);
		System.out.println("end");
	}
}
