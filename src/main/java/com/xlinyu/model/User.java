package com.xlinyu.model;

public class User {
	/**
	 * 账号名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 微信账号唯一标识
	 */
	private String openID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

}
