package net.xuwenhui.core;

/**
 * 接收app层的各种Action
 * <p/>
 * Created by xwh on 2016/3/29.
 */
public interface AppAction {

	/**
	 * 登录
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @param listener  回调监听器
	 */
	void login(String phone_num, String password, ActionCallbackListener<Void> listener);

	/**
	 * 注册
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @param role_id   角色
	 * @param listener  回调监听器
	 */
	void register(String phone_num, String password, int role_id, ActionCallbackListener<Void> listener);
}
