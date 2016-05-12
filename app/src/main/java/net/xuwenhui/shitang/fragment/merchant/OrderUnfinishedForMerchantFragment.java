package net.xuwenhui.shitang.fragment.merchant;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.merchant.OrderManagementActivity;
import net.xuwenhui.shitang.adapter.OrderUnfinishedForMerchantAdapter;
import net.xuwenhui.shitang.fragment.BaseFragment;
import net.xuwenhui.shitang.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 商家：订单未完成界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class OrderUnfinishedForMerchantFragment extends BaseFragment {

	@Bind(R.id.list_order_unfinished)
	RecyclerView mListOrderUnfinished;

	OrderUnfinishedForMerchantAdapter mOrderUnfinishedForMerchantAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_order_unfinished;
	}

	@Override
	protected void initData() {
		// 初始化订单未完成列表
		mListOrderUnfinished.setLayoutManager(new LinearLayoutManager(mContext));
		mListOrderUnfinished.setItemAnimator(new DefaultItemAnimator());
		int shop_id = ((OrderManagementActivity) getActivity()).getShop_id();
		mAppAction.order_query_by_shop(shop_id, new ActionCallbackListener<List<Order>>() {
			@Override
			public void onSuccess(List<Order> data) {
				List<Order> list = new ArrayList<>();
				for (Order order : data) {
					if (order.getOrder_state_id() == 1 || order.getOrder_state_id() == 2 || order.getOrder_state_id() == 3)
						list.add(order);
				}
				mOrderUnfinishedForMerchantAdapter = new OrderUnfinishedForMerchantAdapter(mContext, list, mAppAction);
				mListOrderUnfinished.setAdapter(mOrderUnfinishedForMerchantAdapter);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
				// 测试数据
				List<Order> data = new ArrayList<>();
				for (int i = 1; i <= 5; i++) {
					OrderItem orderItem = new OrderItem(1, "测试菜品", 10.0f, i);
					List<OrderItem> orderItemList = new ArrayList<>();
					orderItemList.add(orderItem);
					Order order = new Order(i, 1, (i + 1) / 2, "", "测试订单" + i, "2016-05-02 15:20:45", false, 20.0f, "无", orderItemList);
					data.add(order);
				}

				mOrderUnfinishedForMerchantAdapter = new OrderUnfinishedForMerchantAdapter(mContext, data, mAppAction);
				mListOrderUnfinished.setAdapter(mOrderUnfinishedForMerchantAdapter);
			}
		});
	}

	@Override
	protected void initListener() {

	}

}
