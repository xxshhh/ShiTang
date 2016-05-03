package net.xuwenhui.shitang.activity.merchant;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;

import butterknife.Bind;

/**
 * 商家：店铺公告管理界面
 * <p/>
 * Created by xwh on 2016/5/3.
 */
public class ShopNoticeActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.tv_notice)
	TextView mTvNotice;
	@Bind(R.id.btn_update_notice)
	Button mBtnUpdateNotice;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_shop_notice;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("公告管理");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 设置公告
		mTvNotice.setText("这是一段很长的公告！！！这是一段很长的公告！！！这是一段很长的公告！！！");
	}

	@Override
	protected void initListener() {
		// 更新公告
		mBtnUpdateNotice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.title("请输入新公告内容")
						.negativeText(R.string.disagree)
						.positiveText(R.string.agree)
						.input("", "", false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								mTvNotice.setText(input);
							}
						}).show();
			}
		});
	}

}
