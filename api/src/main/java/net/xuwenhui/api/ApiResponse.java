package net.xuwenhui.api;

/**
 * 响应结果的json数据实体类
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class ApiResponse<T> {
	/**
	 * json数据分类
	 * {"event": "0", "msg": "success"}
	 * {"event": "0", "msg": "success", "obj":{...}}
	 * {"event": "0", "msg": "success", "objList":[{...}, {...}], "currentPage": 1, "pageSize": 20, "maxCount": 2, "maxPage": 1}
	 */

	/**
	 * 返回码，0为成功
	 */
	private String event;
	/**
	 * 返回信息
	 */
	private String msg;
	/**
	 * 单个对象
	 */
	private T obj;
	/**
	 * 数组对象
	 */
	private T objList;
	/**
	 * 当前页数
	 */
	private int currentPage;
	/**
	 * 每页显示数量
	 */
	private int pageSize;
	/**
	 * 总条数
	 */
	private int maxCount;
	/**
	 * 总页数
	 */
	private int maxPage;

	public ApiResponse(String event, String msg) {
		this.event = event;
		this.msg = msg;
	}

	/**
	 * 响应是否成功
	 *
	 * @return
	 */
	public boolean isSuccess() {
		return event.equals("0");
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public T getObjList() {
		return objList;
	}

	public void setObjList(T objList) {
		this.objList = objList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
}
