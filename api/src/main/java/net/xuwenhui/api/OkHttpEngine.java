package net.xuwenhui.api;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp引擎处理类
 * <p>
 * Created by xwh on 2016/3/29.
 */
public class OkHttpEngine {

	private final static String TAG = "OkHttpEngine";
	private final static String SERVER_URL = "http://192.168.1.104:8080/web/";

	private static OkHttpEngine instance = null;

	private OkHttpEngine() {

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
	 * @throws IOException
	 */
	public <T> T postHandle(Map<String, String> paramsMap, Type typeOfT) throws IOException {
		// 请求表单参数
		FormBody.Builder builder = new FormBody.Builder();
		for (String key : paramsMap.keySet()) {
			builder.add(key, paramsMap.get(key));
			// 打印键表单参数
			Log.i(TAG, "request: key=" + key + " ,value=" + paramsMap.get(key));
		}
		RequestBody formBody = builder.build();
		Request request = new Request.Builder()
				.url(SERVER_URL)
				.post(formBody)
				.build();

		OkHttpClient okHttpClient = new OkHttpClient();
		Response response = okHttpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			// 返回字符串
			final String result = response.body().string();
			// 打印成功结果
			Log.i(TAG, "response: " + result);
			Gson gson = new Gson();
			return gson.fromJson(result, typeOfT);
		} else {
			Log.i(TAG, "response error!!!");
			return null;
		}
	}
}
