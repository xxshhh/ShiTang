package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.core.AppAction;
import net.xuwenhui.model.Order;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.OrderDetailActivity;
import net.xuwenhui.shitang.util.DensityUtils;
import net.xuwenhui.shitang.util.T;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 订单未完成适配器
 * <p/>
 * Created by xwh on 2016/5/2.
 */
public class OrderUnfinishedAdapter extends CommonAdapter<Order> {

	AppAction mAppAction;

	public OrderUnfinishedAdapter(Context context, List<Order> dataList, AppAction appAction) {
		super(context, dataList);
		mAppAction = appAction;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_order_unfinished, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		final ViewHolder viewHolder = (ViewHolder) holder;

		final Order order = mDataList.get(position);
		viewHolder.mTvName.setText(order.getName());
		viewHolder.mTvTime.setText(order.getCreate_time());
		viewHolder.mTvTotalPrice.setText("总价 ￥" + order.getTotal_price());

		// 初始化订单项列表
		viewHolder.mListOrderItem.setLayoutManager(new LinearLayoutManager(mContext));
		viewHolder.mListOrderItem.setItemAnimator(new DefaultItemAnimator());
		viewHolder.mListOrderItem.setAdapter(new OrderItem2Adapter(mContext, order.getOrderItemList()));
		if (order.getImage_src().equals("")) {
			Picasso.with(mContext).load(R.mipmap.ic_launcher).into(viewHolder.mCircleImageShop);
		} else {
			Picasso.with(mContext).load(order.getImage_src())
					.resize(DensityUtils.dp2px(mContext, 48), DensityUtils.dp2px(mContext, 48))
					.centerCrop().into(viewHolder.mCircleImageShop);
		}

		// 设置点击事件
		// 取消订单
		viewHolder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.content("确定取消订单?")
						.positiveText(R.string.agree)
						.negativeText(R.string.disagree)
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								mAppAction.order_update_state(order.getOrder_id(), 5, new ActionCallbackListener<Order>() {
									@Override
									public void onSuccess(Order data) {
										notifyItemRemoved(position);
										mDataList.remove(position);
										notifyItemRangeChanged(position, getItemCount());
									}

									@Override
									public void onFailure(String errorCode, String errorMessage) {
										T.show(mContext, errorMessage);
									}
								});
							}
						}).show();
			}
		});
		// 付款
		viewHolder.mBtnPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.content("确定付款?")
						.positiveText(R.string.agree)
						.negativeText(R.string.disagree)
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								mAppAction.order_update_state(order.getOrder_id(), 2, new ActionCallbackListener<Order>() {
									@Override
									public void onSuccess(Order data) {
										order.setOrder_state_id(2);
										notifyItemChanged(position);
									}

									@Override
									public void onFailure(String errorCode, String errorMessage) {
										T.show(mContext, errorMessage);
									}
								});

							}
						}).show();
			}
		});
		// 查看订单详情界面
		viewHolder.mLayoutMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, OrderDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Order", order);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});

		// 根据订单状态设置外观
		switch (order.getOrder_state_id()) {
			case 1:// 用户已提交
				viewHolder.mBtnCancel.setVisibility(View.VISIBLE);
				viewHolder.mBtnCancel.setText("取消订单");
				viewHolder.mBtnCancel.setClickable(true);

				viewHolder.mBtnPay.setVisibility(View.VISIBLE);
				viewHolder.mBtnPay.setText("付款");
				viewHolder.mBtnPay.setClickable(true);
				break;
			case 2:// 用户已支付
				viewHolder.mBtnCancel.setVisibility(View.INVISIBLE);

				viewHolder.mBtnPay.setText("等待商家确认...");
				viewHolder.mBtnPay.setBackgroundColor(Color.BLACK);
				viewHolder.mBtnPay.setClickable(false);
				break;
			case 3:// 商家已确认
				viewHolder.mBtnCancel.setVisibility(View.INVISIBLE);

				viewHolder.mBtnPay.setText("等待商家配送...");
				viewHolder.mBtnPay.setBackgroundColor(Color.BLACK);
				viewHolder.mBtnPay.setClickable(false);
				break;
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.tv_time)
		TextView mTvTime;
		@Bind(R.id.layout_more)
		RelativeLayout mLayoutMore;
		@Bind(R.id.circle_image_shop)
		CircleImageView mCircleImageShop;
		@Bind(R.id.tv_name)
		TextView mTvName;
		@Bind(R.id.layout1)
		RelativeLayout mLayout1;
		@Bind(R.id.list_order_item)
		RecyclerView mListOrderItem;
		@Bind(R.id.tv_total_price)
		TextView mTvTotalPrice;
		@Bind(R.id.layout2)
		RelativeLayout mLayout2;
		@Bind(R.id.btn_cancel)
		Button mBtnCancel;
		@Bind(R.id.btn_pay)
		Button mBtnPay;
		@Bind(R.id.card_order_unfinished)
		CardView mCardOrderUnfinished;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}

}
