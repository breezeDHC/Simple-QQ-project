package org.lanqiao.qq.biz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class test {
	public static void main(String[] args){
		String filepath1 = "E:"+"\\"+"1.txt";
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(filepath1)));
			String str = "sdfasdjfklsadfja\nasdfsdasdas\n";
			reader = new BufferedReader(new StringReader(str));
			String s = null;
			while((s = reader.readLine()) != null){
				System.out.println(s);
				
				writer.write(s);
				writer.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
