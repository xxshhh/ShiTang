package net.xuwenhui.shitang.activity.merchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 商家：店铺功能界面
 * <p/>
 * Created by xwh on 2016/5/3.
 */
public class ShopFunctionActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.layout_info)
	LinearLayout mLayoutInfo;
	@Bind(R.id.layout_notice)
	LinearLayout mLayoutNotice;
	@Bind(R.id.layout_dishes_category)
	LinearLayout mLayoutDishesCategory;
	@Bind(R.id.layout_dishes)
	LinearLayout mLayoutDishes;
	@Bind(R.id.layout_order)
	LinearLayout mLayoutOrder;
	@Bind(R.id.layout_more)
	LinearLayout mLayoutMore;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_shop_function;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("店铺功能");
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
		// 基本信息管理
		mLayoutInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, CreateUpdateShopActivity.class);
				Bundle bundle = new Bundle();
				Shop shop = new Shop(1, "测试店铺", "", "6食堂", 0, 0, 0);
				bundle.putSerializable("Shop", shop);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		// 发布公告
		mLayoutNotice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, NoticeManagementActivity.class);
				startActivity(intent);
			}
		});

		// 菜品类别管理
		mLayoutDishesCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, DishesCategoryManagementActivity.class);
				startActivity(intent);
			}
		});

		// 菜品管理
		mLayoutDishes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, DishesManagementActivity.class);
				startActivity(intent);
			}
		});

		// 订单管理
		mLayoutOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		// 更多
		mLayoutMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "更多功能,敬请期待...");
			}
		});
	}

}
