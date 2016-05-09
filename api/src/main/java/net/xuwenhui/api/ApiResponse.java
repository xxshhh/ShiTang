package net.xuwenhui.api;

/**
 * 响应结果的json数据实体类
 * <p>
 * Created by xwh on 2016/3/22.
 */
public class ApiResponse<T> {

	/**
	 * {
	 * code：0
	 * message: "success"
	 * data: { key1: value1, key2: value2, ... }
	 * }
	 */

	private String code; // 状态码，0表示成功，非0表示各种不同的错误
	private String message; // 描述信息，成功时为"success"，错误时则是错误信息
	private T data; // 成功时返回的数据，类型为对象或数组

	public ApiResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 响应是否成功
	 *
	 * @return
	 */
	public boolean isSuccess() {
		return code.equals("0");
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
