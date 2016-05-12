package net.xuwenhui.api;

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
 * Api接口
 * <p/>
 * Created by xwh on 2016/3/22.
 */
public interface Api {

	/**
	 * 登录
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @return
	 */
	ApiResponse<User> user_login(String phone_num, String password);

	/**
	 * 注册
	 *
	 * @param phone_num 手机号
	 * @param password  密码
	 * @param role_id   角色（2：用户，3：商家）
	 * @return
	 */
	ApiResponse<Void> user_register(String phone_num, String password, int role_id);

	/**
	 * 忘记密码
	 *
	 * @param phone_num    手机号
	 * @param new_password 新密码
	 * @return
	 */
	ApiResponse<Void> user_forget_password(String phone_num, String new_password);

	/**
	 * 修改密码
	 *
	 * @param phone_num    手机号
	 * @param old_password 旧密码
	 * @param new_password 新密码
	 * @return
	 */
	ApiResponse<Void> user_update_password(String phone_num, String old_password, String new_password);

	/**
	 * 修改信息
	 *
	 * @param user_id   用户id
	 * @param image_src 图片地址
	 * @param nickname  昵称
	 * @return
	 */
	ApiResponse<User> user_update_info(int user_id, String image_src, String nickname);

	/**
	 * 审核商户
	 *
	 * @param user_id  商家id
	 * @param is_valid 审核结果
	 * @return
	 */
	ApiResponse<Void> user_check_merchant(int user_id, boolean is_valid);

	/**
	 * 查询所有普通用户
	 *
	 * @return
	 */
	ApiResponse<List<User>> user_query_user();

	/**
	 * 查询所有商家用户
	 *
	 * @return
	 */
	ApiResponse<List<User>> user_query_merchant();

	/**
	 * 新增收货地址
	 *
	 * @param user_id      用户id
	 * @param name         姓名
	 * @param sex          性别
	 * @param phone_num    手机号
	 * @param address_desc 地址描述
	 * @param note         备注
	 * @return
	 */
	ApiResponse<Address> address_create(int user_id, String name, String sex, String phone_num, String address_desc, String note);

	/**
	 * 删除收货地址（标记删除）
	 *
	 * @param address_id 地址id
	 * @return
	 */
	ApiResponse<Void> address_delete(int address_id);

	/**
	 * 修改收货地址
	 *
	 * @param address_id   地址id
	 * @param name         姓名
	 * @param sex          性别
	 * @param phone_num    手机号
	 * @param address_desc 地址描述
	 * @param note         备注
	 * @return
	 */
	ApiResponse<Address> address_update(int address_id, String name, String sex, String phone_num, String address_desc, String note);

	/**
	 * 根据id查询收货地址
	 *
	 * @param address_id 地址id
	 * @return
	 */
	ApiResponse<Address> address_query_by_id(int address_id);

	/**
	 * 查询用户所有收货地址
	 *
	 * @param user_id 用户id
	 * @return
	 */
	ApiResponse<List<Address>> address_query(int user_id);

	/**
	 * 新增店铺
	 *
	 * @param user_id      商家id
	 * @param sort_id      类别id
	 * @param name         名称
	 * @param image_src    图片地址
	 * @param address_desc 地址描述
	 * @return
	 */
	ApiResponse<Shop> shop_create(int user_id, int sort_id, String name, String image_src, String address_desc);

	/**
	 * 修改店铺基本信息
	 *
	 * @param shop_id      店铺id
	 * @param sort_id      类别id
	 * @param name         名称
	 * @param image_src    图片地址
	 * @param address_desc 地址描述
	 * @return
	 */
	ApiResponse<Shop> shop_update_info(int shop_id, int sort_id, String name, String image_src, String address_desc);

	/**
	 * 修改店铺公告
	 *
	 * @param shop_id 店铺id
	 * @param notice  公告
	 * @return
	 */
	ApiResponse<Shop> shop_update_notice(int shop_id, String notice);

	/**
	 * 查询商户的店铺
	 *
	 * @param user_id 商户id
	 * @return
	 */
	ApiResponse<Shop> shop_query_by_user(int user_id);

	/**
	 * 查询店铺公告
	 *
	 * @param shop_id 店铺id
	 * @return
	 */
	ApiResponse<String> shop_query_notice(int shop_id);

