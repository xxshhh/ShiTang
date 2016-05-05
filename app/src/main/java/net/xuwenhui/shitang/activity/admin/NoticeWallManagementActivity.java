package net.xuwenhui.shitang.activity.admin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.FileHandleUtil;
import net.xuwenhui.shitang.util.T;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 管理员：公告墙管理界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class NoticeWallManagementActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.slider_show)
	SliderLayout mSliderShow;
	@Bind(R.id.btn_update_notice_wall)
	Button mBtnUpdateNoticeWall;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	private MaterialDialog mDialog = null;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_notice_wall_management;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("公告墙管理");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 初始化图片展示墙
		initImageDisplay();
	}

	@Override
	protected void initListener() {
		// 更新广告墙
		mBtnUpdateNoticeWall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("请选择更新序号")
						.items("序号1", "序号2", "序号3", "序号4", "序号5")
						.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								T.show(mContext, text);
								mDialog = new MaterialDialog.Builder(mContext)
										.title("更新" + text)
										.customView(R.layout.dialog_update_notice_wall, true)
										.negativeText(R.string.disagree)
										.positiveText(R.string.agree)
										.build();
								EditText editText = (EditText) mDialog.getCustomView().findViewById(R.id.edt_title);
								ImageView imageView = (ImageView) mDialog.getCustomView().findViewById(R.id.img_notice_wall);
								imageView.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										Intent intent = new Intent();
										intent.setType("image/*");
										intent.setAction(Intent.ACTION_GET_CONTENT);
										startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
									}
								});
								mDialog.show();
								return true;
							}
						})
						.positiveText(R.string.choose)
						.show();
			}
		});
	}

	/**
	 * 初始化图片展示墙
	 */
	private void initImageDisplay() {
		Map<String, String> url_maps = new LinkedHashMap<>();
		url_maps.put("必胜客下午茶", "http://7xjda2.com1.z0.glb.clouddn.com/t1.jpg");
		url_maps.put("KFC宅急送", "http://7xjda2.com1.z0.glb.clouddn.com/t2.jpg");
		url_maps.put("八方缘食馆", "http://7xjda2.com1.z0.glb.clouddn.com/t3.jpg");
		url_maps.put("小何屋外卖店", "http://7xjda2.com1.z0.glb.clouddn.com/t4.jpg");

		for (String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(mContext);
			// initialize a SliderLayout
			textSliderView
					.description(name)
					.image(url_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle()
					.putString("extra", name);

			mSliderShow.addSlider(textSliderView);
		}
		mSliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
		mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mSliderShow.setCustomAnimation(new DescriptionAnimation());
		mSliderShow.setDuration(6000);
	}

	@Override
	public void onResume() {
		mSliderShow.startAutoCycle(6000, 6000, true);
		super.onResume();
	}

	@Override
	public void onStop() {
		mSliderShow.stopAutoCycle();
		super.onStop();
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
		T.show(mContext, slider.getDescription());
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
					((ImageView) mDialog.getCustomView().findViewById(R.id.img_notice_wall)).setBackgroundDrawable(new BitmapDrawable(bitmap));
				}
			}
		}
	}
}
