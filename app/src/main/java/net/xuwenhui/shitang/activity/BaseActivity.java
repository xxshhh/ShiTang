package net.xuwenhui.shitang.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.xuwenhui.core.AppAction;
import net.xuwenhui.shitang.AppApplication;

import butterknife.ButterKnife;

/**
 * Activity抽象基类
 * <p/>
 * Created by xwh on 2016/3/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

	/**
	 * 打印标签
	 */
	protected String TAG;
	/**
	 * 上下文实例
	 */
	protected Context mContext;
	/**
	 * 应用全局实例
	 */
	protected AppApplication mApplication;
	/**
	 * 核心层的Action实例
	 */
	protected AppAction mAppAction;

	/**
	 * 获取布局文件Id
	 *
	 * @return
	 */
	protected abstract int getContentLayoutId();

	/**
	 * 初始化数据
	 *
	 * @return
	 */
	protected abstract void initData();

	/**
	 * 初始化监听器
	 *
	 * @return
	 */
	protected abstract void initListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// 字段赋值
		TAG = getClass().getName();
		mContext = this;
		mApplication = (AppApplication) getApplication();
		mAppAction = mApplication.getAppAction();
		// 方法调用
		setContentView(getContentLayoutId());
		ButterKnife.bind(this); // 注解绑定
		initData();
		initListener();
		Log.i(TAG, "onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}
}
