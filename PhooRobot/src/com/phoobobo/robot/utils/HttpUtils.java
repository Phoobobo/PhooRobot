package com.phoobobo.robot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.phoobobo.robot.bean.ChatMessage;
import com.phoobobo.robot.bean.ChatMessage.Type;
import com.phoobobo.robot.bean.Result;

public class HttpUtils {
	
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "c3c770325dc54b858afd013c21d9b74c";

	/**
	 * @author Phoobobo
	 * @param the msg that we sent
	 * @return the msg that we get
	 */
	public static ChatMessage sendMessage(String msg) {
		
		ChatMessage chatMessage = new ChatMessage();
		
		String jsonResult = doGet(msg);
		Gson gson = new Gson();
		Result result = null;
		try {
			result = gson.fromJson(jsonResult, Result.class);
			if(result.getCode() == 100000) {
				chatMessage.setMsg(result.getText());
			}
			
		} catch (JsonSyntaxException e) {
			chatMessage.setMsg("Sorry, the server is busy :(");
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		
		return chatMessage;
	}
	public static String doGet(String msg) {
		
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URL netUrl = new URL(url);
			// 打开一个链接
			HttpURLConnection conn = (HttpURLConnection) netUrl.openConnection();
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			
			is = conn.getInputStream();
			int len = -1;
			byte[] buffer = new byte[128];
			baos = new ByteArrayOutputStream();
			//读取输入流
			while((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush(); //清除缓冲区
			result = new String(baos.toByteArray()); //输入流本地化
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// lose all the resources!!
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static String setParams(String msg) {
		String url = "";
		try {
			url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

}
