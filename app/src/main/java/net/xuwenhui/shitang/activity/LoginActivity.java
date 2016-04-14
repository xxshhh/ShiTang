package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.util.ProgressDialogUtil;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 登录界面
 * <p/>
 * Created by xwh on 2016/3/25.
 */
public class LoginActivity extends BaseActivity {

	@Bind(R.id.edt_username)
	TextInputEditText mEdtUsername;
	@Bind(R.id.edt_password)
	TextInputEditText mEdtPassword;
	@Bind(R.id.btn_login)
	Button mBtnLogin;
	@Bind(R.id.tv_forgetPsw)
	TextView mTvForgetPsw;
	@Bind(R.id.tv_register)
	TextView mTvRegister;
	@Bind(R.id.layout_login)
	RelativeLayout mLayoutLogin;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	protected void initData() {

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
				String phoneNum = mEdtUsername.getText().toString();
				String password = mEdtPassword.getText().toString();
				mAppAction.login(phoneNum, password, new ActionCallbackListener<Void>() {
					@Override
					public void onSuccess(Void data) {
						ProgressDialogUtil.dismiss();
						T.show(mContext, "登录成功");
						Intent intent = new Intent(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void onFailure(String code, String message) {
						ProgressDialogUtil.dismiss();
						T.show(mContext, message);
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
						.items("普通用户", "商家用户")
						.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								T.show(mContext, text);
								Intent intent = new Intent(mContext, RegisterActivity.class);
								switch (which) {
									case 0:
										intent.putExtra("role_type", 2);
										break;
									case 1:
										intent.putExtra("role_type", 3);
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
	}
}
