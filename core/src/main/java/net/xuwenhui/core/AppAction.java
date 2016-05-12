package net.xuwenhui.core;

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
 * 接收app层的各种Action
 * <p/>
 * Created by xwh on 2016/3/29.
 */
public interface AppAction {

	/**
	 * 登录
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @param listener  回调监听器
	 */
	void user_login(String phone_num, String password, ActionCallbackListener<User> listener);

	/**
	 * 注册
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @param role_id   角色
	 * @param listener  回调监听器
	 */
	void user_register(String phone_num, String password, int role_id, ActionCallbackListener<Void> listener);

	/**
	 * 忘记密码
	 *
	 * @param phone_num    手机号
	 * @param new_password 新密码
	 * @param listener     回调监听器
	 */
	void user_forget_password(String phone_num, String new_password, ActionCallbackListener<Void> listener);

	/**
	 * 修改密码
	 *
	 * @param phone_num    手机号
	 * @param old_password 旧密码
	 * @param new_password 新密码
	 * @param listener     回调监听器
	 */
	void user_update_password(String phone_num, String old_password, String new_password, ActionCallbackListener<Void> listener);

	/**
	 * 修改信息
	 *
	 * @param user_id   用户id
	 * @param image_src 图片地址
	 * @param nickname  昵称
	 * @param listener  回调监听器
	 */
	void user_update_info(int user_id, String image_src, String nickname, ActionCallbackListener<User> listener);

	/**
	 * 审核商户
	 *
	 * @param user_id  商家id
	 * @param is_valid 审核结果
	 * @param listener 回调监听器
	 */
	void user_check_merchant(int user_id, boolean is_valid, ActionCallbackListener<Void> listener);

	/**
	 * 查询所有普通用户
	 *
	 * @param listener 回调监听器
	 */
	void user_query_user(ActionCallbackListener<List<User>> listener);

	/**
	 * 查询所有商家用户
	 *
	 * @param listener 回调监听器
	 */
	void user_query_merchant(ActionCallbackListener<List<User>> listener);

	/**
	 * 新增收货地址
	 *
	 * @param user_id      用户id
	 * @param name         姓名
	 * @param sex          性别
	 * @param phone_num    手机号
	 * @param address_desc 地址描述
	 * @param note         备注
	 * @param listener     回调监听器
	 */
	void address_create(int user_id, String name, String sex, String phone_num, String address_desc, String note, ActionCallbackListener<Address> listener);

	/**
	 * 删除收货地址（标记删除）
	 *
	 * @param address_id 地址id
	 * @param listener   回调监听器
	 */
	void address_delete(int address_id, ActionCallbackListener<Void> listener);

	/**
	 * 修改收货地址
	 *
	 * @param address_id   地址id
	 * @param name         姓名
	 * @param sex          性别
	 * @param phone_num    手机号
	 * @param address_desc 地址描述
	 * @param note         备注
	 * @param listener     回调监听器
	 */
	void address_update(int address_id, String name, String sex, String phone_num, String address_desc, String note, ActionCallbackListener<Address> listener);

	/**
	 * 根据id查询收货地址
	 *
	 * @param address_id 地址id
	 * @param listener   回调监听器
	 * @return
	 */
	void address_query_by_id(int address_id, ActionCallbackListener<Address> listener);

	/**
	 * 查询用户所有收货地址
	 *
	 * @param user_id  用户id
	 * @param listener 回调监听器
	 */
	void address_query(int user_id, ActionCallbackListener<List<Address>> listener);

	/**
	 * 新增店铺
	 *
	 * @param user_id      商家id
	 * @param sort_id      类别id
	 * @param name         名称
	 * @param image_src    图片地址
	 * @param address_desc 地址描述
	 * @param listener     回调监听器
	 */
	void shop_create(int user_id, int sort_id, String name, String image_src, String address_desc, ActionCallbackListener<Shop> listener);

	/**
	 * 修改店铺基本信息
	 *
	 * @param shop_id      店铺id
	 * @param sort_id      类别id
	 * @param name         名称
	 * @param image_src    图片地址
	 * @param address_desc 地址描述
	 * @param listener     回调监听器
	 */
	void shop_update_info(int shop_id, int sort_id, String name, String image_src, String address_desc, ActionCallbackListener<Shop> listener);

	/**
	 * 修改店铺公告
	 *
	 * @param shop_id  店铺id
	 * @param notice   公告
	 * @param listener 回调监听器
	 */
	void shop_update_notice(int shop_id, String notice, ActionCallbackListener<Shop> listener);

	/**
	 * 查询商户的店铺
	 *
	 * @param user_id  商户id
	 * @param listener 回调监听器
	 */
	void shop_query_by_user(int user_id, ActionCallbackListener<Shop> listener);

	/**
	 * 查询店铺公告
	 *
	 * @param shop_id  店铺id
	 * @param listener 回调监听器
	 */
	void shop_query_notice(int shop_id, ActionCallbackListener<String> listener);

	/**
	 * 查询店铺商家电话
	 *
	 * @param shop_id  店铺id
	 * @param listener 回调监听器
	 */
	void shop_query_phone(int shop_id, ActionCallbackListener<String> listener);

