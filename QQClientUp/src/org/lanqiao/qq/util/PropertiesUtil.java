package org.lanqiao.qq.util;
/**
 * 时间：2018.7.5
 * 功能：用来读取client.properties的工具类，根据key返回value值
 */
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static final String FILENAME;
	private static Properties pro;
	static {
		FILENAME = "client.properties";
		pro = new Properties();
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILENAME));
		} catch (IOException e) {
			System.out.println("读取"+FILENAME+"异常");
			e.printStackTrace();
		}
	}
	public static String readPro(String key) {
		return pro.getProperty(key);
	}
}
