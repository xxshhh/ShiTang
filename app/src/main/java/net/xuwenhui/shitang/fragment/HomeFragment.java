package net.xuwenhui.shitang.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.ShopAdapter;
import net.xuwenhui.shitang.util.T;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 首页界面
 * <p/>
 * Created by xwh on 2016/4/14.
 */
public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener {

	@Bind(R.id.slider_show)
	SliderLayout mSliderShow;
	@Bind(R.id.fab_cart)
	FloatingActionButton mFabCart;
	@Bind(R.id.list_shop)
	RecyclerView mListShop;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_home;
	}

	@Override
	protected void initData() {
		// 初始化图片展示墙
		initImageDisplay();
		// 初始化商店列表
		initShopList();
	}

	@Override
	protected void initListener() {
	}

	/**
	 * 初始化图片展示墙
	 */
	private void initImageDisplay() {
		Map<String, String> url_maps = new LinkedHashMap<>();
		url_maps.put("必胜客下午茶", "http://7xjda2.com1.z0.glb.clouddn.com/t1.jpg");
		url_maps.put("KFC宅急送", "http://7xjda2.com1.z0.glb.clouddn.com/t2.jpg");
		url_maps.put("八方缘食馆", "http://7xjda2.com1.z0.glb.clouddn.com/t3.jpg");
		url_maps.put("小何屋外卖店", "http://7xjda2.com1.z0.glb.clouddn.com/t4.jpg");

		for (String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(mContext);
			// initialize a SliderLayout
			textSliderView
					.description(name)
					.image(url_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle()
					.putString("extra", name);

			mSliderShow.addSlider(textSliderView);
		}
		mSliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
		mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mSliderShow.setCustomAnimation(new DescriptionAnimation());
		mSliderShow.setDuration(6000);
	}

	/**
	 * 初始化商店列表
	 */
	public void initShopList() {
		// 设置LinearLayoutManager
		mListShop.setLayoutManager(new LinearLayoutManager(mContext));
		// 设置ItemAnimator
		mListShop.setItemAnimator(new DefaultItemAnimator());
		// 设置分隔线
		mListShop.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		// 设置固定大小
		mListShop.setHasFixedSize(true);

		final List<Shop> data = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			Shop shop = new Shop(i, "测试店" + i, "", "六食堂", i + 20, (float) (i / 5.0 + 2.0), 200 * i);
			data.add(shop);
		}
		ShopAdapter adapter = new ShopAdapter(mContext, data);
		mListShop.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		mSliderShow.startAutoCycle(6000, 6000, true);
		super.onResume();
	}

	@Override
	public void onStop() {
		mSliderShow.stopAutoCycle();
		super.onStop();
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
		T.show(mContext, slider.getDescription());
	}
}
