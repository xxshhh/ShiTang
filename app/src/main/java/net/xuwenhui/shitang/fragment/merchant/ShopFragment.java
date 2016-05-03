package net.xuwenhui.shitang.fragment.merchant;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.merchant.CreateUpdateShopActivity;
import net.xuwenhui.shitang.activity.merchant.ShopFunctionActivity;
import net.xuwenhui.shitang.fragment.BaseFragment;

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

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_shop;
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initListener() {
		// 判断是否有店铺
		boolean flag = true;
		if (flag) {
			Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
			mImgShop.setImageDrawable(drawable);
			mTvTips.setText("这里是店铺名");
			mBtnShop.setText("进入店铺");
			mBtnShop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(mContext, ShopFunctionActivity.class);
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

}