	/**
	 * 查询所有店铺
	 *
	 * @param listener 回调监听器
	 */
	void shop_query(ActionCallbackListener<List<Shop>> listener);

	/**
	 * 新增菜品
	 *
	 * @param shop_id     店铺id
	 * @param category_id 类别id
	 * @param name        名称
	 * @param image_src   图片地址
	 * @param price       价格
	 * @param listener    回调监听器
	 */
	void dishes_create(int shop_id, int category_id, String name, String image_src, float price, ActionCallbackListener<Dishes> listener);

	/**
	 * 删除菜品（标记删除）
	 *
	 * @param dishes_id 菜品id
	 * @param listener  回调监听器
	 */
	void dishes_delete(int dishes_id, ActionCallbackListener<Void> listener);

	/**
	 * 修改菜品
	 *
	 * @param dishes_id   菜品id
	 * @param category_id 类别id
	 * @param name        姓名
	 * @param image_src   图片地址
	 * @param price       价格
	 * @param listener    回调监听器
	 */
	void dishes_update(int dishes_id, int category_id, String name, String image_src, float price, ActionCallbackListener<Dishes> listener);

	/**
	 * 查询店铺所有菜品
	 *
	 * @param shop_id  店铺id
	 * @param listener 回调监听器
	 */
	void dishes_query(int shop_id, ActionCallbackListener<List<Dishes>> listener);

	/**
	 * 新增菜品类别
	 *
	 * @param shop_id       店铺id
	 * @param category_desc 类别描述
	 * @param listener      回调监听器
	 */
	void dishes_category_create(int shop_id, String category_desc, ActionCallbackListener<DishesCategory> listener);

	/**
	 * 删除菜品类别（标记删除）
	 *
	 * @param dishes_category_id 菜品类别id
	 * @param listener           回调监听器
	 */
	void dishes_category_delete(int dishes_category_id, ActionCallbackListener<Void> listener);

	/**
	 * 修改菜品类别
	 *
	 * @param dishes_category_id 菜品类别id
	 * @param category_desc      类别描述
	 * @param listener           回调监听器
	 */
	void dishes_category_update(int dishes_category_id, String category_desc, ActionCallbackListener<DishesCategory> listener);

	/**
	 * 查询菜品类别
	 *
	 * @param dishes_category_id 菜品类别id
	 * @param listener           回调监听器
	 */
	void dishes_category_query_by_id(int dishes_category_id, ActionCallbackListener<DishesCategory> listener);

	/**
	 * 查询店铺所有菜品类别
	 *
	 * @param shop_id  店铺id
	 * @param listener 回调监听器
	 */
	void dishes_category_query(int shop_id, ActionCallbackListener<List<DishesCategory>> listener);

	/**
	 * 新增订单
	 *
	 * @param user_id       用户id
	 * @param shop_id       店铺id
	 * @param address_id    收货地址
	 * @param create_time   创建时间
	 * @param total_price   订单总价
	 * @param note          备注
	 * @param orderItemList 订单项List
	 * @param listener      回调监听器
	 */
	void order_create(int user_id, int shop_id, int address_id, String create_time, float total_price, String note, List<OrderItem> orderItemList, ActionCallbackListener<Order> listener);

	/**
	 * 修改订单状态
	 *
	 * @param order_id       订单id
	 * @param order_state_id 订单状态id
	 * @param listener       回调监听器
	 */
	void order_update_state(int order_id, int order_state_id, ActionCallbackListener<Order> listener);

	/**
	 * 查询用户所有订单
	 *
	 * @param user_id  用户id
	 * @param listener 回调监听器
	 */
	void order_query_by_user(int user_id, ActionCallbackListener<List<Order>> listener);

	/**
	 * 查询店铺所有订单
	 *
	 * @param shop_id  店铺id
	 * @param listener 回调监听器
	 */
	void order_query_by_shop(int shop_id, ActionCallbackListener<List<Order>> listener);

	/**
	 * 新增评价
	 *
	 * @param order_id 订单id
	 * @param star     评价星级
	 * @param content  内容
	 * @param time     时间
	 * @param listener 回调监听器
	 */
	void evaluation_create(int order_id, int star, String content, String time, ActionCallbackListener<Evaluation> listener);

	/**
	 * 查看订单评价
	 *
	 * @param order_id 订单id
	 * @param listener 回调监听器
	 */
	void evaluation_query(int order_id, ActionCallbackListener<Evaluation> listener);

	/**
	 * 查看店铺所有订单评价
	 *
	 * @param shop_id  店铺id
	 * @param listener 回调监听器
	 */
	void evaluation_query_by_shop(int shop_id, ActionCallbackListener<List<Evaluation>> listener);

	/**
	 * 修改公告
	 *
	 * @param notice_id 公告id
	 * @param title     标题
	 * @param image_src 图片地址
	 * @param listener  回调监听器
	 */
	void notice_update(int notice_id, String title, String image_src, ActionCallbackListener<Void> listener);

	/**
	 * 查询所有公告
	 *
	 * @param listener 回调监听器
	 */
	void notice_query(ActionCallbackListener<List<Notice>> listener);

	/**
	 * 获取七牛图片上传的Token
	 *
	 * @param listener 回调监听器
	 */
	void common_get_qiniu_token(ActionCallbackListener<String> listener);

}
