package net.xuwenhui.shitang.fragment.admin;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.User;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.UserForAdminAdapter;
import net.xuwenhui.shitang.fragment.BaseFragment;
import net.xuwenhui.shitang.util.T;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 管理员：已完成审核商户界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class MerchantFinishedFragment extends BaseFragment {

	@Bind(R.id.list_merchant_finished)
	RecyclerView mListMerchantFinished;

	UserForAdminAdapter mUserForAdminAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_merchant_finished;
	}

	@Override
	protected void initData() {
		// 初始化商户列表
		mListMerchantFinished.setLayoutManager(new LinearLayoutManager(mContext));
		mListMerchantFinished.setItemAnimator(new DefaultItemAnimator());
		mListMerchantFinished.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		mAppAction.user_query_merchant(new ActionCallbackListener<List<User>>() {
			@Override
			public void onSuccess(List<User> data) {
				List<User> list = new ArrayList<>();
				for (User user : data) {
					if (user.is_valid())
						list.add(user);
				}
				mUserForAdminAdapter = new UserForAdminAdapter(mContext, list, mAppAction);
				mListMerchantFinished.setAdapter(mUserForAdminAdapter);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
				// 测试数据
				List<User> data = new ArrayList<>();
				for (int i = 1; i <= 20; i++) {
					User user = new User(i, 3, "1599562914" + i % 10, "", "", true);
					data.add(user);
				}

				mUserForAdminAdapter = new UserForAdminAdapter(mContext, data, mAppAction);
				mListMerchantFinished.setAdapter(mUserForAdminAdapter);
			}
		});
	}

	@Override
	protected void initListener() {

	}

}
