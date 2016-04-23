package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 菜品实体类
 * <p/>
 * Created by xwh on 2016/4/21.
 */
public class Dishes implements Serializable {

	private int dishes_id;
	private String name;
	private String image_src;
	private float price;
	private int category_id;

	int sell;

	public Dishes(int dishes_id, String name, String image_src, float price, int category_id, int sell) {
		this.dishes_id = dishes_id;
		this.name = name;
		this.image_src = image_src;
		this.price = price;
		this.category_id = category_id;
		this.sell = sell;
	}

	public int getDishes_id() {
		return dishes_id;
	}

	public void setDishes_id(int dishes_id) {
		this.dishes_id = dishes_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getSell() {
		return sell;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}
}
