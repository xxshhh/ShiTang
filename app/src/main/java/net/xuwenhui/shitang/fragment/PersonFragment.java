package net.xuwenhui.shitang.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.AddressActivity;
import net.xuwenhui.shitang.util.FileHandleUtil;
import net.xuwenhui.shitang.util.T;

import java.io.File;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人信息界面
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class PersonFragment extends BaseFragment {

	@Bind(R.id.circle_image_person)
	CircleImageView mCircleImagePerson;
	@Bind(R.id.layout_image)
	RelativeLayout mLayoutImage;
	@Bind(R.id.layout_nickname)
	RelativeLayout mLayoutNickname;
	@Bind(R.id.tv_nickname)
	TextView mTvNickname;
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
						.input("", "小学生辉辉辉", false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mTvNickname.setText(input);
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
						T.show(mContext, "修改密码成功");
						dialog.dismiss();
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
					// 上传头像到服务器
					mCircleImagePerson.setImageBitmap(bitmap);
				}
			}
		}
	}
}
