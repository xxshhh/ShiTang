package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;

import net.xuwenhui.model.Evaluation;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.util.DateHandleUtil;
import net.xuwenhui.shitang.util.DensityUtils;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 评价适配器
 * <p/>
 * Created by xwh on 2016/4/27.
 */
public class EvaluationAdapter extends CommonAdapter<Evaluation> {

	public EvaluationAdapter(Context context, List<Evaluation> dataList) {
		super(context, dataList);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final ViewHolder viewHolder = (ViewHolder) holder;

		Evaluation evaluation = mDataList.get(position);
		viewHolder.mTvPhoneNum.setText(evaluation.getPhone_num().substring(0, 3) + "****" + evaluation.getPhone_num().substring(7, 11));
		viewHolder.mTvTime.setText(DateHandleUtil.convertToStandard(new Date(Long.parseLong(evaluation.getTime()))));
		viewHolder.mTvContent.setText(evaluation.getContent());
		if (evaluation.getImage_src().equals("")) {
			Picasso.with(mContext).load(R.mipmap.ic_launcher).into(viewHolder.mCircleImagePerson);
		} else {
			Picasso.with(mContext).load(evaluation.getImage_src())
					.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
					.centerCrop().into(viewHolder.mCircleImagePerson);
		}

		// 设置评分条外观
		Drawable fill = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star)
				.color(mContext.getResources().getColor(R.color.colorPrimary))
				.sizeDp(20);
		Drawable empty = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star_border)
				.color(mContext.getResources().getColor(R.color.colorPrimary))
				.sizeDp(20);
		Drawable half = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star_half)
				.color(mContext.getResources().getColor(R.color.colorPrimary))
				.sizeDp(20);
		viewHolder.mRatingBar.setStarFillDrawable(fill);
		viewHolder.mRatingBar.setStarEmptyDrawable(empty);
		viewHolder.mRatingBar.setStarHalfDrawable(half);
		viewHolder.mRatingBar.setStar(evaluation.getStar());
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.circle_image_person)
		CircleImageView mCircleImagePerson;
		@Bind(R.id.tv_time)
		TextView mTvTime;
		@Bind(R.id.tv_phone_num)
		TextView mTvPhoneNum;
		@Bind(R.id.ratingBar)
		RatingBar mRatingBar;
		@Bind(R.id.tv_content)
		TextView mTvContent;
		@Bind(R.id.card_evaluation)
		CardView mCardEvaluation;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
