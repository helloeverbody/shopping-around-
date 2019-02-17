package service;

import java.util.List;

import pojo.ShoppingCart;


/**
 * 商品业务层接口
 *
 */
public interface ShoppingCartService {

	public List<ShoppingCart> shoppingCartFind(ShoppingCart shoppingCart); // 查找业务

	public int shoppingCartAdd(ShoppingCart shoppingCart); // 增加业务

	public boolean shoppingCartDel(ShoppingCart shoppingCart); // 删除业务

	public boolean shoppingCartDelByCondition(ShoppingCart shoppingCart); // 删除业务
																			// 通过condition

	public boolean shoppingCartSave(ShoppingCart shoppingCart); // 保存业务

}
