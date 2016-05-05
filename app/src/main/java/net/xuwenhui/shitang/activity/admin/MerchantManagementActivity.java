package net.xuwenhui.shitang.activity.admin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.adapter.ViewpagerAdapter;
import net.xuwenhui.shitang.fragment.admin.MerchantFinishedFragment;
import net.xuwenhui.shitang.fragment.admin.MerchantUnfinishedFragment;

import butterknife.Bind;

/**
 * 管理员：商家管理界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class MerchantManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.tab_layout)
	TabLayout mTabLayout;
	@Bind(R.id.viewPager)
	ViewPager mViewPager;

	MerchantUnfinishedFragment mMerchantUnfinishedFragment;

	MerchantFinishedFragment mMerchantFinishedFragment;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_merchant_management;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("商户管理");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		// 设置Viewpager
		ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
		mMerchantUnfinishedFragment = new MerchantUnfinishedFragment();
		mMerchantFinishedFragment = new MerchantFinishedFragment();
		adapter.addFragment(mMerchantUnfinishedFragment, "未审核");
		adapter.addFragment(mMerchantFinishedFragment, "已通过");
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	@Override
	protected void initListener() {

	}

}