	/**
	 * 查询店铺商家电话
	 *
	 * @param shop_id 店铺id
	 * @return
	 */
	ApiResponse<String> shop_query_phone(int shop_id);

	/**
	 * 查询所有店铺
	 *
	 * @return
	 */
	ApiResponse<List<Shop>> shop_query();

	/**
	 * 新增菜品
	 *
	 * @param shop_id     店铺id
	 * @param category_id 类别id
	 * @param name        名称
	 * @param image_src   图片地址
	 * @param price       价格
	 * @return
	 */
	ApiResponse<Dishes> dishes_create(int shop_id, int category_id, String name, String image_src, float price);

	/**
	 * 删除菜品（标记删除）
	 *
	 * @param dishes_id 菜品id
	 * @return
	 */
	ApiResponse<Void> dishes_delete(int dishes_id);

	/**
	 * 修改菜品
	 *
	 * @param dishes_id   菜品id
	 * @param category_id 类别id
	 * @param name        姓名
	 * @param image_src   图片地址
	 * @param price       价格
	 * @return
	 */
	ApiResponse<Dishes> dishes_update(int dishes_id, int category_id, String name, String image_src, float price);

	/**
	 * 查询店铺所有菜品
	 *
	 * @param shop_id 店铺id
	 * @return
	 */
	ApiResponse<List<Dishes>> dishes_query(int shop_id);

	/**
	 * 新增菜品类别
	 *
	 * @param shop_id       店铺id
	 * @param category_desc 类别描述
	 * @return
	 */
	ApiResponse<DishesCategory> dishes_category_create(int shop_id, String category_desc);

	/**
	 * 删除菜品类别（标记删除）
	 *
	 * @param dishes_category_id 菜品类别id
	 * @return
	 */
	ApiResponse<Void> dishes_category_delete(int dishes_category_id);

	/**
	 * 修改菜品类别
	 *
	 * @param dishes_category_id 菜品类别id
	 * @param category_desc      类别描述
	 * @return
	 */
	ApiResponse<DishesCategory> dishes_category_update(int dishes_category_id, String category_desc);

	/**
	 * 查询菜品类别
	 *
	 * @param dishes_category_id 菜品类别id
	 * @return
	 */
	ApiResponse<DishesCategory> dishes_category_query_by_id(int dishes_category_id);

	/**
	 * 查询店铺所有菜品类别
	 *
	 * @param shop_id 店铺id
	 * @return
	 */
	ApiResponse<List<DishesCategory>> dishes_category_query(int shop_id);

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
	 * @return
	 */
	ApiResponse<Order> order_create(int user_id, int shop_id, int address_id, String create_time, float total_price, String note, List<OrderItem> orderItemList);

	/**
	 * 修改订单状态
	 *
	 * @param order_id       订单id
	 * @param order_state_id 订单状态id
	 * @return
	 */
	ApiResponse<Order> order_update_state(int order_id, int order_state_id);

	/**
	 * 查询用户所有订单
	 *
	 * @param user_id   用户id
	 * @return
	 */
	ApiResponse<List<Order>> order_query_by_user(int user_id);

	/**
	 * 查询店铺所有订单
	 *
	 * @param shop_id   店铺id
	 * @return
	 */
	ApiResponse<List<Order>> order_query_by_shop(int shop_id);

	/**
	 * 新增评价
	 *
	 * @param order_id 订单id
	 * @param star     评价星级
	 * @param content  内容
	 * @param time     时间
	 * @return
	 */
	ApiResponse<Evaluation> evaluation_create(int order_id, int star, String content, String time);

	/**
	 * 查看订单评价
	 *
	 * @param order_id 订单id
	 * @return
	 */
	ApiResponse<Evaluation> evaluation_query(int order_id);

	/**
	 * 查看店铺所有订单评价
	 *
	 * @param shop_id 店铺id
	 * @return
	 */
	ApiResponse<List<Evaluation>> evaluation_query_by_shop(int shop_id);

	/**
	 * 修改公告
	 *
	 * @param notice_id 公告id
	 * @param title     标题
	 * @param image_src 图片地址
	 * @return
	 */
	ApiResponse<Void> notice_update(int notice_id, String title, String image_src);

	/**
	 * 查询所有公告
	 *
	 * @return
	 */
	ApiResponse<List<Notice>> notice_query();

	/**
	 * 获取七牛图片上传的Token
	 *
	 * @return
	 */
	ApiResponse<String> common_get_qiniu_token();
}
