package com.phoobobo.robot.bean;

import java.util.Date;

//消息对象类，消息返回类型
public class ChatMessage {

	private String name;
	private String msg;
	private Type type;
	private Date date;
	
	public enum Type{
		INCOMING, OUTGOING
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
