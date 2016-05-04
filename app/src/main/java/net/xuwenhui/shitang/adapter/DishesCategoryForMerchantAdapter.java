package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.shitang.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 菜品类别适配器（用于商家）
 * <p/>
 * Created by xwh on 2016/4/21.
 */
public class DishesCategoryForMerchantAdapter extends CommonAdapter<DishesCategory> {

	// 自定义点击事件
	private onMyClickListener mOnMyClickListener;

	public DishesCategoryForMerchantAdapter(Context context, List<DishesCategory> dataList) {
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
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_category_for_merchant, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		final ViewHolder viewHolder = (ViewHolder) holder;

		DishesCategory dishesCategory = mDataList.get(position);
		viewHolder.mTvCategory.setText(dishesCategory.getCategory_desc());

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
		@Bind(R.id.img_flag)
		IconicsImageView mImgFlag;
		@Bind(R.id.tv_category)
		TextView mTvCategory;
		@Bind(R.id.img_edit)
		IconicsImageView mImgEdit;
		@Bind(R.id.img_delete)
		IconicsImageView mImgDelete;
		@Bind(R.id.card_dishes_category)
		CardView mCardDishesCategory;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
