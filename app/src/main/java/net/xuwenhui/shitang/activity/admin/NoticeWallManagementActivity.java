package net.xuwenhui.shitang.activity.admin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Notice;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.FileHandleUtil;
import net.xuwenhui.shitang.util.ProgressDialogUtil;
import net.xuwenhui.shitang.util.T;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 管理员：公告墙管理界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class NoticeWallManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.slider_show)
	SliderLayout mSliderShow;
	@Bind(R.id.btn_update_notice_wall)
	Button mBtnUpdateNoticeWall;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	List<Notice> mNoticeList;

	MaterialDialog mDialog;

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
								final Notice notice = mNoticeList.get(which);
								mDialog = new MaterialDialog.Builder(mContext)
										.title("更新" + text)
										.customView(R.layout.dialog_update_notice_wall, true)
										.negativeText(R.string.disagree)
										.positiveText(R.string.agree)
										.build();
								final EditText editText = (EditText) mDialog.getCustomView().findViewById(R.id.edt_title);
								editText.setText(notice.getTitle());
								final ImageView imageView = (ImageView) mDialog.getCustomView().findViewById(R.id.img_notice_wall);
								Picasso.with(mContext).load(notice.getImage_src()).into(imageView);
								imageView.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										Intent intent = new Intent();
										intent.setType("image/*");
										intent.setAction(Intent.ACTION_GET_CONTENT);
										startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
									}
								});
								mDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										mAppAction.notice_update(notice.getNotice_id(), editText.getText().toString(), (String) imageView.getTag(), new ActionCallbackListener<Void>() {
											@Override
											public void onSuccess(Void data) {
												T.show(mContext, "更新成功");
												mDialog.dismiss();
											}

											@Override
											public void onFailure(String errorCode, String errorMessage) {
												T.show(mContext, errorMessage);
											}
										});
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
		final Map<String, String> url_maps = new LinkedHashMap<>();
		mAppAction.notice_query(new ActionCallbackListener<List<Notice>>() {
			@Override
			public void onSuccess(List<Notice> data) {
				mNoticeList = data;
				for (Notice notice : data) {
					url_maps.put(notice.getTitle(), notice.getImage_src());
				}
				// 设置图片展示墙
				setupImageDisplay(url_maps);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, "请求图片墙失败，使用默认图片");
				// 测试数据
				mNoticeList.add(new Notice(1, "肯德基 炸鸡套餐", "http://o6wgg8qjk.bkt.clouddn.com/default1.jpg"));
				mNoticeList.add(new Notice(2, "良品铺子 星座粽", "http://o6wgg8qjk.bkt.clouddn.com/default2.jpg"));
				mNoticeList.add(new Notice(3, "麦当劳 愤怒的小鸟", "http://o6wgg8qjk.bkt.clouddn.com/default3.jpg"));
				url_maps.put("肯德基 炸鸡套餐", "http://o6wgg8qjk.bkt.clouddn.com/default1.jpg");
				url_maps.put("良品铺子 星座粽", "http://o6wgg8qjk.bkt.clouddn.com/default2.jpg");
				url_maps.put("麦当劳 愤怒的小鸟", "http://o6wgg8qjk.bkt.clouddn.com/default3.jpg");

				// 设置图片展示墙
				setupImageDisplay(url_maps);
			}
		});
	}

	/**
	 * 设置图片展示墙
	 *
	 * @param url_maps
	 */
	private void setupImageDisplay(Map<String, String> url_maps) {
		for (String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(mContext);
			// initialize a SliderLayout
			textSliderView
					.description(name)
					.image(url_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
						@Override
						public void onSliderClick(BaseSliderView slider) {
							T.show(mContext, slider.getDescription());
						}
					});

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
		mSliderShow.startAutoCycle(6000, 6000, true);
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
								final ImageView imageView = (ImageView) mDialog.getCustomView().findViewById(R.id.img_notice_wall);
								imageView.setTag("http://o6wgg8qjk.bkt.clouddn.com/" + name);
								Picasso.with(mContext).load(file).into(imageView);
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
