package com.weixin.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Test;

public class ManagerServletTest {
	
	@Test
	public void testManager() throws IOException {
		URL url = new URL("http://localhost:8080/weixin2.0/addClass");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		String json = "{\"class\":[\"140431\", \"140432\",\"150451\",\"150452\",\"130443\",\"140423\"],"
				+ "\"name\":\"万海燕\"}";
//		String json = "{\"class\":[\"140431\", \"140432\"],"
//				+ "\"name\":\"万海燕\"}";
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		//post请求
		OutputStream out = connection.getOutputStream();
		out.write(json.getBytes("UTF-8"));
		System.out.println(json);
		out.close();
		InputStream input = connection.getInputStream();
		InputStreamReader reade = new InputStreamReader(input, "UTF-8");
		BufferedReader bufferReader = new BufferedReader(reade);
		String line = bufferReader.readLine();
		bufferReader.close();
		reade.close();
		input.close();
		connection.disconnect();
		System.out.println("line:" + line);
	}
//	@Test
	public void testDel() throws IOException {
		URL url = new URL("http://localhost:8080/weixin2.0/delClass");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		String json = "{\"class\":[\"140431\", \"140432\",\"150451\",\"150452\",\"130443\"],"
				+ "\"name\":\"王艺\"}";
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		//post请求
		OutputStream out = connection.getOutputStream();
		out.write(json.getBytes("UTF-8"));
		System.out.println(json);
		out.close();
		InputStream input = connection.getInputStream();
		InputStreamReader reade = new InputStreamReader(input, "UTF-8");
		BufferedReader bufferReader = new BufferedReader(reade);
		String line = bufferReader.readLine();
		bufferReader.close();
		reade.close();
		input.close();
		connection.disconnect();
		System.out.println("line:" + line);
	}
	
	
}
