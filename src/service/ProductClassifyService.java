package service;

import java.util.List;

import pojo.ProductClassify;


/**
 * 商品业务层接口
 *
 */
public interface ProductClassifyService {

	public List<ProductClassify> productClassifyFind(ProductClassify productClassify); // 查找业务

	public int productClassifyAdd(ProductClassify productClassify); // 增加业务

	public boolean productClassifyDel(ProductClassify productClassify); // 删除业务

	public boolean productClassifySave(ProductClassify productClassify); // 保存业务

	public boolean productClassifyDelByCondition(ProductClassify productClassify); 	// 删除业务 通过condition

	public int productClassifyCount(ProductClassify productClassify); 				// 查询数据总数

}
