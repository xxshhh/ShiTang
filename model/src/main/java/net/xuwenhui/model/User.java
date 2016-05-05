package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 用户实体类
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class User implements Serializable {

	private int user_id;
	private int role_id;
	private String phone_num;
	private String password;
	private String image_src;
	private String nickname;
	private boolean is_valid;

	public User() {
		super();
	}

	public User(int user_id, int role_id, String phone_num, String password, String image_src, String nickname, boolean is_valid) {
		this.user_id = user_id;
		this.role_id = role_id;
		this.phone_num = phone_num;
		this.password = password;
		this.image_src = image_src;
		this.nickname = nickname;
		this.is_valid = is_valid;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean is_valid() {
		return is_valid;
	}

	public void setIs_valid(boolean is_valid) {
		this.is_valid = is_valid;
	}
}
