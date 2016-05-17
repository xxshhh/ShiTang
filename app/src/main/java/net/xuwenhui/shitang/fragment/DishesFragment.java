package net.xuwenhui.shitang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Dishes;
import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.model.Shop;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.activity.ConfirmOrderActivity;
import net.xuwenhui.shitang.activity.NeedLoginRegisterActivity;
import net.xuwenhui.shitang.activity.ShopDetailActivity;
import net.xuwenhui.shitang.adapter.DishesAdapter;
import net.xuwenhui.shitang.adapter.DishesCategoryAdapter;
import net.xuwenhui.shitang.adapter.OrderItem1Adapter;
import net.xuwenhui.shitang.util.T;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 点菜界面
 * <p/>
 * Created by xwh on 2016/4/20.
 */
public class DishesFragment extends BaseFragment {

	@Bind(R.id.btn_order)
	Button mBtnOrder;
	@Bind(R.id.img_cart)
	IconicsImageView mImgCart;
	@Bind(R.id.tv_total_price)
	TextView mTvTotalPrice;
	@Bind(R.id.layout_check)
	RelativeLayout mLayoutCheck;
	@Bind(R.id.layout_cart_bottom)
	RelativeLayout mLayoutCartBottom;
	@Bind(R.id.list_dishes_category)
	RecyclerView mListDishesCategory;
	@Bind(R.id.list_dishes)
	RecyclerView mListDishes;
	@Bind(R.id.layout_clean_cart)
	LinearLayout mLayoutCleanCart;
	@Bind(R.id.layout)
	RelativeLayout mLayout;
	@Bind(R.id.list_order_item)
	RecyclerView mListOrderItem;
	@Bind(R.id.layout_cart_detail)
	FrameLayout mLayoutCartDetail;

	DishesCategoryAdapter mDishesCategoryAdapter;

	DishesAdapter mDishesAdapter;

	OrderItem1Adapter mOrderItem1Adapter;

	Shop mShop;

	float total_price;

