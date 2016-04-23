package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单项适配器
 * <p/>
 * Created by xwh on 2016/4/23.
 */
public class OrderItemAdapter extends CommonAdapter<OrderItem> {

	// 菜品数量
	private Map<Integer, Integer> mDishesCountMap;

	// 自定义点击事件
	private onMyClickListener mOnMyClickListener;

	public OrderItemAdapter(Context context, List<OrderItem> dataList, Map<Integer, Integer> dishesCountMap) {
		super(context, dataList);
		mDishesCountMap = dishesCountMap;
	}

	public void setOnMyClickListener(onMyClickListener onMyClickListener) {
		mOnMyClickListener = onMyClickListener;
	}

	public interface onMyClickListener {
		void onAddClickListener(View view, int position);

		void onSubtractClickListener(View view, int position);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_order_item, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		ViewHolder viewHolder = (ViewHolder) holder;

		OrderItem orderItem = mDataList.get(position);
		viewHolder.mTvName.setText(orderItem.getDishes_name());
		viewHolder.mTvAmount.setText(String.valueOf(orderItem.getCount()));
		viewHolder.mTvTotalPrice.setText(String.valueOf(orderItem.getPrice() * orderItem.getCount()));

		// 设置点击事件
		if (mOnMyClickListener != null) {
			viewHolder.mImgAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mOnMyClickListener.onAddClickListener(view, position);
				}
			});
			viewHolder.mImgSubtract.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mOnMyClickListener.onSubtractClickListener(view, position);
				}
			});
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.img_add)
		ImageView mImgAdd;
		@Bind(R.id.tv_amount)
		TextView mTvAmount;
		@Bind(R.id.img_subtract)
		ImageView mImgSubtract;
		@Bind(R.id.tv_total_price)
		TextView mTvTotalPrice;
		@Bind(R.id.tv_name)
		TextView mTvName;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
