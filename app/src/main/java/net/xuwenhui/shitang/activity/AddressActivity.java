package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Address;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.AddressAdapter;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 收货地址
 * <p>
 * Created by xwh on 2016/4/28.
 */
public class AddressActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.layout_add_address)
	LinearLayout mLayoutAddAddress;
	@Bind(R.id.list_address)
	RecyclerView mListAddress;

	AddressAdapter mAddressAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_address;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("管理地址");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		// 初始化地址列表
		initAddressList();
	}

	@Override
	protected void initListener() {
		// 新增地址
		mLayoutAddAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, AddressDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化地址列表
	 */
	private void initAddressList() {
		mListAddress.setLayoutManager(new LinearLayoutManager(mContext));
		mListAddress.setItemAnimator(new DefaultItemAnimator());
		mListAddress.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		mAppAction.address_query(mApplication.getUser().getUser_id(), new ActionCallbackListener<List<Address>>() {
			@Override
			public void onSuccess(List<Address> data) {
				mAddressAdapter = new AddressAdapter(mContext, data);
				mListAddress.setAdapter(mAddressAdapter);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				// 测试数据
				List<Address> data = new ArrayList<>();
				for (int i = 1; i <= 3; i++) {
					Address address = new Address(i, "许文辉" + i, "先生", "18995629148", "海4", "");
					data.add(address);
				}

				mAddressAdapter = new AddressAdapter(mContext, data);
				mListAddress.setAdapter(mAddressAdapter);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 初始化地址列表
		initAddressList();
	}
}
