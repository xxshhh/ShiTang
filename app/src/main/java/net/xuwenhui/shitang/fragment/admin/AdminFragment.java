package net.xuwenhui.shitang.fragment.admin;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.admin.MerchantManagementActivity;
import net.xuwenhui.shitang.activity.admin.NoticeWallManagementActivity;
import net.xuwenhui.shitang.activity.admin.UserManagementActivity;
import net.xuwenhui.shitang.fragment.BaseFragment;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 管理员：管理员专属界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class AdminFragment extends BaseFragment {

	@Bind(R.id.layout_user)
	LinearLayout mLayoutUser;
	@Bind(R.id.layout_merchant)
	LinearLayout mLayoutMerchant;
	@Bind(R.id.layout_notice_wall)
	LinearLayout mLayoutNoticeWall;
	@Bind(R.id.layout_more)
	LinearLayout mLayoutMore;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_admin;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {
		// 用户管理
		mLayoutUser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, UserManagementActivity.class);
				startActivity(intent);
			}
		});

		// 商户管理
		mLayoutMerchant.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, MerchantManagementActivity.class);
				startActivity(intent);
			}
		});

		// 公告墙管理
		mLayoutNoticeWall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, NoticeWallManagementActivity.class);
				startActivity(intent);
			}
		});

		// 更多
		mLayoutMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "更多管理员特权,敬请期待...");
			}
		});
	}
}
