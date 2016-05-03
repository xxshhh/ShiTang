package net.xuwenhui.shitang.activity.merchant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.FileHandleUtil;
import net.xuwenhui.shitang.util.T;

import java.io.File;

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

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_create_update_shop;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("创建店铺");
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
		// 修改店铺名称
		mLayoutName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("修改店铺名称")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", "", false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mTvName.setText(input);
							}
						}).show();
			}
		});

		// 修改店铺头像
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
										mTvSort.setText("1食堂");
										break;
									case 1:
										mTvSort.setText("2食堂");
										break;
									case 2:
										mTvSort.setText("3食堂");
										break;
									case 3:
										mTvSort.setText("6食堂");
										break;
									case 4:
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
				T.show(mContext, "创建成功！！！");
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
					// 上传头像到服务器
					mCircleImageShop.setImageBitmap(bitmap);
				}
			}
		}
	}
}
