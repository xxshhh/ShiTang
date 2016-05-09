package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.util.ProgressDialogUtil;
import net.xuwenhui.shitang.util.T;

import org.json.JSONObject;

import butterknife.Bind;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册界面
 * <p/>
 * Created by xwh on 2016/3/25.
 */
public class RegisterActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.tv_step1)
	TextView mTvStep1;
	@Bind(R.id.tv_step2)
	TextView mTvStep2;
	@Bind(R.id.tv_step3)
	TextView mTvStep3;
	@Bind(R.id.layout_step)
	LinearLayout mLayoutStep;
	@Bind(R.id.img_input)
	IconicsImageView mImgInput;
	@Bind(R.id.edt_input)
	EditText mEdtInput;
	@Bind(R.id.layout_input)
	LinearLayout mLayoutInput;
	@Bind(R.id.btn_handle)
	Button mBtnHandle;

	private int mStep = 1; // 当前注册步骤

	private String mPhoneNum = "";
	private String mCode = "";
	private String mPassword = "";
	private int mRoleId = 2;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_register;
	}

	@Override
	protected void initData() {
		// 获取intent
		Intent intent = getIntent();
		mRoleId = intent.getIntExtra("role_id", 2);
		// 设置toolbar
		if (mRoleId == 3) {
			mToolbar.setTitle("商家用户注册");
		} else {
			mToolbar.setTitle("普通用户注册");
		}
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 用于通信的Handle
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ProgressDialogUtil.dismiss();
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				// 回调完成
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 获取验证码成功
					if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						T.show(mContext, "获取验证码成功");
						mStep = 2;
						changeStyleByStep();
					}
					// 提交验证码成功
					else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						T.show(mContext, "验证码正确");
						mStep = 3;
						changeStyleByStep();
					}
				} else {
					try {
						Throwable throwable = (Throwable) data;
						throwable.printStackTrace();
						JSONObject object = new JSONObject(throwable.getMessage());
						String detail = object.optString("detail");//错误描述
						int status = object.optInt("status");//错误代码
						if (status > 0 && !TextUtils.isEmpty(detail)) {
							T.show(mContext, detail);
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		// 初始化SDK
		SMSSDK.initSDK(mContext, "11601d90b3500", "d9319fafeaa575f43c184948cb075630");
		EventHandler eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {
				// 将数据传给handler处理
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调接口
		SMSSDK.registerEventHandler(eh);
	}

	@Override
	protected void initListener() {
		/**
		 * 处理点击事件
		 */
		mBtnHandle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProgressDialogUtil.show(mContext);
				switch (mStep) {
					case 1:
						mPhoneNum = mEdtInput.getText().toString();
						// 请求获取短信验证码
						SMSSDK.getVerificationCode("+86", mPhoneNum);
						break;
					case 2:
						mCode = mEdtInput.getText().toString();
						// 提交短信验证码
						SMSSDK.submitVerificationCode("+86", mPhoneNum, mCode);
						break;
					case 3:
						// 注册
						mPassword = mEdtInput.getText().toString();
						mAppAction.user_register(mPhoneNum, mPassword, mRoleId, new ActionCallbackListener<Void>() {
							@Override
							public void onSuccess(Void data) {
								ProgressDialogUtil.dismiss();
								T.show(mContext, "注册成功");
								finish();
							}

							@Override
							public void onFailure(String code, String message) {
								ProgressDialogUtil.dismiss();
								Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
							}
						});
						break;
					default:
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}

	/**
	 * 根据当前步骤改变布局样式
	 */
	private void changeStyleByStep() {
		int colorPrimary = getResources().getColor(R.color.colorPrimary);
		int secondary_text = getResources().getColor(R.color.secondary_text);

		switch (mStep) {
			case 2:
				mTvStep1.setTextColor(secondary_text);
				mTvStep2.setTextColor(colorPrimary);
				mTvStep3.setTextColor(secondary_text);

				IconicsDrawable icon2 = new IconicsDrawable(mContext)
						.icon(GoogleMaterial.Icon.gmd_cloud_upload)
						.color(colorPrimary)
						.sizeDp(20);
				mImgInput.setIcon(icon2);
				mEdtInput.setText(null);
				mEdtInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
				mEdtInput.setHint("请输入您的验证码");
				mBtnHandle.setText("核对验证码");
				break;
			case 3:
				mTvStep1.setTextColor(secondary_text);
				mTvStep2.setTextColor(secondary_text);
				mTvStep3.setTextColor(colorPrimary);

				IconicsDrawable icon3 = new IconicsDrawable(mContext)
						.icon(GoogleMaterial.Icon.gmd_lock_open)
						.color(colorPrimary)
						.sizeDp(20);
				mImgInput.setIcon(icon3);
				mEdtInput.setText(null);
				mEdtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				mEdtInput.setHint("请输入您的密码");
				mBtnHandle.setText("注册");
				break;
			default:
		}
	}
}
