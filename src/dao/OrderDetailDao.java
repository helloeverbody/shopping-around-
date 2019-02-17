package dao;

import java.util.List;

import pojo.OrderDetail;


public interface OrderDetailDao {

	public List<OrderDetail> orderDetailSelect(OrderDetail orderDetail);// 查询

	public int orderDetailInsert(OrderDetail orderDetail);// 添加 添加成功返回id，否则返回-1

	public boolean orderDetailDelete(OrderDetail orderDetail);// 删除

	public boolean orderDetailDeleteByCondition(String condition);// 通过condition删除

	public boolean orderDetailUpdate(OrderDetail orderDetail);// 修改

	public int orderDetailCount(OrderDetail orderDetail); // 返回所查询总数

	public boolean operateBySql(String sql); // 执行sql

}
