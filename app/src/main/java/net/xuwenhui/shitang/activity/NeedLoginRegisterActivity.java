package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.xuwenhui.shitang.R;

import butterknife.Bind;

/**
 * 需要登录/注册界面
 * <p/>
 * Created by xwh on 2016/5/3.
 */
public class NeedLoginRegisterActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.img_person)
	ImageView mImgPerson;
	@Bind(R.id.tv_tips)
	TextView mTvTips;
	@Bind(R.id.btn_login_register)
	Button mBtnLoginRegister;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_need_login_register;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("需要登录/注册");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	@Override
	protected void initListener() {
		// 跳转登录界面
		mBtnLoginRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, LoginActivity.class);
				startActivity(intent);
			}
		});
	}

}
