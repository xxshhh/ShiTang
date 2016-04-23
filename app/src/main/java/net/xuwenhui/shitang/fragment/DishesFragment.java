package net.xuwenhui.shitang.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.xuwenhui.model.Dishes;
import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.DishesAdapter;
import net.xuwenhui.shitang.adapter.DishesCategoryAdapter;
import net.xuwenhui.shitang.adapter.OrderItemAdapter;
import net.xuwenhui.shitang.view.DividerItemDecoration;

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

	@Bind(R.id.tv_order)
	TextView mTvOrder;
	@Bind(R.id.img_cart)
	ImageView mImgCart;
	@Bind(R.id.tv_total_price)
	TextView mTvTotalPrice;
	@Bind(R.id.layout_check)
	RelativeLayout mLayoutCheck;
	@Bind(R.id.layout1)
	RelativeLayout mLayout1;
	@Bind(R.id.list_dishes_category)
	RecyclerView mListDishesCategory;
	@Bind(R.id.list_dishes)
	RecyclerView mListDishes;
	@Bind(R.id.layout_clean_cart)
	LinearLayout mLayoutCleanCart;
	@Bind(R.id.layout2)
	RelativeLayout mLayout2;
	@Bind(R.id.list_order_item)
	RecyclerView mListOrderItem;

	DishesCategoryAdapter mDishesCategoryAdapter;

	DishesAdapter mDishesAdapter;

	OrderItemAdapter mOrderItemAdapter;

	// 菜品数量
	private Map<Integer, Integer> mDishesCountMap;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_dishes;
	}

	@Override
	protected void initData() {
		// 初始化菜品类别列表
		mListDishesCategory.setLayoutManager(new LinearLayoutManager(mContext));
		mListDishesCategory.setItemAnimator(new DefaultItemAnimator());
		mListDishesCategory.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<DishesCategory> data1 = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			DishesCategory dishesCategory = new DishesCategory(i, "类别" + i);
			data1.add(dishesCategory);
		}
		mDishesCategoryAdapter = new DishesCategoryAdapter(mContext, data1);
		mListDishesCategory.setAdapter(mDishesCategoryAdapter);

		// 初始化菜品列表
		mListDishes.setLayoutManager(new LinearLayoutManager(mContext));
		mListDishes.setItemAnimator(new DefaultItemAnimator());
		mListDishes.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<Dishes> data2 = new ArrayList<>();
		for (int i = 1; i <= 15; i++) {
			Dishes dishes = new Dishes(i, "测试菜品" + i, "", 10, (i - 1) / 5 + 1, 99 * i);
			data2.add(dishes);
		}
		mDishesCountMap = new HashMap<>();
		mDishesAdapter = new DishesAdapter(mContext, data2, mDishesCountMap);
		mListDishes.setAdapter(mDishesAdapter);

		// 初始化订单列表
		mListOrderItem.setLayoutManager(new LinearLayoutManager(mContext));
		mListOrderItem.setItemAnimator(new DefaultItemAnimator());
		mListOrderItem.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<OrderItem> data3 = new ArrayList<>();
		mOrderItemAdapter = new OrderItemAdapter(mContext, data3, mDishesCountMap);
		mListOrderItem.setAdapter(mOrderItemAdapter);
	}

	@Override
	protected void initListener() {
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

		// 监听加、减按钮
		mDishesAdapter.setOnMyClickListener(new DishesAdapter.onMyClickListener() {
			@Override
			public void onAddClickListener(View view, int position) {
				Dishes dishes = mDishesAdapter.getDataList().get(position);
				int dishes_id = dishes.getDishes_id();
				if (mDishesCountMap != null && mDishesCountMap.containsKey(dishes_id)) {
					int count = mDishesCountMap.get(dishes_id);
					mDishesCountMap.put(dishes_id, count + 1);
				} else {
					mDishesCountMap.put(dishes_id, 1);
				}
				mDishesAdapter.notifyDataSetChanged();

				// 更新购物车...
				mOrderItemAdapter.notifyDataSetChanged();
			}

			@Override
			public void onSubtractClickListener(View view, int position) {
				Dishes dishes = mDishesAdapter.getDataList().get(position);
				int dishes_id = dishes.getDishes_id();
				if (mDishesCountMap != null && mDishesCountMap.containsKey(dishes_id)) {
					int count = mDishesCountMap.get(dishes_id);
					if (count == 1) {
						mDishesCountMap.remove(dishes_id);
					} else {
						mDishesCountMap.put(dishes_id, count - 1);
					}
					mDishesAdapter.notifyDataSetChanged();

					// 更新购物车...
					mOrderItemAdapter.notifyDataSetChanged();
				}
			}
		});

		// 清空购物车
		mLayoutCleanCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			}
		});
	}

}
