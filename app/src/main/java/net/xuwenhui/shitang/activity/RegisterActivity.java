package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.shitang.R;
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
	@Bind(R.id.edt_username)
	EditText mEdtPhoneNum;
	@Bind(R.id.btn_sendSMSCode)
	Button mBtnSendSMSCode;
	@Bind(R.id.edt_SMSCode)
	EditText mEdtSMSCode;
	@Bind(R.id.edt_password)
	EditText mEdtPassword;
	@Bind(R.id.btn_register)
	Button mBtnRegister;

	String phone_num = "";
	String code = "";
	String password = "";
	int role_id = 2;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_register;
	}

	@Override
	protected void initData() {
		// 获取intent
		Intent intent = getIntent();
		role_id = intent.getIntExtra("role_type", 2);
		// 设置toolbar
		if (role_id == 3) {
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
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				// 回调完成
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 获取验证码成功
					if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						T.show(mContext, "获取验证码成功");
					}
					// 提交验证码成功
					else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						// 注册
						mAppAction.register(phone_num, password, role_id, new ActionCallbackListener<Void>() {
							@Override
							public void onSuccess(Void data) {
								T.show(mContext, "注册成功");
								finish();
							}

							@Override
							public void onFailure(String code, String message) {
								Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
							}
						});
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
		 * 获取验证码
		 */
		mBtnSendSMSCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				phone_num = mEdtPhoneNum.getText().toString();
				// 请求获取短信验证码
				SMSSDK.getVerificationCode("+86", phone_num);
			}
		});

		/**
		 * 注册
		 */
		mBtnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				code = mEdtSMSCode.getText().toString();
				password = mEdtPassword.getText().toString();
				// 提交短信验证码
				SMSSDK.submitVerificationCode("+86", phone_num, code);
			}
		});
	}

	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}
}
