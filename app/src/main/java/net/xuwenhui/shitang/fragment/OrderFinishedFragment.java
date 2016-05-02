package net.xuwenhui.shitang.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.OrderFinishedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 订单已完成界面
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class OrderFinishedFragment extends BaseFragment {

	@Bind(R.id.list_order_finished)
	RecyclerView mListOrderFinished;

	OrderFinishedAdapter mOrderFinishedAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_order_finished;
	}

	@Override
	protected void initData() {
		// 初始化订单已完成列表
		mListOrderFinished.setLayoutManager(new LinearLayoutManager(mContext));
		mListOrderFinished.setItemAnimator(new DefaultItemAnimator());
		List<Order> data = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			OrderItem orderItem = new OrderItem(1, "测试菜品", 20.0f, i);
			List<OrderItem> orderItemList = new ArrayList<>();
			orderItemList.add(orderItem);
			Order order = new Order(i, 4, "", "测试订单" + i, "2016-05-02 15:20:45", false, 20.0f, "许文辉 先生 1899562914\n海虹公寓4栋", "无", orderItemList);
			data.add(order);
		}
		mOrderFinishedAdapter = new OrderFinishedAdapter(mContext, data);
		mListOrderFinished.setAdapter(mOrderFinishedAdapter);
	}

	@Override
	protected void initListener() {

	}

}
