package net.xuwenhui.shitang.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.OrderUnfinishedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 订单未完成界面
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class OrderUnfinishedFragment extends BaseFragment {

	@Bind(R.id.list_order_unfinished)
	RecyclerView mListOrderUnfinished;

	OrderUnfinishedAdapter mOrderUnfinishedAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_order_unfinished;
	}

	@Override
	protected void initData() {
		// 初始化订单未完成列表
		mListOrderUnfinished.setLayoutManager(new LinearLayoutManager(mContext));
		mListOrderUnfinished.setItemAnimator(new DefaultItemAnimator());
		mAppAction.order_query_by_user(mApplication.getUser().getUser_id(), new ActionCallbackListener<List<Order>>() {
			@Override
			public void onSuccess(List<Order> data) {
				List<Order> list = new ArrayList<>();
				for (Order order : data) {
					if (order.getOrder_state_id() == 1 || order.getOrder_state_id() == 2 || order.getOrder_state_id() == 3)
						list.add(order);
				}
				mOrderUnfinishedAdapter = new OrderUnfinishedAdapter(mContext, list, mAppAction);
				mListOrderUnfinished.setAdapter(mOrderUnfinishedAdapter);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				// 测试数据
				List<Order> data = new ArrayList<>();
				for (int i = 1; i <= 5; i++) {
					OrderItem orderItem = new OrderItem(1, "测试菜品", 10.0f, i);
					List<OrderItem> orderItemList = new ArrayList<>();
					orderItemList.add(orderItem);
					Order order = new Order(i, 1, (i + 1) / 2, "", "测试订单" + i, "2016-05-02 15:20:45", false, 20.0f, "无", orderItemList);
					data.add(order);
				}

				mOrderUnfinishedAdapter = new OrderUnfinishedAdapter(mContext, data, mAppAction);
				mListOrderUnfinished.setAdapter(mOrderUnfinishedAdapter);
			}
		});
	}

	@Override
	protected void initListener() {

	}

}
