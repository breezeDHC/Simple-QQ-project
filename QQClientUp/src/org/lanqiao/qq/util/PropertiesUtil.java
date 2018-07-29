package org.lanqiao.qq.util;
/**
 * ʱ�䣺2018.7.5
 * ���ܣ�������ȡclient.properties�Ĺ����࣬����key����valueֵ
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
			System.out.println("��ȡ"+FILENAME+"�쳣");
			e.printStackTrace();
		}
	}
	public static String readPro(String key) {
		return pro.getProperty(key);
	}
}
