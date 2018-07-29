package org.lanqiao.qq.util;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 2018.7.5
 * 对象流工具类，
 */
import java.net.Socket;

public class ObjectUtil {
	/**
	 * 往socket中写对象
	 * @param s
	 * @param o
	 * @throws IOException
	 */
	public static void writeObject(Socket s,Object o) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(o);
	}
	/**
	 * 从socket中读对象
	 * @param s
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object readObject(Socket s) throws ClassNotFoundException, IOException {
		ObjectInputStream ois;
		ois = new ObjectInputStream(s.getInputStream());
		return ois.readObject();
		
	}
}
