package net.xuwenhui.core;

/**
 * Action的处理结果回调监听器
 * <p>
 * Created by xwh on 2016/3/29.
 */
public interface ActionCallbackListener<T> {

	/**
	 * 成功时调用
	 *
	 * @param data 返回的数据
	 */
	void onSuccess(T data);

	/**
	 * 失败时调用
	 *
	 * @param errorCode    错误码
	 * @param errorMessage 错误信息
	 */
	void onFailure(String errorCode, String errorMessage);
}
