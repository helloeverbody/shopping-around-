package service;

import java.util.List;

import pojo.OrderDetail;


/**
 * 客户业务层接口
 *
 */
public interface OrderDetailService {
	public List<OrderDetail> orderDetailFind(OrderDetail orderDetail); // 查找业务

	public int orderDetailAdd(OrderDetail orderDetail); // 增加业务

	public boolean orderDetailDel(OrderDetail orderDetail); // 删除业务

	public boolean orderDetailSave(OrderDetail orderDetail); // 保存业务

	public int orderDetailCount(OrderDetail orderDetail); // 查询数据总数

}
