package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 用户实体类
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class User implements Serializable {
	/**
	 * 唯一id
	 */
	private int id;
	/**
	 * 手机号
	 */
	private String phoneNum;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户类型
	 */
	private int userType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
