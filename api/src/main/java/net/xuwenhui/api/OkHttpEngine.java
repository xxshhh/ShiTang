package net.xuwenhui.api;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp引擎处理类
 * <p/>
 * Created by xwh on 2016/3/29.
 */
public class OkHttpEngine {

	private final static String TAG = "OkHttpEngine";
	private final static String SERVER_URL = "http://192.168.191.1:8080/web/";

	private OkHttpClient mOkHttpClient;

	private static OkHttpEngine instance = null;

	private OkHttpEngine() {
		mOkHttpClient = new OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.build();
	}

	public static OkHttpEngine getInstance() {
		if (instance == null) {
			instance = new OkHttpEngine();
		}
		return instance;
	}

	/**
	 * Post请求表单
	 *
	 * @param paramsMap 表单参数
	 * @param typeOfT   泛型参数类型
	 * @param <T>       泛型参数
	 * @return
	 * @throws Exception
	 */
	public <T> T postHandle(Map<String, String> paramsMap, Type typeOfT) throws Exception {
		// 请求表单参数
		FormBody.Builder builder = new FormBody.Builder();
		for (String key : paramsMap.keySet()) {
			builder.add(key, paramsMap.get(key));
			// 打印键表单参数
			Log.e(TAG, "request: key=" + key + " ,value=" + paramsMap.get(key));
		}
		RequestBody formBody = builder.build();
		Request request = new Request.Builder()
				.url(SERVER_URL)
				.post(formBody)
				.build();

		Response response = mOkHttpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			// 返回字符串
			final String result = response.body().string();
			// 打印成功结果
			Log.e(TAG, "response: " + result);
			Gson gson = new Gson();
			return gson.fromJson(result, typeOfT);
		} else {
			Log.e(TAG, "response error!!!");
			return null;
		}
	}
}
