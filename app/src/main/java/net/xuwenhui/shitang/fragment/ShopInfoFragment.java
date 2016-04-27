package net.xuwenhui.shitang.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.xuwenhui.shitang.R;

import butterknife.Bind;

/**
 * 商家基本信息界面
 * <p/>
 * Created by xwh on 2016/4/27.
 */
public class ShopInfoFragment extends BaseFragment {

	@Bind(R.id.tv_phone_num)
	TextView mTvPhoneNum;
	@Bind(R.id.layout_phone)
	LinearLayout mLayoutPhone;
	@Bind(R.id.tv_address_desc)
	TextView mTvAddressDesc;
	@Bind(R.id.layout_address)
	LinearLayout mLayoutAddress;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_shop_info;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

	}

}
