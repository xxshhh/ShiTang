package net.xuwenhui.shitang.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

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
	@Bind(R.id.tv_display_name)
	TextView mTvDisplayName;
	@Bind(R.id.layout)
	LinearLayout mLayout;
	@Bind(R.id.list_shop)
	RecyclerView mListShop;
	@Bind(R.id.fab_a)
	FloatingActionButton mFabA;
	@Bind(R.id.fab_b)
	FloatingActionButton mFabB;
	@Bind(R.id.fab_c)
	FloatingActionButton mFabC;
	@Bind(R.id.fab_menu)
	FloatingActionsMenu mFabMenu;

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
		// 初始化FAB
		initFAB();
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
			Shop shop = new Shop(i, "测试店" + i, "", "六食堂", i + 20, (float) (i / 5.0 + 3.0), 200 * i);
			data.add(shop);
		}
		ShopAdapter adapter = new ShopAdapter(mContext, data);
		mListShop.setAdapter(adapter);
	}

	/**
	 * 初始化FAB
	 */
	private void initFAB() {
		// FabA：选择食堂
		Drawable drawableA = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_location_city)
				.color(mContext.getResources().getColor(R.color.colorAccent))
				.sizeDp(30);
		mFabA.setIconDrawable(drawableA);
		mFabA.setTitle("选择食堂");
		mFabA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "选择食堂");
				new MaterialDialog.Builder(mContext)
						.items("全部", "1食堂", "2食堂", "3食堂", "6食堂", "7食堂")
						.itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								T.show(mContext, text);
								switch (which) {
									case 0:
										mTvDisplayName.setText("全部商家");
										mFabMenu.collapse();
										break;
									case 1:
										mTvDisplayName.setText("1食堂商家");
										mFabMenu.collapse();
										break;
									case 2:
										mTvDisplayName.setText("2食堂商家");
										mFabMenu.collapse();
										break;
									case 3:
										mTvDisplayName.setText("3食堂商家");
										mFabMenu.collapse();
										break;
									case 4:
										mTvDisplayName.setText("6食堂商家");
										mFabMenu.collapse();
										break;
									case 5:
										mTvDisplayName.setText("7食堂商家");
										mFabMenu.collapse();
										break;
									default:
										return true;
								}
								return true;
							}
						})
						.positiveText(R.string.choose)
						.show();
			}
		});

		// FabB：销量最高
		Drawable drawableB = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_whatshot)
				.color(mContext.getResources().getColor(R.color.colorAccent))
				.sizeDp(30);
		mFabB.setIconDrawable(drawableB);
		mFabB.setTitle("销量最高");
		mFabB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "销量最高");
				mTvDisplayName.setText("销量最高商家");
				mFabMenu.collapse();
			}
		});

		// FabC：评分最高
		Drawable drawableC = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star_border)
				.color(mContext.getResources().getColor(R.color.colorAccent))
				.sizeDp(30);
		mFabC.setIconDrawable(drawableC);
		mFabC.setTitle("评分最高");
		mFabC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				T.show(mContext, "评分最高");
				mTvDisplayName.setText("评分最高商家");
				mFabMenu.collapse();
			}
		});
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
