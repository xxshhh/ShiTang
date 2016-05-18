package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.User;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.util.ProgressDialogUtil;
import net.xuwenhui.shitang.util.T;

import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录界面
 * <p>
 * Created by xwh on 2016/3/25.
 */
public class LoginActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.edt_phone_num)
	EditText mEdtPhoneNum;
	@Bind(R.id.edt_password)
	EditText mEdtPassword;
	@Bind(R.id.layout_login)
	LinearLayout mLayoutLogin;
	@Bind(R.id.btn_login)
	Button mBtnLogin;
	@Bind(R.id.tv_forgetPsw)
	TextView mTvForgetPsw;
	@Bind(R.id.tv_register)
	TextView mTvRegister;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("登录");
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
		/**
		 * 登录
		 */
		mBtnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProgressDialogUtil.show(mContext);
				String phone_num = mEdtPhoneNum.getText().toString();
				String password = mEdtPassword.getText().toString();
				mAppAction.user_login(phone_num, password, new ActionCallbackListener<User>() {
					@Override
					public void onSuccess(User data) {
						ProgressDialogUtil.dismiss();
						T.show(mContext, "登录成功");
						// 极光推送设置别名（使用用户id作为标识）
						JPushInterface.setAliasAndTags(mContext, String.valueOf(data.getUser_id()), null, new TagAliasCallback() {
							@Override
							public void gotResult(int code, String alias, Set<String> tags) {
								String logs;
								switch (code) {
									case 0:
										logs = "Set tag and alias success";
										Log.e(TAG, logs);
										break;

									default:
										logs = "Failed with errorCode = " + code;
										Log.e(TAG, logs);
								}
							}
						});

						// 存数据到SharedPreferences
						SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("user_phone_num", mEdtPhoneNum.getText().toString());
						editor.putString("user_password", mEdtPassword.getText().toString());
						editor.commit();

						// 设置全局用户并跳转主界面
						mApplication.setUser(data);
						Intent intent = new Intent(mContext, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}

					@Override
					public void onFailure(String errorCode, String errorMessage) {
						ProgressDialogUtil.dismiss();
						T.show(mContext, errorMessage);
						// 清空输入框
						mEdtPhoneNum.setText("");
						mEdtPassword.setText("");
					}
				});
			}
		});

		/**
		 * 跳转注册页面
		 */
		mTvRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("请选择注册用户类型")
						.items("普通用户", "商家用户(需要管理员审核)")
						.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								T.show(mContext, text);
								Intent intent = new Intent(mContext, RegisterActivity.class);
								switch (which) {
									case 0:
										intent.putExtra("role_id", 2);
										break;
									case 1:
										intent.putExtra("role_id", 3);
										break;
									default:
										return true;
								}
								startActivity(intent);
								return true;
							}
						})
						.positiveText(R.string.choose)
						.show();
			}
		});

		/**
		 * 跳转忘记密码界面
		 */
		mTvForgetPsw.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
				startActivity(intent);
			}
		});
	}

}
