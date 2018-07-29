package org.lanqiao.qq.dao;
/**
 * 时间：2018.7.4
 * 功能：实体类的接口类的父类，用来处理与数据库相关的操作，主要提供公共方法，如获取连接对象， 关闭资源
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
	//初始化信息变量
	static {
		driver = PropertiesUtil.readPro("DRIVER");
		user = PropertiesUtil.readPro("USER");
		url = PropertiesUtil.readPro("URL");
		pwd = PropertiesUtil.readPro("PASSWORD");
	}
	//获取数据库连接
	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			System.out.println("获取数据库连接失败");
			e.printStackTrace();
		}
		return conn;
	}
	//关闭资源
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
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		BaseDao base = new BaseDao();
		Connection c = base.getConn();
		System.out.println(c);
		System.out.println("end");
	}
}
