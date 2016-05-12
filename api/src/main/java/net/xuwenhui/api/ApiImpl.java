package net.xuwenhui.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.xuwenhui.model.Address;
import net.xuwenhui.model.Dishes;
import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.model.Evaluation;
import net.xuwenhui.model.Notice;
import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.model.Shop;
import net.xuwenhui.model.User;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Api实现类
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public class ApiImpl implements Api {

	private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
	private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

	private OkHttpEngine mOkHttpEngine;

	public ApiImpl() {
		mOkHttpEngine = OkHttpEngine.getInstance();
	}

	@Override
	public ApiResponse<User> user_login(String phone_num, String password) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_login");
		paramMap.put("phone_num", phone_num);
		paramMap.put("password", password);

		Type type = new TypeToken<ApiResponse<User>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> user_register(String phone_num, String password, int role_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_register");
		paramMap.put("phone_num", phone_num);
		paramMap.put("password", password);
		paramMap.put("role_id", String.valueOf(role_id));

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> user_forget_password(String phone_num, String new_password) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_forget_password");
		paramMap.put("phone_num", phone_num);
		paramMap.put("new_password", new_password);

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> user_update_password(String phone_num, String old_password, String new_password) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_update_password");
		paramMap.put("phone_num", phone_num);
		paramMap.put("old_password", old_password);
		paramMap.put("new_password", new_password);

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<User> user_update_info(int user_id, String image_src, String nickname) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_update_info");
		paramMap.put("user_id", String.valueOf(user_id));
		paramMap.put("image_src", image_src);
		paramMap.put("nickname", nickname);

		Type type = new TypeToken<ApiResponse<User>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> user_check_merchant(int user_id, boolean is_valid) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_check_merchant");
		paramMap.put("user_id", String.valueOf(user_id));
		paramMap.put("is_valid", String.valueOf(is_valid));

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<User>> user_query_user() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_query_user");

		Type type = new TypeToken<ApiResponse<List<User>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<User>> user_query_merchant() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "user_query_merchant");

		Type type = new TypeToken<ApiResponse<List<User>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Address> address_create(int user_id, String name, String sex, String phone_num, String address_desc, String note) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "address_create");
		paramMap.put("user_id", String.valueOf(user_id));
		paramMap.put("name", name);
		paramMap.put("sex", sex);
		paramMap.put("phone_num", phone_num);
		paramMap.put("address_desc", address_desc);
		paramMap.put("note", note);

		Type type = new TypeToken<ApiResponse<Address>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> address_delete(int address_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "address_delete");
		paramMap.put("address_id", String.valueOf(address_id));

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Address> address_update(int address_id, String name, String sex, String phone_num, String address_desc, String note) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "address_update");
		paramMap.put("address_id", String.valueOf(address_id));
		paramMap.put("name", name);
		paramMap.put("sex", sex);
		paramMap.put("phone_num", phone_num);
		paramMap.put("address_desc", address_desc);
		paramMap.put("note", note);

		Type type = new TypeToken<ApiResponse<Address>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Address> address_query_by_id(int address_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "address_query_by_id");
		paramMap.put("address_id", String.valueOf(address_id));

		Type type = new TypeToken<ApiResponse<Address>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Address>> address_query(int user_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "address_query");
		paramMap.put("user_id", String.valueOf(user_id));

		Type type = new TypeToken<ApiResponse<List<Address>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Shop> shop_create(int user_id, int sort_id, String name, String image_src, String address_desc) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_create");
		paramMap.put("user_id", String.valueOf(user_id));
		paramMap.put("sort_id", String.valueOf(sort_id));
		paramMap.put("name", name);
		paramMap.put("image_src", image_src);
		paramMap.put("address_desc", address_desc);

		Type type = new TypeToken<ApiResponse<Shop>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Shop> shop_update_info(int shop_id, int sort_id, String name, String image_src, String address_desc) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_update_info");
		paramMap.put("shop_id", String.valueOf(shop_id));
		paramMap.put("sort_id", String.valueOf(sort_id));
		paramMap.put("name", name);
		paramMap.put("image_src", image_src);
		paramMap.put("address_desc", address_desc);

		Type type = new TypeToken<ApiResponse<Shop>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Shop> shop_update_notice(int shop_id, String notice) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_update_notice");
		paramMap.put("shop_id", String.valueOf(shop_id));
		paramMap.put("notice", notice);

		Type type = new TypeToken<ApiResponse<Shop>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Shop> shop_query_by_user(int user_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_query_by_user");
		paramMap.put("user_id", String.valueOf(user_id));

		Type type = new TypeToken<ApiResponse<Shop>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<String> shop_query_notice(int shop_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_query_notice");
		paramMap.put("shop_id", String.valueOf(shop_id));

		Type type = new TypeToken<ApiResponse<String>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<String> shop_query_phone(int shop_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_query_phone");
		paramMap.put("shop_id", String.valueOf(shop_id));

		Type type = new TypeToken<ApiResponse<String>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Shop>> shop_query() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "shop_query");

		Type type = new TypeToken<ApiResponse<List<Shop>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Dishes> dishes_create(int shop_id, int category_id, String name, String image_src, float price) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_create");
		paramMap.put("shop_id", String.valueOf(shop_id));
		paramMap.put("category_id", String.valueOf(category_id));
		paramMap.put("name", name);
		paramMap.put("image_src", image_src);
		paramMap.put("price", String.valueOf(price));

		Type type = new TypeToken<ApiResponse<Dishes>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> dishes_delete(int dishes_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_delete");
		paramMap.put("dishes_id", String.valueOf(dishes_id));

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Dishes> dishes_update(int dishes_id, int category_id, String name, String image_src, float price) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_update");
		paramMap.put("dishes_id", String.valueOf(dishes_id));
		paramMap.put("category_id", String.valueOf(category_id));
		paramMap.put("name", name);
		paramMap.put("image_src", image_src);
		paramMap.put("price", String.valueOf(price));

		Type type = new TypeToken<ApiResponse<Dishes>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Dishes>> dishes_query(int shop_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_query");
		paramMap.put("shop_id", String.valueOf(shop_id));

		Type type = new TypeToken<ApiResponse<List<Dishes>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<DishesCategory> dishes_category_create(int shop_id, String category_desc) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_category_create");
		paramMap.put("shop_id", String.valueOf(shop_id));
		paramMap.put("category_desc", category_desc);

		Type type = new TypeToken<ApiResponse<DishesCategory>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> dishes_category_delete(int dishes_category_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_category_delete");
		paramMap.put("dishes_category_id", String.valueOf(dishes_category_id));

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<DishesCategory> dishes_category_update(int dishes_category_id, String category_desc) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_category_update");
		paramMap.put("dishes_category_id", String.valueOf(dishes_category_id));
		paramMap.put("category_desc", category_desc);

		Type type = new TypeToken<ApiResponse<DishesCategory>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<DishesCategory> dishes_category_query_by_id(int dishes_category_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_category_query_by_id");
		paramMap.put("dishes_category_id", String.valueOf(dishes_category_id));

		Type type = new TypeToken<ApiResponse<DishesCategory>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<DishesCategory>> dishes_category_query(int shop_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "dishes_category_query");
		paramMap.put("shop_id", String.valueOf(shop_id));

		Type type = new TypeToken<ApiResponse<List<DishesCategory>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Order> order_create(int user_id, int shop_id, int address_id, String create_time, float total_price, String note, List<OrderItem> orderItemList) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "order_create");
		paramMap.put("user_id", String.valueOf(user_id));
		paramMap.put("shop_id", String.valueOf(shop_id));
		paramMap.put("address_id", String.valueOf(address_id));
		paramMap.put("create_time", create_time);
		paramMap.put("total_price", String.valueOf(total_price));
		paramMap.put("orderItemList", new Gson().toJson(orderItemList));

		Type type = new TypeToken<ApiResponse<Order>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Order> order_update_state(int order_id, int order_state_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "order_update_state");
		paramMap.put("order_id", String.valueOf(order_id));
		paramMap.put("order_state_id", String.valueOf(order_state_id));

		Type type = new TypeToken<ApiResponse<Order>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Order>> order_query_by_user(int user_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "order_query_by_user");
		paramMap.put("user_id", String.valueOf(user_id));

		Type type = new TypeToken<ApiResponse<List<Order>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Order>> order_query_by_shop(int shop_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "order_query_by_shop");
		paramMap.put("shop_id", String.valueOf(shop_id));

		Type type = new TypeToken<ApiResponse<List<Order>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Evaluation> evaluation_create(int order_id, int star, String content, String time) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "evaluation_create");
		paramMap.put("order_id", String.valueOf(order_id));
		paramMap.put("star", String.valueOf(star));
		paramMap.put("content", content);
		paramMap.put("time", time);

		Type type = new TypeToken<ApiResponse<Evaluation>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Evaluation> evaluation_query(int order_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "evaluation_query");
		paramMap.put("order_id", String.valueOf(order_id));

		Type type = new TypeToken<ApiResponse<Evaluation>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Evaluation>> evaluation_query_by_shop(int shop_id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "evaluation_query_by_shop");
		paramMap.put("shop_id", String.valueOf(shop_id));

		Type type = new TypeToken<ApiResponse<List<Evaluation>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<Void> notice_update(int notice_id, String title, String image_src) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "notice_update");
		paramMap.put("notice_id", String.valueOf(notice_id));
		paramMap.put("title", title);
		paramMap.put("image_src", image_src);

		Type type = new TypeToken<ApiResponse<Void>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<List<Notice>> notice_query() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "notice_query");

		Type type = new TypeToken<ApiResponse<List<Notice>>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}

	@Override
	public ApiResponse<String> common_get_qiniu_token() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("method", "common_get_qiniu_token");

		Type type = new TypeToken<ApiResponse<String>>() {
		}.getType();
		try {
			return mOkHttpEngine.postHandle(paramMap, type);
		} catch (Exception e) {
			return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
		}
	}
}
