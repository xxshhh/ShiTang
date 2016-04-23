package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 店铺实体类
 * <p>
 * Created by xwh on 2016/4/15.
 */
public class Shop implements Serializable {

	private int shop_id;
	private String name;
	private String image_src;
	private String sort_desc;

	private int delivery_time;
	private float avg_star;
	private int sell;

	public Shop(int shop_id, String name, String image_src, String sort_desc, int delivery_time, float avg_star, int sell) {
		this.shop_id = shop_id;
		this.name = name;
		this.image_src = image_src;
		this.sort_desc = sort_desc;
		this.delivery_time = delivery_time;
		this.avg_star = avg_star;
		this.sell = sell;
	}

	public int getShop_id() {
		return shop_id;
	}

	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
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

	public String getSort_desc() {
		return sort_desc;
	}

	public void setSort_desc(String sort_desc) {
		this.sort_desc = sort_desc;
	}

	public int getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(int delivery_time) {
		this.delivery_time = delivery_time;
	}

	public float getAvg_star() {
		return avg_star;
	}

	public void setAvg_star(float avg_star) {
		this.avg_star = avg_star;
	}

	public int getSell() {
		return sell;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}
}
