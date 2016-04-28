package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单项适配器2（用于确认订单）
 * <p/>
 * Created by xwh on 2016/4/23.
 */
public class OrderItem2Adapter extends CommonAdapter<OrderItem> {

	public OrderItem2Adapter(Context context, List<OrderItem> dataList) {
		super(context, dataList);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_order_item2, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		ViewHolder viewHolder = (ViewHolder) holder;

		OrderItem orderItem = mDataList.get(position);
		viewHolder.mTvName.setText(orderItem.getDishes_name());
		viewHolder.mTvAmount.setText("x" + orderItem.getCount());
		viewHolder.mTvPrice.setText("￥" + orderItem.getPrice());
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.tv_price)
		TextView mTvPrice;
		@Bind(R.id.tv_amount)
		TextView mTvAmount;
		@Bind(R.id.tv_name)
		TextView mTvName;
		@Bind(R.id.card_order_item)
		CardView mCardOrderItem;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
