package net.xuwenhui.api;

/**
 * Api接口
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public interface Api {
	/**
	 * 发送验证码
	 *
	 * @param phoneNum 手机号
	 * @return
	 */
	public ApiResponse<Void> sendSmsCode4Register(String phoneNum);

	/**
	 * 注册
	 *
	 * @param phoneNum 手机号
	 * @param code     验证码
	 * @param password MD5加密的密码
	 * @return
	 */
	public ApiResponse<Void> register(String phoneNum, String code, String password);

	/**
	 * 登录
	 *
	 * @param phoneNum 手机号
	 * @param password MD5加密的密码
	 * @return
	 */
	public ApiResponse<Void> login(String phoneNum, String password);
}
