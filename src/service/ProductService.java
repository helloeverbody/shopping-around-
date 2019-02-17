package service;

import java.util.List;

import pojo.Product;


/**
 * 商品业务层接口
 *
 */
public interface ProductService {
	public List<Product> productFind(Product product); // 查找业务

	public int productAdd(Product product); // 增加业务

	public boolean productDel(Product product); // 删除业务

	public boolean productDelByCondition(Product product); // 删除业务 通过condition

	public boolean productSave(Product product); // 保存业务

	public int productCount(Product product); // 查询数据总数

	public List<List<Product>> productShopFind(Product product); // 店铺，商品两表查询

}
