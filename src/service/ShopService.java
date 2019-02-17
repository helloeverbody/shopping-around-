package service;


import java.util.List;

import pojo.Shop;

/**
 * 客户业务层接口
 *
 */
public interface ShopService {
	public List<Shop> shopFind(Shop shop); // 查找业务

	public int shopAdd(Shop shop); // 增加业务

	public boolean shopDel(Shop shop); // 删除业务

	public boolean shopSave(Shop shop); // 保存业务

	public int shopCount(Shop shop); // 查询数据总数


}
