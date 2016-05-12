package net.xuwenhui.shitang.fragment;

import android.widget.LinearLayout;
import android.widget.TextView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.ShopDetailActivity;
import net.xuwenhui.shitang.util.T;

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

	Shop mShop;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_shop_info;
	}

	@Override
	protected void initData() {
		// 获取Activity的Shop对象
		mShop = ((ShopDetailActivity) getActivity()).getShop();
		if (!mShop.getAddress_desc().equals("")) {
			mTvAddressDesc.setText(mShop.getAddress_desc());
		}
		mAppAction.shop_query_phone(mShop.getShop_id(), new ActionCallbackListener<String>() {
			@Override
			public void onSuccess(String data) {
				if (!data.equals(""))
					mTvPhoneNum.setText(data);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
			}
		});
	}

	@Override
	protected void initListener() {

	}

}
