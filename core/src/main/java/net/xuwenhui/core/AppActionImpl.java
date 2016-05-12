package net.xuwenhui.core;

import android.content.Context;
import android.os.AsyncTask;

import net.xuwenhui.api.Api;
import net.xuwenhui.api.ApiImpl;
import net.xuwenhui.api.ApiResponse;
import net.xuwenhui.core.util.FormCheckUtil;
import net.xuwenhui.model.Address;
import net.xuwenhui.model.Dishes;
import net.xuwenhui.model.DishesCategory;
import net.xuwenhui.model.Evaluation;
import net.xuwenhui.model.Notice;
import net.xuwenhui.model.Order;
import net.xuwenhui.model.OrderItem;
import net.xuwenhui.model.Shop;
import net.xuwenhui.model.User;

import java.util.List;

/**
 * AppAction接口的实现类
 * <p/>
 * Created by xwh on 2016/3/29.
 */
public class AppActionImpl implements AppAction {

	private Context mContext;
	private Api mApi;

	public AppActionImpl(Context context) {
		mContext = context;
		mApi = new ApiImpl();
	}

	@Override
	public void user_login(final String phone_num, final String password, final ActionCallbackListener<User> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num) && !phone_num.equals("admin")) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "手机号格式不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(password)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码为空");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<User>>() {

			@Override
			protected ApiResponse<User> doInBackground(Void... voids) {
				return mApi.user_login(phone_num, password);
			}

			@Override
			protected void onPostExecute(ApiResponse<User> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_register(final String phone_num, final String password, final int role_id, final ActionCallbackListener<Void> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "手机号格式不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(password)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码为空");
			return;
		}
		if (!FormCheckUtil.checkLength(password, 6, 16)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码长度必须为6-16位");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.user_register(phone_num, password, role_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_forget_password(final String phone_num, final String new_password, final ActionCallbackListener<Void> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "手机号格式不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(new_password)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码为空");
			return;
		}
		if (!FormCheckUtil.checkLength(new_password, 6, 16)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码长度必须为6-16位");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.user_forget_password(phone_num, new_password);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_update_password(final String phone_num, final String old_password, final String new_password, final ActionCallbackListener<Void> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num) && !phone_num.equals("admin")) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "手机号格式不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(old_password) || FormCheckUtil.isEmpty(new_password)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码为空");
			return;
		}
		if (!FormCheckUtil.checkLength(old_password, 6, 16) || !FormCheckUtil.checkLength(new_password, 6, 16)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "密码长度必须为6-16位");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.user_update_password(phone_num, old_password, new_password);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_update_info(final int user_id, final String image_src, final String nickname, final ActionCallbackListener<User> listener) {
		// 参数检查
		if (!FormCheckUtil.checkLength(nickname, 0, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "昵称长度不能超过10位");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<User>>() {

			@Override
			protected ApiResponse<User> doInBackground(Void... voids) {
				return mApi.user_update_info(user_id, image_src, nickname);
			}

			@Override
			protected void onPostExecute(ApiResponse<User> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_check_merchant(final int user_id, final boolean is_valid, final ActionCallbackListener<Void> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.user_check_merchant(user_id, is_valid);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_query_user(final ActionCallbackListener<List<User>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<User>>>() {

			@Override
			protected ApiResponse<List<User>> doInBackground(Void... voids) {
				return mApi.user_query_user();
			}

			@Override
			protected void onPostExecute(ApiResponse<List<User>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void user_query_merchant(final ActionCallbackListener<List<User>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<User>>>() {

			@Override
			protected ApiResponse<List<User>> doInBackground(Void... voids) {
				return mApi.user_query_merchant();
			}

			@Override
			protected void onPostExecute(ApiResponse<List<User>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void address_create(final int user_id, final String name, final String sex, final String phone_num, final String address_desc, final String note, final ActionCallbackListener<Address> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(name)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "名字为空");
			return;
		}
		if (!FormCheckUtil.checkLength(name, 1, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "名字长度不能超过10位");
			return;
		}
		if (FormCheckUtil.isEmpty(sex)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "性别为空");
			return;
		}
		if (FormCheckUtil.isEmpty(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num) && !phone_num.equals("admin")) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "手机号格式不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(address_desc)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "地址描述为空");
			return;
		}
		if (!FormCheckUtil.checkLength(address_desc, 1, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "地址描述不能超过20字");
			return;
		}
		if (!FormCheckUtil.checkLength(note, 0, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "备注不能超过20字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Address>>() {

			@Override
			protected ApiResponse<Address> doInBackground(Void... voids) {
				return mApi.address_create(user_id, name, sex, phone_num, address_desc, note);
			}

			@Override
			protected void onPostExecute(ApiResponse<Address> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void address_delete(final int address_id, final ActionCallbackListener<Void> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.address_delete(address_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void address_update(final int address_id, final String name, final String sex, final String phone_num, final String address_desc, final String note, final ActionCallbackListener<Address> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(name)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "名字为空");
			return;
		}
		if (!FormCheckUtil.checkLength(name, 1, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "名字长度不能超过10位");
			return;
		}
		if (FormCheckUtil.isEmpty(sex)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "性别为空");
			return;
		}
		if (FormCheckUtil.isEmpty(phone_num)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "手机号为空");
			return;
		}
		if (!FormCheckUtil.checkPhoneNum(phone_num) && !phone_num.equals("admin")) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "手机号格式不正确");
			return;
		}
		if (FormCheckUtil.isEmpty(address_desc)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "地址描述为空");
			return;
		}
		if (!FormCheckUtil.checkLength(address_desc, 1, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "地址描述不能超过20字");
			return;
		}
		if (!FormCheckUtil.checkLength(note, 0, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "备注不能超过20字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Address>>() {

			@Override
			protected ApiResponse<Address> doInBackground(Void... voids) {
				return mApi.address_update(address_id, name, sex, phone_num, address_desc, note);
			}

			@Override
			protected void onPostExecute(ApiResponse<Address> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void address_query_by_id(final int address_id, final ActionCallbackListener<Address> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Address>>() {

			@Override
			protected ApiResponse<Address> doInBackground(Void... voids) {
				return mApi.address_query_by_id(address_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Address> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void address_query(final int user_id, final ActionCallbackListener<List<Address>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Address>>>() {

			@Override
			protected ApiResponse<List<Address>> doInBackground(Void... voids) {
				return mApi.address_query(user_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Address>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_create(final int user_id, final int sort_id, final String name, final String image_src, final String address_desc, final ActionCallbackListener<Shop> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(name)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "名字为空");
			return;
		}
		if (!FormCheckUtil.checkLength(name, 1, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "名字长度不能超过10位");
			return;
		}
		if (sort_id == 0) {
			listener.onFailure(ErrorCode.PARAM_NULL, "食堂不能为空");
			return;
		}
		if (FormCheckUtil.isEmpty(address_desc)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "地址描述为空");
			return;
		}
		if (!FormCheckUtil.checkLength(address_desc, 1, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "地址描述不能超过20字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Shop>>() {

			@Override
			protected ApiResponse<Shop> doInBackground(Void... voids) {
				return mApi.shop_create(user_id, sort_id, name, image_src, address_desc);
			}

			@Override
			protected void onPostExecute(ApiResponse<Shop> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_update_info(final int shop_id, final int sort_id, final String name, final String image_src, final String address_desc, final ActionCallbackListener<Shop> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(name)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "名字为空");
			return;
		}
		if (!FormCheckUtil.checkLength(name, 1, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "名字长度不能超过10位");
			return;
		}
		if (sort_id == 0) {
			listener.onFailure(ErrorCode.PARAM_NULL, "食堂不能为空");
			return;
		}
		if (FormCheckUtil.isEmpty(address_desc)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "地址描述为空");
			return;
		}
		if (!FormCheckUtil.checkLength(address_desc, 1, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "地址描述不能超过20字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Shop>>() {

			@Override
			protected ApiResponse<Shop> doInBackground(Void... voids) {
				return mApi.shop_update_info(shop_id, sort_id, name, image_src, address_desc);
			}

			@Override
			protected void onPostExecute(ApiResponse<Shop> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_update_notice(final int shop_id, final String notice, final ActionCallbackListener<Shop> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(notice)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "公告内容为空");
			return;
		}
		if (!FormCheckUtil.checkLength(notice, 1, 50)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "公告内容不能超过50字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Shop>>() {

			@Override
			protected ApiResponse<Shop> doInBackground(Void... voids) {
				return mApi.shop_update_notice(shop_id, notice);
			}

			@Override
			protected void onPostExecute(ApiResponse<Shop> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_query_by_user(final int user_id, final ActionCallbackListener<Shop> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Shop>>() {

			@Override
			protected ApiResponse<Shop> doInBackground(Void... voids) {
				return mApi.shop_query_by_user(user_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Shop> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_query_notice(final int shop_id, final ActionCallbackListener<String> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<String>>() {

			@Override
			protected ApiResponse<String> doInBackground(Void... voids) {
				return mApi.shop_query_notice(shop_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<String> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_query_phone(final int shop_id, final ActionCallbackListener<String> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<String>>() {

			@Override
			protected ApiResponse<String> doInBackground(Void... voids) {
				return mApi.shop_query_phone(shop_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<String> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void shop_query(final ActionCallbackListener<List<Shop>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Shop>>>() {

			@Override
			protected ApiResponse<List<Shop>> doInBackground(Void... voids) {
				return mApi.shop_query();
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Shop>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_create(final int shop_id, final int category_id, final String name, final String image_src, final float price, final ActionCallbackListener<Dishes> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(name)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品名称为空");
			return;
		}
		if (category_id == 0) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品类别不能为空");
			return;
		}
		if (!FormCheckUtil.checkLength(name, 1, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "菜品名称不能超过20字");
			return;
		}
		if (price == 0.0f) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品价格不能为空");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Dishes>>() {

			@Override
			protected ApiResponse<Dishes> doInBackground(Void... voids) {
				return mApi.dishes_create(shop_id, category_id, name, image_src, price);
			}

			@Override
			protected void onPostExecute(ApiResponse<Dishes> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_delete(final int dishes_id, final ActionCallbackListener<Void> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.dishes_delete(dishes_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_update(final int dishes_id, final int category_id, final String name, final String image_src, final float price, final ActionCallbackListener<Dishes> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(name)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品名称为空");
			return;
		}
		if (category_id == 0) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品类别不能为空");
			return;
		}
		if (!FormCheckUtil.checkLength(name, 1, 20)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "菜品名称不能超过20字");
			return;
		}
		if (price == 0.0f) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品价格不能为空");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Dishes>>() {

			@Override
			protected ApiResponse<Dishes> doInBackground(Void... voids) {
				return mApi.dishes_update(dishes_id, category_id, name, image_src, price);
			}

			@Override
			protected void onPostExecute(ApiResponse<Dishes> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_query(final int shop_id, final ActionCallbackListener<List<Dishes>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Dishes>>>() {

			@Override
			protected ApiResponse<List<Dishes>> doInBackground(Void... voids) {
				return mApi.dishes_query(shop_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Dishes>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_category_create(final int shop_id, final String category_desc, final ActionCallbackListener<DishesCategory> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(category_desc)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品类别名称为空");
			return;
		}
		if (!FormCheckUtil.checkLength(category_desc, 1, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "菜品类别名称不能超过10字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<DishesCategory>>() {

			@Override
			protected ApiResponse<DishesCategory> doInBackground(Void... voids) {
				return mApi.dishes_category_create(shop_id, category_desc);
			}

			@Override
			protected void onPostExecute(ApiResponse<DishesCategory> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_category_delete(final int dishes_category_id, final ActionCallbackListener<Void> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.dishes_category_delete(dishes_category_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_category_update(final int dishes_category_id, final String category_desc, final ActionCallbackListener<DishesCategory> listener) {
		// 参数检查
		if (FormCheckUtil.isEmpty(category_desc)) {
			listener.onFailure(ErrorCode.PARAM_NULL, "菜品类别名称为空");
			return;
		}
		if (!FormCheckUtil.checkLength(category_desc, 1, 10)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "菜品类别名称不能超过10字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<DishesCategory>>() {

			@Override
			protected ApiResponse<DishesCategory> doInBackground(Void... voids) {
				return mApi.dishes_category_update(dishes_category_id, category_desc);
			}

			@Override
			protected void onPostExecute(ApiResponse<DishesCategory> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_category_query_by_id(final int dishes_category_id, final ActionCallbackListener<DishesCategory> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<DishesCategory>>() {

			@Override
			protected ApiResponse<DishesCategory> doInBackground(Void... voids) {
				return mApi.dishes_category_query_by_id(dishes_category_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<DishesCategory> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void dishes_category_query(final int shop_id, final ActionCallbackListener<List<DishesCategory>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<DishesCategory>>>() {

			@Override
			protected ApiResponse<List<DishesCategory>> doInBackground(Void... voids) {
				return mApi.dishes_category_query(shop_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<List<DishesCategory>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void order_create(final int user_id, final int shop_id, final int address_id, final String create_time, final float total_price, final String note, final List<OrderItem> orderItemList, final ActionCallbackListener<Order> listener) {
		// 参数检查
		if (!FormCheckUtil.checkLength(note, 0, 30)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "备注不能超过30字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Order>>() {

			@Override
			protected ApiResponse<Order> doInBackground(Void... voids) {
				return mApi.order_create(user_id, shop_id, address_id, create_time, total_price, note, orderItemList);
			}

			@Override
			protected void onPostExecute(ApiResponse<Order> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void order_update_state(final int order_id, final int order_state_id, final ActionCallbackListener<Order> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Order>>() {

			@Override
			protected ApiResponse<Order> doInBackground(Void... voids) {
				return mApi.order_update_state(order_id, order_state_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Order> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void order_query_by_user(final int user_id, final ActionCallbackListener<List<Order>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Order>>>() {

			@Override
			protected ApiResponse<List<Order>> doInBackground(Void... voids) {
				return mApi.order_query_by_user(user_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Order>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void order_query_by_shop(final int shop_id, final ActionCallbackListener<List<Order>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Order>>>() {

			@Override
			protected ApiResponse<List<Order>> doInBackground(Void... voids) {
				return mApi.order_query_by_shop(shop_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Order>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void evaluation_create(final int order_id, final int star, final String content, final String time, final ActionCallbackListener<Evaluation> listener) {
		// 参数检查
		if (!FormCheckUtil.checkLength(content, 1, 100)) {
			listener.onFailure(ErrorCode.PARAM_ILLEGAL, "评价内容不能超过100字");
			return;
		}

		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Evaluation>>() {

			@Override
			protected ApiResponse<Evaluation> doInBackground(Void... voids) {
				return mApi.evaluation_create(order_id, star, content, time);
			}

			@Override
			protected void onPostExecute(ApiResponse<Evaluation> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void evaluation_query(final int order_id, final ActionCallbackListener<Evaluation> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Evaluation>>() {

			@Override
			protected ApiResponse<Evaluation> doInBackground(Void... voids) {
				return mApi.evaluation_query(order_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<Evaluation> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void evaluation_query_by_shop(final int shop_id, final ActionCallbackListener<List<Evaluation>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Evaluation>>>() {

			@Override
			protected ApiResponse<List<Evaluation>> doInBackground(Void... voids) {
				return mApi.evaluation_query_by_shop(shop_id);
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Evaluation>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void notice_update(final int notice_id, final String title, final String image_src, final ActionCallbackListener<Void> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<Void>>() {

			@Override
			protected ApiResponse<Void> doInBackground(Void... voids) {
				return mApi.notice_update(notice_id, title, image_src);
			}

			@Override
			protected void onPostExecute(ApiResponse<Void> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(null);
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void notice_query(final ActionCallbackListener<List<Notice>> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<List<Notice>>>() {

			@Override
			protected ApiResponse<List<Notice>> doInBackground(Void... voids) {
				return mApi.notice_query();
			}

			@Override
			protected void onPostExecute(ApiResponse<List<Notice>> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}

	@Override
	public void common_get_qiniu_token(final ActionCallbackListener<String> listener) {
		// 请求Api
		new AsyncTask<Void, Void, ApiResponse<String>>() {

			@Override
			protected ApiResponse<String> doInBackground(Void... voids) {
				return mApi.common_get_qiniu_token();
			}

			@Override
			protected void onPostExecute(ApiResponse<String> response) {
				if (response != null) {
					if (response.isSuccess()) {
						listener.onSuccess(response.getData());
					} else {
						listener.onFailure(response.getCode(), response.getMessage());
					}
				}
			}
		}.execute();
	}
}
