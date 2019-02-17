package dao;


import java.util.List;

import pojo.Shop;

public interface ShopDao {

	public List<Shop> shopSelect(Shop shop);// 查询

	public int shopInsert(Shop shop);// 添加 添加成功返回id，否则返回-1

	public boolean shopDelete(Shop shop);// 删除

	public boolean shopUpdate(Shop shop);// 修改

	public int shopCount(Shop shop); // 返回所查询总数
}
