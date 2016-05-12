package net.xuwenhui.shitang.activity.merchant;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.BaseActivity;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 商家：公告管理界面
 * <p/>
 * Created by xwh on 2016/5/3.
 */
public class NoticeManagementActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.tv_notice)
	TextView mTvNotice;
	@Bind(R.id.btn_update_notice)
	Button mBtnUpdateNotice;

	int shop_id;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_notice_management;
	}

	@Override
	protected void initData() {
		// 获取intent
		shop_id = getIntent().getIntExtra("shop_id", 0);
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
		mAppAction.shop_query_notice(shop_id, new ActionCallbackListener<String>() {
			@Override
			public void onSuccess(String data) {
				if (!data.equals("")) {
					mTvNotice.setText(data);
				}
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
			}
		});

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
						.input("", mTvNotice.getText().toString(), false, new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
								mAppAction.shop_update_notice(shop_id, input.toString(), new ActionCallbackListener<Shop>() {
									@Override
									public void onSuccess(Shop data) {
										T.show(mContext, "公告更新成功");
										mTvNotice.setText(input.toString());
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
	}

}
