package net.xuwenhui.shitang.activity.admin;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.xuwenhui.model.User;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.adapter.UserForAdminAdapter;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 管理员：用户管理界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class UserManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.list_user)
	RecyclerView mListUser;

	UserForAdminAdapter mUserForAdminAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_user_management;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("用户管理");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 初始化用户列表
		mListUser.setLayoutManager(new LinearLayoutManager(mContext));
		mListUser.setItemAnimator(new DefaultItemAnimator());
		mListUser.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<User> data = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			User user = new User(i, 2, "1899562914" + i % 10, "", "", "", true);
			data.add(user);
		}
		mUserForAdminAdapter = new UserForAdminAdapter(mContext, data);
		mListUser.setAdapter(mUserForAdminAdapter);
	}

	@Override
	protected void initListener() {

	}

}
