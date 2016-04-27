package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 订单项实体类
 * <p/>
 * Created by xwh on 2016/4/23.
 */
public class OrderItem implements Serializable {

	private int dishes_id;
	private String dishes_name;
	private float price;
	private int count;

	public OrderItem(int dishes_id, String dishes_name, float price, int count) {
		this.dishes_id = dishes_id;
		this.dishes_name = dishes_name;
		this.price = price;
		this.count = count;
	}

	public int getDishes_id() {
		return dishes_id;
	}

	public void setDishes_id(int dishes_id) {
		this.dishes_id = dishes_id;
	}

	public String getDishes_name() {
		return dishes_name;
	}

	public void setDishes_name(String dishes_name) {
		this.dishes_name = dishes_name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
