package net.xuwenhui.shitang.fragment.merchant;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.merchant.OrderManagementActivity;
import net.xuwenhui.shitang.adapter.OrderFinishedForMerchantAdapter;
import net.xuwenhui.shitang.fragment.BaseFragment;
import net.xuwenhui.shitang.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 商家：订单已完成界面
 * <p/>
 * Created by xwh on 2016/5/4.
 */
public class OrderFinishedForMerchantFragment extends BaseFragment {

	@Bind(R.id.list_order_finished)
	RecyclerView mListOrderFinished;

	OrderFinishedForMerchantAdapter mOrderFinishedForMerchantAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_order_finished;
	}

	@Override
	protected void initData() {
		// 初始化订单已完成列表
		mListOrderFinished.setLayoutManager(new LinearLayoutManager(mContext));
		mListOrderFinished.setItemAnimator(new DefaultItemAnimator());
		int shop_id = ((OrderManagementActivity) getActivity()).getShop_id();
		mAppAction.order_query_by_shop(shop_id, new ActionCallbackListener<List<Order>>() {
			@Override
			public void onSuccess(List<Order> data) {
				List<Order> list = new ArrayList<>();
				for (Order order : data) {
					if (order.getOrder_state_id() == 4)
						list.add(order);
				}
				mOrderFinishedForMerchantAdapter = new OrderFinishedForMerchantAdapter(mContext, list, mAppAction);
				mListOrderFinished.setAdapter(mOrderFinishedForMerchantAdapter);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
				// 测试数据
				List<Order> data = new ArrayList<>();
				for (int i = 1; i <= 5; i++) {
					OrderItem orderItem = new OrderItem(1, "测试菜品", 20.0f, i);
					List<OrderItem> orderItemList = new ArrayList<>();
					orderItemList.add(orderItem);
					Order order = new Order(i, 1, 4, "", "测试订单" + i, "2016-05-02 15:20:45", false, 20.0f, "无", orderItemList);
					if (i == 1) {
						order.setEvaluate(true);
					}
					data.add(order);
				}

				mOrderFinishedForMerchantAdapter = new OrderFinishedForMerchantAdapter(mContext, data, mAppAction);
				mListOrderFinished.setAdapter(mOrderFinishedForMerchantAdapter);
			}
		});
	}

	@Override
	protected void initListener() {

	}

}
