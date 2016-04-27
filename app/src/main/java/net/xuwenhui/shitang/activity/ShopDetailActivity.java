package net.xuwenhui.shitang.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.ViewpagerAdapter;
import net.xuwenhui.shitang.fragment.DishesFragment;
import net.xuwenhui.shitang.fragment.EmptyFragment;
import net.xuwenhui.shitang.fragment.EvaluationFragment;
import net.xuwenhui.shitang.fragment.ShopInfoFragment;

import butterknife.Bind;

/**
 * 店铺详情界面
 * <p>
 * Created by xwh on 2016/4/20.
 */
public class ShopDetailActivity extends BaseActivity {

	@Bind(R.id.img_shop)
	ImageView mImgShop;
	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.collapsing_toolbar)
	CollapsingToolbarLayout mCollapsingToolbar;
	@Bind(R.id.appbar)
	AppBarLayout mAppbar;
	@Bind(R.id.tv_notice)
	TextView mTvNotice;
	@Bind(R.id.tab_layout)
	TabLayout mTabLayout;
	@Bind(R.id.viewPager)
	ViewPager mViewPager;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_shop_detail;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		mCollapsingToolbar.setTitle("这是店铺名");
		// 设置公告
		mTvNotice.setText("这是一段很长的公告！！！这是一段很长的公告！！！这是一段很长的公告！！！");
		// 设置Viewpager
		ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new DishesFragment(), "点菜");
		adapter.addFragment(new EvaluationFragment(), "评价");
		adapter.addFragment(new ShopInfoFragment(), "商家");
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	@Override
	protected void initListener() {

	}

}
