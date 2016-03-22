package net.xuwenhui.shitang.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.xuwenhui.shitang.MyApplication;

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
	protected MyApplication mApplication;

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
	protected abstract int initData();

	/**
	 * 初始化监听器
	 *
	 * @return
	 */
	protected abstract int initListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// 字段赋值
		TAG = this.getClass().getSimpleName();
		mContext = getApplicationContext();
		mApplication = (MyApplication) this.getApplication();
		// 方法调用
		setContentView(getContentLayoutId());
		ButterKnife.bind(this); // 注解绑定
		initData();
		initListener();
		Log.e(TAG, "TaskId:" + this.getTaskId());
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
}
