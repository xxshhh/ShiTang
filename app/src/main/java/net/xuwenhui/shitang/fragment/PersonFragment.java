package net.xuwenhui.shitang.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.User;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.AddressActivity;
import net.xuwenhui.shitang.activity.MainActivity;
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
 * 我的信息界面
 * <p>
 * Created by xwh on 2016/5/2.
 */
public class PersonFragment extends BaseFragment {

	@Bind(R.id.circle_image_person)
	CircleImageView mCircleImagePerson;
	@Bind(R.id.layout_image)
	RelativeLayout mLayoutImage;
	@Bind(R.id.tv_nickname)
	TextView mTvNickname;
	@Bind(R.id.layout_nickname)
	RelativeLayout mLayoutNickname;
	@Bind(R.id.tv_phone_num)
	TextView mTvPhoneNum;
	@Bind(R.id.layout_phone_num)
	RelativeLayout mLayoutPhoneNum;
	@Bind(R.id.layout_password)
	RelativeLayout mLayoutPassword;
	@Bind(R.id.layout_address)
	RelativeLayout mLayoutAddress;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;//选择图片

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_person;
	}

	@Override
	protected void initData() {
		// 填充数据
		if (mApplication.getUser().getImage_src().equals("")) {
			Picasso.with(mContext).load(R.mipmap.ic_launcher).into(mCircleImagePerson);
		} else {
			Picasso.with(mContext).load(mApplication.getUser().getImage_src())
					.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
					.centerCrop().into(mCircleImagePerson);
		}
		if (mApplication.getUser().getNickname().equals("")) {
			mTvNickname.setText("暂无昵称");
		} else {
			mTvNickname.setText(mApplication.getUser().getNickname());
		}
		mTvPhoneNum.setText(mApplication.getUser().getPhone_num().substring(0, 3) + "****" + mApplication.getUser().getPhone_num().substring(7, 11));
	}

	@Override
	protected void initListener() {
		// 更换头像
		mLayoutImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
			}
		});

		// 修改昵称
		mLayoutNickname.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("修改昵称")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", mApplication.getUser().getNickname(), false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
								mAppAction.user_update_info(mApplication.getUser().getUser_id(), mApplication.getUser().getImage_src(), input.toString(), new ActionCallbackListener<User>() {
									@Override
									public void onSuccess(User data) {
										T.show(mContext, "昵称更新成功");
										mTvNickname.setText(input.toString());
										mApplication.setUser(data);
										((MainActivity) getActivity()).updateInfo();
									}

									@Override
									public void onFailure(String errorCode, String errorMessage) {
										T.show(mContext, errorMessage);
									}
								});
							}
						}).show();

			}
		});

		// 手机号不允许修改
		mLayoutPhoneNum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "手机号不允许修改！");
			}
		});

		// 修改密码
		mLayoutPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
						.title("修改密码")
						.customView(R.layout.dialog_change_psw, true)
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.build();

				dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						final EditText edt_old_password = (EditText) dialog.getCustomView().findViewById(R.id.edt_old_password);
						final EditText edt_new_password = (EditText) dialog.getCustomView().findViewById(R.id.edt_new_password);
						final EditText edt_new_password_again = (EditText) dialog.getCustomView().findViewById(R.id.edt_new_password_again);
						if (!edt_new_password_again.getText().toString().equals(edt_new_password.getText().toString())) {
							T.show(mContext, "两次新密码不同");
							return;
						}
						mAppAction.user_update_password(mApplication.getUser().getPhone_num(), edt_old_password.getText().toString(), edt_new_password.getText().toString(), new ActionCallbackListener<Void>() {
							@Override
							public void onSuccess(Void data) {
								T.show(mContext, "修改密码成功");
								dialog.dismiss();
							}

							@Override
							public void onFailure(String errorCode, String errorMessage) {
								T.show(mContext, errorMessage);
								edt_old_password.setText("");
								edt_new_password.setText("");
								edt_new_password_again.setText("");
							}
						});
					}
				});
				dialog.show();
			}
		});

		// 管理地址
		mLayoutAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, AddressActivity.class);
				intent.putExtra("flag", true);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
								mCircleImagePerson.setTag("http://o6wgg8qjk.bkt.clouddn.com/" + name);
								mAppAction.user_update_info(mApplication.getUser().getUser_id(), (String) mCircleImagePerson.getTag(), mApplication.getUser().getNickname(), new ActionCallbackListener<User>() {
									@Override
									public void onSuccess(User data) {
										T.show(mContext, "头像更新成功");
										Picasso.with(mContext).load(file)
												.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
												.centerCrop().into(mCircleImagePerson);
										mApplication.setUser(data);
										((MainActivity) getActivity()).updateInfo();
									}

									@Override
									public void onFailure(String errorCode, String errorMessage) {
										T.show(mContext, errorMessage);
									}
								});
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
