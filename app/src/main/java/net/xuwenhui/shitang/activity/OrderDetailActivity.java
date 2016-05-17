package net.xuwenhui.shitang.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Address;
import net.xuwenhui.model.Order;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 订单详情界面
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class OrderDetailActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.tv_order_id)
	TextView mTvOrderId;
	@Bind(R.id.tv_name)
	TextView mTvName;
	@Bind(R.id.tv_create_time)
	TextView mTvCreateTime;
	@Bind(R.id.tv_address_summary)
	TextView mTvAddressSummary;
	@Bind(R.id.tv_note)
	TextView mTvNote;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_order_detail;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("订单详情");
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		// 获取Intent
		Bundle bundle = getIntent().getExtras();
		Order order = (Order) bundle.getSerializable("Order");
		if (order != null) {
			mTvOrderId.setText(String.valueOf(order.getOrder_id()));
			mTvName.setText(order.getName());
			mTvCreateTime.setText(order.getCreate_time());
			mAppAction.address_query_by_id(order.getAddress_id(), new ActionCallbackListener<Address>() {
				@Override
				public void onSuccess(Address data) {
					String summary = data.getName() + " " + data.getSex() + " "
							+ data.getPhone_num() + '\n' + data.getAddress_desc();
					mTvAddressSummary.setText(summary);
				}

				@Override
				public void onFailure(String errorCode, String errorMessage) {
					T.show(mContext, errorMessage);
					mTvAddressSummary.setText("");
				}
			});
			mTvNote.setText(order.getNote());
		}
	}

	@Override
	protected void initListener() {

	}

}
