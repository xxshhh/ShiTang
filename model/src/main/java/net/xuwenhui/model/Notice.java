package net.xuwenhui.model;

import java.io.Serializable;

/**
 * 公告
 * <p/>
 * Created by xwh on 2016/5/10.
 */
public class Notice implements Serializable {

	private int notice_id;
	private String title;
	private String image_src;

	public Notice(int notice_id, String title, String image_src) {
		this.notice_id = notice_id;
		this.title = title;
		this.image_src = image_src;
	}

	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}
}
