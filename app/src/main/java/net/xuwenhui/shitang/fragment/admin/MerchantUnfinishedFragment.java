package net.xuwenhui.shitang.fragment.admin;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.model.User;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.UserForAdminAdapter;
import net.xuwenhui.shitang.fragment.BaseFragment;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 管理员：未完成审核商户界面
 * <p/>
 * Created by xwh on 2016/5/5.
 */
public class MerchantUnfinishedFragment extends BaseFragment {

	@Bind(R.id.list_merchant_unfinished)
	RecyclerView mListMerchantUnfinished;

	UserForAdminAdapter mUserForAdminAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_merchant_unfinished;
	}

	@Override
	protected void initData() {
		// 初始化商户列表
		mListMerchantUnfinished.setLayoutManager(new LinearLayoutManager(mContext));
		mListMerchantUnfinished.setItemAnimator(new DefaultItemAnimator());
		mListMerchantUnfinished.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<User> data = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			User user = new User(i, 3, "1389562914" + i % 10, "", "", "", false);
			data.add(user);
		}
		mUserForAdminAdapter = new UserForAdminAdapter(mContext, data);
		mListMerchantUnfinished.setAdapter(mUserForAdminAdapter);
	}

	@Override
	protected void initListener() {

	}

}
