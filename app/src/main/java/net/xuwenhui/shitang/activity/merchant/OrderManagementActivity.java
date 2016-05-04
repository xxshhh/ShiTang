package net.xuwenhui.shitang.activity.merchant;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.adapter.ViewpagerAdapter;
import net.xuwenhui.shitang.fragment.merchant.OrderFinishedForMerchantFragment;
import net.xuwenhui.shitang.fragment.merchant.OrderUnfinishedForMerchantFragment;

import butterknife.Bind;

/**
 * 商家：订单管理界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class OrderManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.tab_layout)
	TabLayout mTabLayout;
	@Bind(R.id.viewPager)
	ViewPager mViewPager;

	OrderUnfinishedForMerchantFragment mOrderUnfinishedForMerchantFragment;

	OrderFinishedForMerchantFragment mOrderFinishedForMerchantFragment;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_order_management;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("订单管理");
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
		mOrderUnfinishedForMerchantFragment = new OrderUnfinishedForMerchantFragment();
		mOrderFinishedForMerchantFragment = new OrderFinishedForMerchantFragment();
		adapter.addFragment(mOrderUnfinishedForMerchantFragment, "未完成");
		adapter.addFragment(mOrderFinishedForMerchantFragment, "已完成");
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	@Override
	protected void initListener() {

	}

}