	// 菜品数量
	private Map<Integer, Integer> mDishesCountMap;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_dishes;
	}

	@Override
	protected void initData() {
		// 获取Activity的Shop对象
		mShop = ((ShopDetailActivity) getActivity()).getShop();
		// 初始化菜品类别列表和菜品列表
		initDishesCategoryListAndDishesList();
		// 初始化订单项列表
		initOrderItemList();
	}

	@Override
	protected void initListener() {
		// 监听购物车上的加、减按钮
		mOrderItem1Adapter.setOnMyClickListener(new OrderItem1Adapter.onMyClickListener() {
			@Override
			public void onAddClickListener(View view, int position) {
				int dishes_id = mOrderItem1Adapter.getDataList().get(position).getDishes_id();
				int count = mDishesCountMap.get(dishes_id);
				mDishesCountMap.put(dishes_id, count + 1);
				mOrderItem1Adapter.getDataList().get(position).setCount(count + 1);
				updateBottomCartStyle();

				mDishesAdapter.notifyDataSetChanged();
				mOrderItem1Adapter.notifyDataSetChanged();
			}

			@Override
			public void onSubtractClickListener(View view, int position) {
				int dishes_id = mOrderItem1Adapter.getDataList().get(position).getDishes_id();
				int count = mDishesCountMap.get(dishes_id);
				if (count == 1) {
					mDishesCountMap.remove(dishes_id);
					mOrderItem1Adapter.getDataList().remove(position);
					updateBottomCartStyle();
				} else {
					mDishesCountMap.put(dishes_id, count - 1);
					mOrderItem1Adapter.getDataList().get(position).setCount(count - 1);
					updateBottomCartStyle();
				}

				mDishesAdapter.notifyDataSetChanged();
				mOrderItem1Adapter.notifyDataSetChanged();
			}
		});

		// 设置购物车显示
		mLayoutCartDetail.setVisibility(View.INVISIBLE);
		mLayoutCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mLayoutCartDetail.getVisibility() == View.INVISIBLE)
					mLayoutCartDetail.setVisibility(View.VISIBLE);
				else {
					mLayoutCartDetail.setVisibility(View.INVISIBLE);
				}
			}
		});
		mLayoutCheck.setClickable(false);

		// 清空购物车
		mLayoutCleanCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new MaterialDialog.Builder(mContext)
						.content("确定要清空购物车吗？")
						.positiveText(R.string.agree)
						.negativeText(R.string.disagree)
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								mDishesCountMap.clear();
								mOrderItem1Adapter.getDataList().clear();
								updateBottomCartStyle();

								mDishesAdapter.notifyDataSetChanged();
								mOrderItem1Adapter.notifyDataSetChanged();
							}
						})
						.show();
			}
		});

		// 下单按钮
		mBtnOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 检查用户是否登录
				if (mApplication.getUser() == null) {
					Intent intent = new Intent(mContext, NeedLoginRegisterActivity.class);
					startActivity(intent);
					return;
				}
				Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("shop_id", mShop.getShop_id());
				bundle.putSerializable("OrderItemList", (Serializable) mOrderItem1Adapter.getDataList());
				bundle.putFloat("total_price", total_price);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		mBtnOrder.setClickable(false);
	}

	/**
	 * 初始化菜品类别列表和菜品列表
	 */
	private void initDishesCategoryListAndDishesList() {
		// 初始化菜品类别列表
		mListDishesCategory.setLayoutManager(new LinearLayoutManager(mContext));
		mListDishesCategory.setItemAnimator(new DefaultItemAnimator());
		mListDishesCategory.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		mAppAction.dishes_category_query(mShop.getShop_id(), new ActionCallbackListener<List<DishesCategory>>() {
			@Override
			public void onSuccess(List<DishesCategory> data) {
				mDishesCategoryAdapter = new DishesCategoryAdapter(mContext, data);
				mListDishesCategory.setAdapter(mDishesCategoryAdapter);
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
				// 测试数据
				List<DishesCategory> data = new ArrayList<>();
				for (int i = 1; i <= 3; i++) {
					DishesCategory dishesCategory = new DishesCategory(i, "类别" + i);
					data.add(dishesCategory);
				}

				mDishesCategoryAdapter = new DishesCategoryAdapter(mContext, data);
				mListDishesCategory.setAdapter(mDishesCategoryAdapter);
			}
		});

		// 初始化菜品列表
		mListDishes.setLayoutManager(new LinearLayoutManager(mContext));
		mListDishes.setItemAnimator(new DefaultItemAnimator());
		mListDishes.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		mAppAction.dishes_query(mShop.getShop_id(), new ActionCallbackListener<List<Dishes>>() {
			@Override
			public void onSuccess(List<Dishes> data) {
				mDishesCountMap = new HashMap<>(); // 记录菜品数量
				mDishesAdapter = new DishesAdapter(mContext, data, mDishesCountMap);
				mListDishes.setAdapter(mDishesAdapter);
				// 初始化相关监听器
				initRelatedListener();
			}

			@Override
			public void onFailure(String errorCode, String errorMessage) {
				T.show(mContext, errorMessage);
				// 测试数据
				List<Dishes> data = new ArrayList<>();
				for (int i = 1; i <= 15; i++) {
					Dishes dishes = new Dishes(i, "测试菜品" + i, "", 10 - i / 5, (i - 1) / 5 + 1, 99 * i);
					data.add(dishes);
				}

				mDishesCountMap = new HashMap<>(); // 记录菜品数量
				mDishesAdapter = new DishesAdapter(mContext, data, mDishesCountMap);
				mListDishes.setAdapter(mDishesAdapter);
				// 初始化相关监听器
				initRelatedListener();
			}
		});
	}

	/**
	 * 初始化订单项列表
	 */
	private void initOrderItemList() {
		// 初始化订单列表
		mListOrderItem.setLayoutManager(new LinearLayoutManager(mContext));
		mListOrderItem.setItemAnimator(new DefaultItemAnimator());
		mListOrderItem.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<OrderItem> data = new ArrayList<>();
		mOrderItem1Adapter = new OrderItem1Adapter(mContext, data);
		mListOrderItem.setAdapter(mOrderItem1Adapter);
	}

	/**
	 * 初始化相关监听器
	 */
	private void initRelatedListener() {
		if (mDishesCategoryAdapter.getItemCount() != 0 && mDishesAdapter.getItemCount() != 0) {
			// 设置两个列表联动
			mDishesCategoryAdapter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int category_position = (int) view.getTag();
					int category_id = mDishesCategoryAdapter.getDataList().get(category_position).getCategory_id();
					int first_position = 0;
					for (int i = 0; i < mDishesAdapter.getItemCount(); i++) {
						if (mDishesAdapter.getDataList().get(i).getCategory_id() == category_id) {
							first_position = i;
							break;
						}
					}
					LinearLayoutManager layoutManager = (LinearLayoutManager) mListDishes.getLayoutManager();
					layoutManager.scrollToPositionWithOffset(first_position, 0);
				}
			});
			mListDishes.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
					super.onScrollStateChanged(recyclerView, newState);
					LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
					int first_position = layoutManager.findFirstVisibleItemPosition();
					int category_id = mDishesAdapter.getDataList().get(first_position).getCategory_id();
					mDishesCategoryAdapter.setCurrentLocation(category_id);
				}
			});

			// 监听菜品上的加、减按钮
			mDishesAdapter.setOnMyClickListener(new DishesAdapter.onMyClickListener() {
				@Override
				public void onAddClickListener(View view, int position) {
					Dishes dishes = mDishesAdapter.getDataList().get(position);
					int dishes_id = dishes.getDishes_id();
					if (mDishesCountMap.size() != 0 && mDishesCountMap.containsKey(dishes_id)) {
						int count = mDishesCountMap.get(dishes_id);
						mDishesCountMap.put(dishes_id, count + 1);
						// 更新购物车数据
						for (int i = 0; i < mOrderItem1Adapter.getDataList().size(); i++) {
							if (mOrderItem1Adapter.getDataList().get(i).getDishes_id() == dishes_id) {
								mOrderItem1Adapter.getDataList().get(i).setCount(count + 1);
								break;
							}
						}
						updateBottomCartStyle();
					} else {
						mDishesCountMap.put(dishes_id, 1);
						// 更新购物车数据
						OrderItem orderItem = new OrderItem(dishes_id, dishes.getName(), dishes.getPrice(), 1);
						mOrderItem1Adapter.getDataList().add(orderItem);
						updateBottomCartStyle();
					}
					mDishesAdapter.notifyDataSetChanged();
					mOrderItem1Adapter.notifyDataSetChanged();
				}

				@Override
				public void onSubtractClickListener(View view, int position) {
					Dishes dishes = mDishesAdapter.getDataList().get(position);
					int dishes_id = dishes.getDishes_id();
					if (mDishesCountMap != null && mDishesCountMap.containsKey(dishes_id)) {
						int count = mDishesCountMap.get(dishes_id);
						if (count == 1) {
							mDishesCountMap.remove(dishes_id);
							// 更新购物车数据
							for (int i = 0; i < mOrderItem1Adapter.getDataList().size(); i++) {
								if (mOrderItem1Adapter.getDataList().get(i).getDishes_id() == dishes_id) {
									mOrderItem1Adapter.getDataList().remove(i);
									break;
								}
							}
							updateBottomCartStyle();
						} else {
							mDishesCountMap.put(dishes_id, count - 1);
							// 更新购物车数据
							for (int i = 0; i < mOrderItem1Adapter.getDataList().size(); i++) {
								if (mOrderItem1Adapter.getDataList().get(i).getDishes_id() == dishes_id) {
									mOrderItem1Adapter.getDataList().get(i).setCount(count - 1);
									break;
								}
							}
							updateBottomCartStyle();
						}
						mDishesAdapter.notifyDataSetChanged();
						mOrderItem1Adapter.notifyDataSetChanged();
					}
				}
			});
		}
	}

	/**
	 * 更新底部购物车样式
	 */
	void updateBottomCartStyle() {
		// 无点餐样式
		if (mDishesCountMap.size() == 0) {
			mLayoutCartDetail.setVisibility(View.INVISIBLE);
			mLayoutCheck.setClickable(false);
			mBtnOrder.setClickable(false);
			mImgCart.setColor(getResources().getColor(R.color.third_text));
			mBtnOrder.setText("开始选购");
			mBtnOrder.setBackgroundColor(getResources().getColor(R.color.third_text));
			total_price = 0.0f;
			mTvTotalPrice.setText("￥0.0");
			mTvTotalPrice.setTextColor(getResources().getColor(R.color.third_text));
		} else {
			mLayoutCheck.setClickable(true);
			mBtnOrder.setClickable(true);
			mImgCart.setColor(getResources().getColor(R.color.colorPrimary));
			mBtnOrder.setText("选好了");
			mBtnOrder.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_common_selector));
			total_price = 0.0f;
			for (int i = 0; i < mOrderItem1Adapter.getDataList().size(); i++) {
				total_price += mOrderItem1Adapter.getDataList().get(i).getPrice()
						* mOrderItem1Adapter.getDataList().get(i).getCount();
			}
			mTvTotalPrice.setText("￥" + total_price);
			mTvTotalPrice.setTextColor(getResources().getColor(R.color.colorAccent));
		}
	}

}
