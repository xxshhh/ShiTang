package net.xuwenhui.shitang.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.ViewpagerAdapter;

import butterknife.Bind;

/**
 * 订单界面
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class OrderFragment extends BaseFragment {

	@Bind(R.id.tab_layout)
	TabLayout mTabLayout;
	@Bind(R.id.viewPager)
	ViewPager mViewPager;

	OrderUnfinishedFragment mOrderUnfinishedFragment;

	OrderFinishedFragment mOrderFinishedFragment;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_order;
	}

	@Override
	protected void initData() {
		// 设置Viewpager
		ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager());
		mOrderUnfinishedFragment = new OrderUnfinishedFragment();
		mOrderFinishedFragment = new OrderFinishedFragment();
		adapter.addFragment(mOrderUnfinishedFragment, "未完成");
		adapter.addFragment(mOrderFinishedFragment, "已完成");
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	@Override
	protected void initListener() {

	}

}
