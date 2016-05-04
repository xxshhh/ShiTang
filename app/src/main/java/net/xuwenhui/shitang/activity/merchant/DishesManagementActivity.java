package net.xuwenhui.shitang.activity.merchant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.model.Dishes;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.adapter.DishesForMerchantAdapter;
import net.xuwenhui.shitang.util.T;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 商家：菜品管理界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class DishesManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.btn_update_dishes)
	Button mBtnUpdateDishes;
	@Bind(R.id.layout_add)
	LinearLayout mLayoutAdd;
	@Bind(R.id.tv_tips)
	TextView mTvTips;
	@Bind(R.id.list_dishes)
	RecyclerView mListDishes;

	DishesForMerchantAdapter mDishesForMerchantAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_dishes_management;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("菜品管理");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 初始化菜品列表
		mListDishes.setLayoutManager(new LinearLayoutManager(mContext));
		mListDishes.setItemAnimator(new DefaultItemAnimator());
		mListDishes.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<Dishes> data2 = new ArrayList<>();
		for (int i = 1; i <= 15; i++) {
			Dishes dishes = new Dishes(i, "测试菜品" + i, "", 10 - i / 5, (i - 1) / 5 + 1, 99 * i);
			data2.add(dishes);
		}
		mDishesForMerchantAdapter = new DishesForMerchantAdapter(mContext, data2);
		mListDishes.setAdapter(mDishesForMerchantAdapter);

		// 更新提示
		updateTips();
	}

	@Override
	protected void initListener() {
		// 监听编辑和删除事件
		mDishesForMerchantAdapter.setOnMyClickListener(new DishesForMerchantAdapter.onMyClickListener() {
			@Override
			public void onEditClickListener(View view, final int position) {
				Intent intent = new Intent(mContext, AddEditDishesActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Dishes", mDishesForMerchantAdapter.getDataList().get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}

			@Override
			public void onDeleteClickListener(View view, final int position) {
				String name = mDishesForMerchantAdapter.getDataList().get(position).getName();
				SpannableString content = new SpannableString("确定要删除 " + name + " 吗？");
				content.setSpan(new ForegroundColorSpan(Color.RED), 6, 6 + name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				new MaterialDialog.Builder(mContext)
						.content(content)
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								mDishesForMerchantAdapter.notifyItemRemoved(position);
								mDishesForMerchantAdapter.getDataList().remove(position);
								mDishesForMerchantAdapter.notifyItemRangeChanged(position, mDishesForMerchantAdapter.getItemCount());
								// 更新提示
								updateTips();
							}
						}).show();
			}
		});

		// 添加事件
		mLayoutAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, AddEditDishesActivity.class);
				startActivity(intent);
			}
		});

		// 上传修改
		mBtnUpdateDishes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "修改成功！！！");
			}
		});
	}

	/**
	 * 根据数量设置提示
	 */
	void updateTips() {
		int count = mDishesForMerchantAdapter.getItemCount();
		if (count == 0) {
			mTvTips.setText("提示：当前没有菜品，请添加。");
		} else {
			SpannableString tips = new SpannableString("提示：当前一共有" + count + "个菜品");
			tips.setSpan(new ForegroundColorSpan(Color.RED), 8, 8 + String.valueOf(count).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			mTvTips.setText(tips);
		}
	}

}
