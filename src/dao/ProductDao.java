package dao;

import java.util.List;

import pojo.Product;


public interface ProductDao {

	public List<Product> productSelect(Product product);// 查询

	public List<Product> prodShopSelect(Product product);// 商品和店铺查询

	public int productInsert(Product product);// 添加 添加成功返回id，否则返回-1

	public boolean productDelete(Product product);// 删除

	public boolean productDeleteByCondition(Product product);// 删除通过condition

	public boolean productUpdate(Product product);// 修改
	
	public int productCount(Product product); // 返回所查询总数
}
