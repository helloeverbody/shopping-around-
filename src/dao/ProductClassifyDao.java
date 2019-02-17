package dao;

import java.util.List;

import pojo.ProductClassify;


public interface ProductClassifyDao {

	public List<ProductClassify> productSelect(ProductClassify productClassify);// 查询

	public int productInsert(ProductClassify productClassify); // 添加成功返回id，否则返回-1

	public boolean productClassifyDelete(ProductClassify productClassify);// 删除

	public boolean productClassifyUpdate(ProductClassify productClassify);// 修改

	public boolean productClassifyDeleteByCondition(ProductClassify productClassify);// 删除通过condition

	public int productClassifyCount(ProductClassify productClassify); // 返回所查询总数

	public boolean operateBysql(String sql);  //操作通过sql



}
