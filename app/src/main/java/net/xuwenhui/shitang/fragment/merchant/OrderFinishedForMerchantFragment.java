package net.xuwenhui.shitang.fragment.merchant;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.OrderFinishedForMerchantAdapter;
import net.xuwenhui.shitang.fragment.BaseFragment;

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
		List<Order> data = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			OrderItem orderItem = new OrderItem(1, "测试菜品", 20.0f, i);
			List<OrderItem> orderItemList = new ArrayList<>();
			orderItemList.add(orderItem);
			Order order = new Order(i, 4, "", "测试订单" + i, "2016-05-02 15:20:45", false, 20.0f, "许文辉 先生 1899562914\n海虹公寓4栋", "无", orderItemList);
			if (i == 1) {
				order.setIs_evaluate(true);
			}
			data.add(order);
		}
		mOrderFinishedForMerchantAdapter = new OrderFinishedForMerchantAdapter(mContext, data);
		mListOrderFinished.setAdapter(mOrderFinishedForMerchantAdapter);
	}

	@Override
	protected void initListener() {

	}

}
