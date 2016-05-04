package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.model.Dishes;
import net.xuwenhui.shitang.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商家：菜品适配器
 * <p/>
 * Created by xwh on 2016/4/21.
 */
public class DishesForMerchantAdapter extends CommonAdapter<Dishes> {

	// 自定义点击事件
	private onMyClickListener mOnMyClickListener;

	public DishesForMerchantAdapter(Context context, List<Dishes> dataList) {
		super(context, dataList);
	}

	public void setOnMyClickListener(onMyClickListener onMyClickListener) {
		mOnMyClickListener = onMyClickListener;
	}

	public interface onMyClickListener {
		void onEditClickListener(View view, int position);

		void onDeleteClickListener(View view, int position);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_for_merchant, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		ViewHolder viewHolder = (ViewHolder) holder;

		Dishes dishes = mDataList.get(position);
		viewHolder.mTvName.setText(dishes.getName());
		viewHolder.mTvPrice.setText("￥" + dishes.getPrice());

		// 设置点击事件
		if (mOnMyClickListener != null) {
			viewHolder.mImgEdit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mOnMyClickListener.onEditClickListener(view, position);
				}
			});
			viewHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mOnMyClickListener.onDeleteClickListener(view, position);
				}
			});
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.img_dishes)
		ImageView mImgDishes;
		@Bind(R.id.img_edit)
		IconicsImageView mImgEdit;
		@Bind(R.id.img_delete)
		IconicsImageView mImgDelete;
		@Bind(R.id.layout_handle)
		LinearLayout mLayoutHandle;
		@Bind(R.id.tv_name)
		TextView mTvName;
		@Bind(R.id.tv_price)
		TextView mTvPrice;
		@Bind(R.id.card_dishes)
		CardView mCardDishes;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
