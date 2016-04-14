package net.xuwenhui.shitang;

import android.app.Application;

import net.xuwenhui.core.AppAction;
import net.xuwenhui.core.AppActionImpl;

/**
 * Application类，应用级别的操作都放在这里
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class AppApplication extends Application {

	private AppAction mAppAction;

	@Override
	public void onCreate() {
		super.onCreate();
		mAppAction = new AppActionImpl(this);
	}

	public AppAction getAppAction() {
		return mAppAction;
	}
}
