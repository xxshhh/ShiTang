package net.xuwenhui.shitang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.model.Address;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.AddressActivity;
import net.xuwenhui.shitang.activity.AddressDetailActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 地址适配器
 * <p/>
 * Created by xwh on 2016/4/28.
 */
public class AddressAdapter extends CommonAdapter<Address> {

	// 当前选中地址位置
	private int current_location = -1;

	public AddressAdapter(Context context, List<Address> dataList) {
		super(context, dataList);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		final ViewHolder viewHolder = (ViewHolder) holder;

		// 设置tag
		viewHolder.mCardAddress.setTag(position);

		final Address address = mDataList.get(position);
		viewHolder.mTvAddress1.setText(address.getName() + " " + address.getSex() + " " + address.getPhone_num());
		viewHolder.mTvAddress2.setText(address.getAddress_desc());
		if (position == current_location) {
			viewHolder.mIconSelect.setVisibility(View.VISIBLE);
		} else {
			viewHolder.mIconSelect.setVisibility(View.INVISIBLE);
		}

		// 设置点击事件
		viewHolder.mCardAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int position = (int) view.getTag();
				current_location = position;
				notifyDataSetChanged();

				Intent returnIntent = new Intent();
				Bundle bundle = new Bundle();
				Address address = mDataList.get(current_location);
				bundle.putSerializable("Address", address);
				returnIntent.putExtras(bundle);
				((AddressActivity) mContext).setResult(Activity.RESULT_OK, returnIntent);
			}
		});
		viewHolder.mIconEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, AddressDetailActivity.class);
				intent.putExtra("Address", address);
				mContext.startActivity(intent);
			}
		});
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.icon_select)
		IconicsImageView mIconSelect;
		@Bind(R.id.icon_edit)
		IconicsImageView mIconEdit;
		@Bind(R.id.tv_address_1)
		TextView mTvAddress1;
		@Bind(R.id.tv_address_2)
		TextView mTvAddress2;
		@Bind(R.id.layout_address)
		RelativeLayout mLayoutAddress;
		@Bind(R.id.card_address)
		CardView mCardAddress;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
