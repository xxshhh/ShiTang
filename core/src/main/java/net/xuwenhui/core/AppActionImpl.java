package net.xuwenhui.core;

import android.content.Context;
import android.os.AsyncTask;

import net.xuwenhui.api.Api;
import net.xuwenhui.api.ApiImpl;
import net.xuwenhui.api.ApiResponse;
import net.xuwenhui.core.util.FormCheckUtil;
import net.xuwenhui.model.User;

/**
 * AppAction接口的实现类
 * <p/>
 * Created by xwh on 2016/3/29.
 */
public class AppActionImpl implements AppAction {

	static final public String PARAM_NULL = "PARAM_NULL"; // 参数为空
	static final public String PARAM_ILLEGAL = "PARAM_ILLEGAL"; // 参数不合法

	private Context mContext;
	private Api mApi;

	public AppActionImpl(Context context) {
		mContext = context;
		mApi = new ApiImpl();
	}

	@Override
	public void login(final String phone_num, final String password, final ActionCallbackListener<User> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(phone_num)) {
			if (listener != null)
				listener.onFailure(PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num) && !phone_num.equals("admin")) {
			if (listener != null)
				listener.onFailure(PARAM_ILLEGAL, "手机号不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(password)) {
			if (listener != null)
				listener.onFailure(PARAM_NULL, "密码为空");
			return;
		}
		if (!FormCheckUtil.checkPassword(password)) {
			if (listener != null)
				listener.onFailure(PARAM_NULL, "密码长度必须为6-16位");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<User>>() {

			@Override
			protected ApiResponse<User> doInBackground(Void... voids) {
				return mApi.login(phone_num, password);
			}

			@Override
			protected void onPostExecute(ApiResponse<User> response) {
				if (listener != null && response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void register(final String phone_num, final String password, final int role_id, final ActionCallbackListener<Void> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(phone_num)) {
			if (listener != null)
				listener.onFailure(PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num)) {
			if (listener != null)
				listener.onFailure(PARAM_ILLEGAL, "手机号不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(password)) {
			if (listener != null)
				listener.onFailure(PARAM_NULL, "密码为空");
			return;
		}
		if (!FormCheckUtil.checkPassword(password)) {
			if (listener != null)
				listener.onFailure(PARAM_NULL, "密码长度必须为6-16位");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.register(phone_num, password, role_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (listener != null && response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}
}
