package service;

import java.util.List;

import pojo.Order;


/**
 * 客户业务层接口
 *
 */
public interface OrderService {
	public List<Order> orderFind(Order order); // 查找业务

	public int orderAdd(Order order); // 增加业务

	public boolean orderDel(Order order); // 删除业务

	public boolean orderSave(Order order); // 保存业务

	public int orderCount(Order order); // 查询数据总数
	public boolean orderDelByCondition(Order order); 	// 删除业务 通过condition

}
