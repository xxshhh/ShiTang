package net.xuwenhui.api;

import com.google.gson.reflect.TypeToken;

import net.xuwenhui.model.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Api实现类
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class ApiImpl implements Api {

	private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
	private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

	private OkHttpEngine mOkHttpEngine;

	public ApiImpl() {
		mOkHttpEngine = OkHttpEngine.getInstance();
	}

	@Override
	public ApiResponse<User> login(String phone_num, String password) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "login");
		paramMap.put("phone_num", phone_num);
		paramMap.put("password", password);

		Type type = new TypeToken<ApiResponse<User>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (IOException e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> register(String phone_num, String password, int role_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "register");
		paramMap.put("phone_num", phone_num);
		paramMap.put("password", password);
		paramMap.put("role_id", String.valueOf(role_id));

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (IOException e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}
}
