package net.xuwenhui.shitang.activity.merchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.model.Dishes;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 商家：添加/修改菜品信息界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class AddEditDishesActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.btn_add_edit_dishes)
	Button mBtnAddEditDishes;
	@Bind(R.id.tv_name)
	TextView mTvName;
	@Bind(R.id.layout_name)
	RelativeLayout mLayoutName;
	@Bind(R.id.img_dishes)
	ImageView mImgDishes;
	@Bind(R.id.layout_image)
	RelativeLayout mLayoutImage;
	@Bind(R.id.tv_category)
	TextView mTvCategory;
	@Bind(R.id.layout_dishes_category)
	RelativeLayout mLayoutDishesCategory;
	@Bind(R.id.tv_price)
	TextView mTvPrice;
	@Bind(R.id.layout_price)
	RelativeLayout mLayoutPrice;

	private Dishes mDishes = null;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_add_edit_dishes;
	}

	@Override
	protected void initData() {
		// 获取Intent
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			mToolbar.setTitle("添加菜品");
		} else {
			mDishes = (Dishes) bundle.getSerializable("Dishes");
			mToolbar.setTitle("修改菜品");
			mTvName.setText(mDishes.getName());
			mTvCategory.setText(String.valueOf(mDishes.getCategory_id()));
			mTvPrice.setText("￥" + mDishes.getPrice());
			mBtnAddEditDishes.setText("修改商品");
		}
		// 设置toolbar
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
		// 菜品名称
		mLayoutName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("菜品名称")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", mDishes.getName(), false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mTvName.setText(input);
							}
						}).show();
			}
		});

		// 菜品图标
		mLayoutImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
			}
		});

		// 选择菜品类别
		mLayoutDishesCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("请选择菜品类别")
						.items("类别1", "类别2", "类别3")
						.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								T.show(mContext, text);
								switch (which) {
									case 0:
										mTvCategory.setText("类别1");
										break;
									case 1:
										mTvCategory.setText("类别2");
										break;
									case 2:
										mTvCategory.setText("类别3");
										break;
									default:
										return true;
								}
								return true;
							}
						})
						.positiveText(R.string.choose)
						.show();
			}
		});

		// 添加/修改菜品
		mBtnAddEditDishes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mDishes == null) {
					T.show(mContext, "添加成功！！！");
				} else {
					T.show(mContext, "修改成功！！！");
				}
			}
		});
	}

}
