package net.xuwenhui.shitang.activity.merchant;

import android.graphics.Color;
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

import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.adapter.DishesCategoryForMerchantAdapter;
import net.xuwenhui.shitang.util.T;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 商家：菜品类别管理界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class DishesCategoryManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.btn_update_category)
	Button mBtnUpdateCategory;
	@Bind(R.id.layout_add)
	LinearLayout mLayoutAdd;
	@Bind(R.id.tv_tips)
	TextView mTvTips;
	@Bind(R.id.list_dishes_category)
	RecyclerView mListDishesCategory;

	DishesCategoryForMerchantAdapter mDishesCategoryForMerchantAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_dishes_category_management;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("菜品类别管理");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 初始化菜品类别列表
		mListDishesCategory.setLayoutManager(new LinearLayoutManager(mContext));
		mListDishesCategory.setItemAnimator(new DefaultItemAnimator());
		mListDishesCategory.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<DishesCategory> data1 = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			DishesCategory dishesCategory = new DishesCategory("类别" + i);
			data1.add(dishesCategory);
		}
		mDishesCategoryForMerchantAdapter = new DishesCategoryForMerchantAdapter(mContext, data1);
		mListDishesCategory.setAdapter(mDishesCategoryForMerchantAdapter);

		// 更新提示
		updateTips();
	}

	@Override
	protected void initListener() {
		// 监听编辑和删除事件
		mDishesCategoryForMerchantAdapter.setOnMyClickListener(new DishesCategoryForMerchantAdapter.onMyClickListener() {
			@Override
			public void onEditClickListener(View view, final int position) {
				String category_desc = mDishesCategoryForMerchantAdapter.getDataList().get(position).getCategory_desc();
				new MaterialDialog.Builder(mContext)
						.title("修改菜品类别描述")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", category_desc, false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mDishesCategoryForMerchantAdapter.getDataList().get(position).setCategory_desc(String.valueOf(input));
								mDishesCategoryForMerchantAdapter.notifyItemChanged(position);
							}
						}).show();
			}

			@Override
			public void onDeleteClickListener(View view, final int position) {
				String category_desc = mDishesCategoryForMerchantAdapter.getDataList().get(position).getCategory_desc();
				SpannableString content = new SpannableString("确定要删除 " + category_desc + " 吗？");
				content.setSpan(new ForegroundColorSpan(Color.RED), 6, 6 + category_desc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				new MaterialDialog.Builder(mContext)
						.content(content)
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								mDishesCategoryForMerchantAdapter.notifyItemRemoved(position);
								mDishesCategoryForMerchantAdapter.getDataList().remove(position);
								mDishesCategoryForMerchantAdapter.notifyItemRangeChanged(position, mDishesCategoryForMerchantAdapter.getItemCount());
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
				new MaterialDialog.Builder(mContext)
						.title("添加菜品类别")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("输入菜品类别描述", "", false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mDishesCategoryForMerchantAdapter.getDataList().add(new DishesCategory(String.valueOf(input)));
								mDishesCategoryForMerchantAdapter.notifyItemInserted(mDishesCategoryForMerchantAdapter.getItemCount() - 1);

								mListDishesCategory.smoothScrollToPosition(mDishesCategoryForMerchantAdapter.getItemCount() - 1);
								// 更新提示
								updateTips();
							}
						}).show();
			}
		});

		// 上传修改
		mBtnUpdateCategory.setOnClickListener(new View.OnClickListener() {
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
		int count = mDishesCategoryForMerchantAdapter.getItemCount();
		if (count == 0) {
			mTvTips.setText("提示：当前没有菜品类别，请添加。");
		} else {
			SpannableString tips = new SpannableString("提示：当前一共有" + count + "个菜品类别");
			tips.setSpan(new ForegroundColorSpan(Color.RED), 8, 8 + String.valueOf(count).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			mTvTips.setText(tips);
		}
	}

}
