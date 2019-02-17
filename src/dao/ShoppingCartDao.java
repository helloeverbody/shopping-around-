package dao;

import java.util.List;

import pojo.ShoppingCart;


public interface ShoppingCartDao {

	public List<ShoppingCart> productSelect(ShoppingCart shoppingCart);// 查询

	public int productInsert(ShoppingCart shoppingCart); // 添加成功返回id，否则返回-1

	public boolean shoppingCartDelete(ShoppingCart shoppingCart);// 删除

	public boolean shoppingCartDeleteByCondition(ShoppingCart shoppingCart);// 删除通过condition

	public boolean shoppingCartUpdate(ShoppingCart shoppingCart);// 修改
	
}
