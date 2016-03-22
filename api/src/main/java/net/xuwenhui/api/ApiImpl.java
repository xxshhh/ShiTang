package net.xuwenhui.api;

/**
 * Api实现类
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class ApiImpl implements Api {
	@Override
	public ApiResponse<Void> sendSmsCode4Register(String phoneNum) {
		return null;
	}

	@Override
	public ApiResponse<Void> register(String phoneNum, String code, String password) {
		return null;
	}

	@Override
	public ApiResponse<Void> login(String phoneNum, String password) {
		return null;
	}
}
