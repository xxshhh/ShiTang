package net.xuwenhui.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体类
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class Order implements Serializable {

	private int order_id;
	private int address_id;
	private int order_state_id;
	private String image_src;
	private String name;
	private String create_time;
	private boolean evaluate;
	private float total_price;
	private String note;

	private List<OrderItem> orderItemList;

	public Order(int order_id, int address_id, int order_state_id, String image_src, String name, String create_time, boolean evaluate, float total_price, String note, List<OrderItem> orderItemList) {
		this.order_id = order_id;
		this.address_id = address_id;
		this.order_state_id = order_state_id;
		this.image_src = image_src;
		this.name = name;
		this.create_time = create_time;
		this.evaluate = evaluate;
		this.total_price = total_price;
		this.note = note;
		this.orderItemList = orderItemList;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getOrder_state_id() {
		return order_state_id;
	}

	public void setOrder_state_id(int order_state_id) {
		this.order_state_id = order_state_id;
	}

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public boolean is_evaluate() {
		return evaluate;
	}

	public void setEvaluate(boolean evaluate) {
		this.evaluate = evaluate;
	}

	public float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
