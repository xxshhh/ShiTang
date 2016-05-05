package net.xuwenhui.shitang.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.shitang.AppApplication;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.MainActivity;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 设置界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class SettingFragment extends BaseFragment {

	@Bind(R.id.layout_contact)
	RelativeLayout mLayoutContact;
	@Bind(R.id.layout_author)
	RelativeLayout mLayoutAuthor;
	@Bind(R.id.layout_about)
	RelativeLayout mLayoutAbout;
	@Bind(R.id.layout_logout)
	RelativeLayout mLayoutLogout;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_setting;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {
		// 跳转博客
		mLayoutAuthor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Uri uri = Uri.parse("http://xuwenhui.net");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		// 关于APP
		mLayoutAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("关于APP")
						.content("本食堂APP支持普通用户和商家用户两大角色，暂时只覆盖了武汉理工大学余区的食堂，欢迎使用！")
						.positiveText(R.string.agree)
						.show();
			}
		});

		// 注销
		mLayoutLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.content("确定要注销当前用户吗？")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								T.show(mContext, "注销用户");
								((AppApplication) getActivity().getApplication()).setUser(null);
								startActivity(new Intent(mContext, MainActivity.class));
								getActivity().finish();
							}
						})
						.show();
			}
		});
	}

}
