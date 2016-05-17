package net.xuwenhui.shitang;

import android.app.Application;

import net.xuwenhui.core.AppAction;
import net.xuwenhui.core.AppActionImpl;
import net.xuwenhui.model.User;

/**
 * Application类，应用级别的操作都放在这里
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class AppApplication extends Application {

	private AppAction mAppAction;

	private User mUser;// 当前登录用户

	@Override
	public void onCreate() {
		super.onCreate();
		mAppAction = new AppActionImpl(this);
	}

	public AppAction getAppAction() {
		return mAppAction;
	}

	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		mUser = user;
	}
}
