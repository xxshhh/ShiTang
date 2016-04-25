package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.xuwenhui.model.Dishes;
import net.xuwenhui.shitang.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 菜品适配器
 * <p/>
 * Created by xwh on 2016/4/21.
 */
public class DishesAdapter extends CommonAdapter<Dishes> {

	// 菜品数量
	private Map<Integer, Integer> mDishesCountMap;

	// 自定义点击事件
	private onMyClickListener mOnMyClickListener;

	public DishesAdapter(Context context, List<Dishes> dataList, Map<Integer, Integer> dishesCountMap) {
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
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_dishes, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		ViewHolder viewHolder = (ViewHolder) holder;

		Dishes dishes = mDataList.get(position);
		viewHolder.mTvName.setText(dishes.getName());
		viewHolder.mTvSell.setText("月售" + dishes.getSell() + "份");
		viewHolder.mTvPrice.setText("￥" + dishes.getPrice());

		if (mDishesCountMap.size() != 0 && mDishesCountMap.containsKey(dishes.getDishes_id())) {
			viewHolder.mTvAmount.setText(String.valueOf(mDishesCountMap.get(dishes.getDishes_id())));
			viewHolder.mTvAmount.setVisibility(View.VISIBLE);
			viewHolder.mImgSubtract.setVisibility(View.VISIBLE);
		} else {
			viewHolder.mTvAmount.setText(String.valueOf(0));
			viewHolder.mTvAmount.setVisibility(View.INVISIBLE);
			viewHolder.mImgSubtract.setVisibility(View.INVISIBLE);
		}

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
		@Bind(R.id.img_dishes)
		ImageView mImgDishes;
		@Bind(R.id.tv_name)
		TextView mTvName;
		@Bind(R.id.tv_sell)
		TextView mTvSell;
		@Bind(R.id.layout1)
		RelativeLayout mLayout1;
		@Bind(R.id.tv_price)
		TextView mTvPrice;
		@Bind(R.id.img_add)
		ImageView mImgAdd;
		@Bind(R.id.tv_amount)
		TextView mTvAmount;
		@Bind(R.id.img_subtract)
		ImageView mImgSubtract;
		@Bind(R.id.card_shop)
		CardView mCardShop;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
