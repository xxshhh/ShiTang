package net.xuwenhui.shitang.fragment.merchant;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.merchant.CreateUpdateShopActivity;
import net.xuwenhui.shitang.activity.merchant.ShopFunctionActivity;
import net.xuwenhui.shitang.fragment.BaseFragment;
import net.xuwenhui.shitang.util.DensityUtils;
import net.xuwenhui.shitang.util.T;

import butterknife.Bind;

/**
 * 商家：我的店铺界面
 * <p/>
 * Created by xwh on 2016/5/3.
 */
public class ShopFragment extends BaseFragment {

	@Bind(R.id.img_shop)
	ImageView mImgShop;
	@Bind(R.id.tv_tips)
	TextView mTvTips;
	@Bind(R.id.btn_shop)
	Button mBtnShop;

	Shop mShop;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_shop;
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initListener() {
		// 查看商户是否有店铺
		mAppAction.shop_query_by_user(mApplication.getUser().getUser_id(), new ActionCallbackListener<Shop>() {
			@Override
			public void onSuccess(Shop data) {
				if (data != null) {
					mShop = data;
					if (mShop.getImage_src().equals("")) {
						Picasso.with(mContext).load(R.mipmap.ic_launcher).into(mImgShop);
					} else {
						Picasso.with(mContext).load(mShop.getImage_src())
								.resize(DensityUtils.dp2px(mContext, 96), DensityUtils.dp2px(mContext, 96))
								.centerCrop().into(mImgShop);
					}
					mTvTips.setText(mShop.getName());
					mBtnShop.setText("进入店铺");
					mBtnShop.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							Intent intent = new Intent(mContext, ShopFunctionActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("Shop", mShop);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				} else {
					Drawable drawable = new IconicsDrawable(mContext)
							.icon(GoogleMaterial.Icon.gmd_local_dining)
							.color(getResources().getColor(R.color.colorAccent))
							.sizeDp(96);
					mImgShop.setImageDrawable(drawable);
					mTvTips.setText("您还没有店铺呢！");
					mBtnShop.setText("创建店铺");
					mBtnShop.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							Intent intent = new Intent(mContext, CreateUpdateShopActivity.class);
							startActivity(intent);
						}
					});
				}
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
			}
		});
	}

}
