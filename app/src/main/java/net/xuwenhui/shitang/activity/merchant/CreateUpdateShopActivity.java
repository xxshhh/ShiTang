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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.DensityUtils;
import net.xuwenhui.shitang.util.FileHandleUtil;
import net.xuwenhui.shitang.util.ProgressDialogUtil;
import net.xuwenhui.shitang.util.T;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 商家：创建/更新店铺界面
 * <p/>
 * Created by xwh on 2016/5/3.
 */
public class CreateUpdateShopActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.btn_create_update_shop)
	Button mBtnCreateUpdateShop;
	@Bind(R.id.tv_name)
	TextView mTvName;
	@Bind(R.id.layout_name)
	RelativeLayout mLayoutName;
	@Bind(R.id.circle_image_shop)
	CircleImageView mCircleImageShop;
	@Bind(R.id.layout_image)
	RelativeLayout mLayoutImage;
	@Bind(R.id.tv_sort)
	TextView mTvSort;
	@Bind(R.id.layout_sort)
	RelativeLayout mLayoutSort;
	@Bind(R.id.edt_address_desc)
	EditText mEdtAddressDesc;
	@Bind(R.id.layout_address_desc)
	RelativeLayout mLayoutAddressDesc;

	Shop mShop;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_create_update_shop;
	}

	@Override
	protected void initData() {
		// 获取Intent
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			mToolbar.setTitle("创建店铺");
			mTvSort.setTag(0);
			mCircleImageShop.setTag("");
		} else {
			mShop = (Shop) bundle.getSerializable("Shop");
			mToolbar.setTitle("修改店铺");
			mTvName.setText(mShop.getName());
			mTvSort.setText(mShop.getSort_desc());
			switch (mShop.getSort_desc()) {
				case "1食堂":
					mTvSort.setTag(1);
					break;
				case "2食堂":
					mTvSort.setTag(2);
					break;
				case "3食堂":
					mTvSort.setTag(3);
					break;
				case "6食堂":
					mTvSort.setTag(4);
					break;
				case "7食堂":
					mTvSort.setTag(5);
					break;
			}

			mEdtAddressDesc.setText(mShop.getAddress_desc());
			mBtnCreateUpdateShop.setText("修改店铺");
			mCircleImageShop.setTag(mShop.getImage_src());
			if (mShop.getImage_src().equals("")) {
				Picasso.with(mContext).load(R.mipmap.ic_launcher).into(mCircleImageShop);
			} else {
				Picasso.with(mContext).load(mShop.getImage_src())
						.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
						.centerCrop().into(mCircleImageShop);
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
		// 店铺名称
		mLayoutName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("店铺名称")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", mShop != null ? mShop.getName() : "", false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mTvName.setText(input);
							}
						}).show();
			}
		});

		// 店铺头像
		mLayoutImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
			}
		});

		// 选择食堂
		mLayoutSort.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("请选择食堂")
						.items("1食堂", "2食堂", "3食堂", "6食堂", "7食堂")
						.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								T.show(mContext, text);
								switch (which) {
									case 0:
										mTvSort.setTag(1);
										mTvSort.setText("1食堂");
										break;
									case 1:
										mTvSort.setTag(2);
										mTvSort.setText("2食堂");
										break;
									case 2:
										mTvSort.setTag(3);
										mTvSort.setText("3食堂");
										break;
									case 3:
										mTvSort.setTag(4);
										mTvSort.setText("6食堂");
										break;
									case 4:
										mTvSort.setTag(5);
										mTvSort.setText("7食堂");
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

		// 创建/更新店铺
		mBtnCreateUpdateShop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProgressDialogUtil.show(mContext);
				if (mShop == null) {
					mAppAction.shop_create(mApplication.getUser().getUser_id(), (Integer) mTvSort.getTag(), mTvName.getText().toString(), (String) mCircleImageShop.getTag(), mEdtAddressDesc.getText().toString(), new ActionCallbackListener<Shop>() {
						@Override
						public void onSuccess(Shop data) {
							T.show(mContext, "添加成功！！！");
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
					mAppAction.shop_update_info(mShop.getShop_id(), (Integer) mTvSort.getTag(), mTvName.getText().toString(), (String) mCircleImageShop.getTag(), mEdtAddressDesc.getText().toString(), new ActionCallbackListener<Shop>() {
						@Override
						public void onSuccess(Shop data) {
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
								mCircleImageShop.setTag("http://o6wgg8qjk.bkt.clouddn.com/" + name);
								Picasso.with(mContext).load(file)
										.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
										.centerCrop().into(mCircleImageShop);
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
