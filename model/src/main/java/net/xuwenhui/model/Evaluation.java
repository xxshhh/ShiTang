package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 评价实体类
 * <p/>
 * Created by xwh on 2016/4/25.
 */
public class Evaluation implements Serializable {

	private int evaluation_id;
	private String image_src;
	private String phone_num;
	private String time;
	private float star;
	private String message;

	public Evaluation(int evaluation_id, String image_src, String phone_num, String time, float star, String message) {
		this.evaluation_id = evaluation_id;
		this.image_src = image_src;
		this.phone_num = phone_num;
		this.time = time;
		this.star = star;
		this.message = message;
	}

	public int getEvaluation_id() {
		return evaluation_id;
	}

	public void setEvaluation_id(int evaluation_id) {
		this.evaluation_id = evaluation_id;
	}

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
