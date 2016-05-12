package net.xuwenhui.shitang.activity.merchant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Dishes;
import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.DensityUtils;
import net.xuwenhui.shitang.util.FileHandleUtil;
import net.xuwenhui.shitang.util.ProgressDialogUtil;
import net.xuwenhui.shitang.util.T;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	Dishes mDishes;

	int shop_id;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_add_edit_dishes;
	}

	@Override
	protected void initData() {
		// 获取Intent
		Bundle bundle = getIntent().getExtras();
		mDishes = (Dishes) bundle.getSerializable("Dishes");
		shop_id = bundle.getInt("shop_id");
		if (mDishes == null) {
			mToolbar.setTitle("添加菜品");
			mTvCategory.setTag(0);
			mImgDishes.setTag("");
			mTvPrice.setTag(0.0f);
		} else {
			mToolbar.setTitle("修改菜品");
			mTvName.setText(mDishes.getName());
			mAppAction.dishes_category_query_by_id(mDishes.getCategory_id(), new ActionCallbackListener<DishesCategory>() {
				@Override
				public void onSuccess(DishesCategory data) {
					mTvCategory.setText(String.valueOf(data.getCategory_desc()));
					mTvCategory.setTag(mDishes.getCategory_id());
				}

				@Override
				public void onFailure(String errorCode, String errorMessage) {
					T.show(mContext, errorMessage);
				}
			});
			mTvPrice.setText("￥" + mDishes.getPrice());
			mTvPrice.setTag(mDishes.getPrice());
			mBtnAddEditDishes.setText("修改菜品");
			mImgDishes.setTag(mDishes.getImage_src());
			if (mDishes.getImage_src().equals("")) {
				Picasso.with(mContext).load(R.mipmap.ic_launcher).into(mImgDishes);
			} else {
				Picasso.with(mContext).load(mDishes.getImage_src())
						.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
						.centerCrop().into(mImgDishes);
			}
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
						.input("", mDishes != null ? mDishes.getName() : "", false, new MaterialDialog.InputCallback() {
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
				mAppAction.dishes_category_query(shop_id, new ActionCallbackListener<List<DishesCategory>>() {
					@Override
					public void onSuccess(final List<DishesCategory> data) {
						final List<String> list = new ArrayList<>();
						for (DishesCategory dishesCategory : data) {
							list.add(dishesCategory.getCategory_desc());
						}
						if (list.size() > 0) {
							new MaterialDialog.Builder(mContext)
									.title("请选择菜品类别")
									.items(list)
									.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
										@Override
										public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
											T.show(mContext, text);
											mTvCategory.setText(list.get(which));
											mTvCategory.setTag(data.get(which).getCategory_id());
											return true;
										}
									})
									.positiveText(R.string.choose)
									.show();
						} else {
							T.show(mContext, "没有菜品类别，请添加。");
						}
					}

					@Override
					public void onFailure(String errorCode, String errorMessage) {
						T.show(mContext, errorMessage);
					}
				});
			}
		});

		// 菜品定价
		mLayoutPrice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("菜品定价")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", mDishes != null ? String.valueOf(mDishes.getPrice()) : "", false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								try {
									float price = Float.parseFloat(input.toString());
									mTvPrice.setText("￥" + price);
									mTvPrice.setTag(price);
								} catch (Exception e) {
									T.show(mContext, "请输入正确的价格格式，如10.0");
								}
							}
						}).show();
			}
		});

		// 添加/修改菜品
		mBtnAddEditDishes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProgressDialogUtil.show(mContext);
				if (mDishes == null) {
					mAppAction.dishes_create(shop_id, (Integer) mTvCategory.getTag(), mTvName.getText().toString(), (String) mImgDishes.getTag(), (Float) mTvPrice.getTag(), new ActionCallbackListener<Dishes>() {
						@Override
						public void onSuccess(Dishes data) {
							T.show(mContext, "添加成功");
							ProgressDialogUtil.dismiss();
							finish();
						}

						@Override
						public void onFailure(String errorCode, String errorMessage) {
							T.show(mContext, errorMessage);
							ProgressDialogUtil.dismiss();
						}
					});
				} else {
					mAppAction.dishes_update(mDishes.getDishes_id(), (Integer) mTvCategory.getTag(), mTvName.getText().toString(), (String) mImgDishes.getTag(), (Float) mTvPrice.getTag(), new ActionCallbackListener<Dishes>() {
						@Override
						public void onSuccess(Dishes data) {
							T.show(mContext, "修改成功！！！");
							ProgressDialogUtil.dismiss();
							finish();
						}

						@Override
						public void onFailure(String errorCode, String errorMessage) {
							T.show(mContext, errorMessage);
							ProgressDialogUtil.dismiss();
						}
					});
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE_PICK_IMAGE) {
				ContentResolver resolver = mContext.getContentResolver();
				File file = null;
				Bitmap bitmap = null;
				try {
					Uri uri = data.getData();
					file = new File(FileHandleUtil.getImageAbsolutePath(mContext, uri));
					bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (file != null && bitmap != null) {
					// 上传头像到七牛图床
					uploadImageToQiniu(file);
				}
			}
		}
	}

	/**
	 * 上传头像到七牛图床
	 *
	 * @param file
	 */
	private void uploadImageToQiniu(final File file) {
		ProgressDialogUtil.show(mContext);
		mAppAction.common_get_qiniu_token(new ActionCallbackListener<String>() {
			@Override
			public void onSuccess(String data) {
				String token = data;
				UploadManager uploadManager = new UploadManager();
				final String name = mApplication.getUser().getUser_id() + "_" + new Date().getTime();
				uploadManager.put(file, name, token,
						new UpCompletionHandler() {
							@Override
							public void complete(String key, ResponseInfo info, JSONObject response) {
								ProgressDialogUtil.dismiss();
								T.show(mContext, "头像上传成功");
								mImgDishes.setTag("http://o6wgg8qjk.bkt.clouddn.com/" + name);
								Picasso.with(mContext).load(file)
										.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
										.centerCrop().into(mImgDishes);
							}
						}, null);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				ProgressDialogUtil.dismiss();
				T.show(mContext, errorMessage);
			}
		});
	}
}
