package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.shitang.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 菜品类别适配器
 * <p/>
 * Created by xwh on 2016/4/21.
 */
public class DishesCategoryAdapter extends CommonAdapter<DishesCategory> {

	// 当前定位位置
	private int current_location = 0;

	// 点击事件公开出去使用
	private View.OnClickListener mOnClickListener;

	public void setOnClickListener(View.OnClickListener onClickListener) {
		mOnClickListener = onClickListener;
	}

	public DishesCategoryAdapter(Context context, List<DishesCategory> dataList) {
		super(context, dataList);
	}

	/**
	 * 根据菜品类别id设置当前定位位置
	 *
	 * @param category_id
	 */
	public void setCurrentLocation(int category_id) {
		int position = 0;
		for (int i = 0; i < getItemCount(); i++) {
			if (category_id == mDataList.get(i).getCategory_id()) {
				position = i;
				break;
			}
		}
		current_location = position;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_category, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		final ViewHolder viewHolder = (ViewHolder) holder;

		// 设置tag
		viewHolder.mCardDishesCategory.setTag(position);

		DishesCategory dishesCategory = mDataList.get(position);
		viewHolder.mTvCategory.setText(dishesCategory.getCategory_desc());
		if (position == current_location) {
			viewHolder.mImgFlag.setVisibility(View.VISIBLE);
			viewHolder.mTvCategory.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
		} else {
			viewHolder.mImgFlag.setVisibility(View.INVISIBLE);
			viewHolder.mTvCategory.setTextColor(mContext.getResources().getColor(R.color.secondary_text));
		}

		// 设置点击事件
		if (mOnClickListener != null) {
			viewHolder.mCardDishesCategory.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int position = (int) view.getTag();
					current_location = position;
					notifyDataSetChanged();
					// 外面设置的点击事件
					mOnClickListener.onClick(view);
				}
			});
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.card_dishes_category)
		CardView mCardDishesCategory;
		@Bind(R.id.img_flag)
		ImageView mImgFlag;
		@Bind(R.id.tv_category)
		TextView mTvCategory;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
