package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 菜品类别实体类
 * <p/>
 * Created by xwh on 2016/4/23.
 */
public class DishesCategory implements Serializable {

	private int category_id;
	private String category_desc;

	public DishesCategory(String category_desc) {
		this.category_desc = category_desc;
	}

	public DishesCategory(int category_id, String category_desc) {
		this.category_id = category_id;
		this.category_desc = category_desc;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_desc() {
		return category_desc;
	}

	public void setCategory_desc(String category_desc) {
		this.category_desc = category_desc;
	}
}
