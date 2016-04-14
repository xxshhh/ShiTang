package net.xuwenhui.api;

/**
 * Api接口
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public interface Api {

	/**
	 * 登录
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @return
	 */
	ApiResponse<Void> login(String phone_num, String password);

	/**
	 * 注册
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @param role_id   角色（2：用户，3：商家）
	 * @return
	 */
	ApiResponse<Void> register(String phone_num, String password, int role_id);
}
