package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 地址实体类
 * <p/>
 * Created by xwh on 2016/4/28.
 */
public class Address implements Serializable {

	private int address_id;
	private String name;
	private String sex;
	private String phone_num;
	private String address_desc;
	private String note;

	public Address(int address_id, String name, String sex, String phone_num, String address_desc, String note) {
		this.address_id = address_id;
		this.name = name;
		this.sex = sex;
		this.phone_num = phone_num;
		this.address_desc = address_desc;
		this.note = note;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getAddress_desc() {
		return address_desc;
	}

	public void setAddress_desc(String address_desc) {
		this.address_desc = address_desc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
