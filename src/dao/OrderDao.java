package dao;

import java.util.List;

import pojo.Order;


public interface OrderDao {

	public List<Order> orderSelect(Order order);// 查询

	public int orderInsert(Order order);// 添加 添加成功返回id，否则返回-1

	public boolean orderDelete(Order order);// 删除

	public boolean orderUpdate(Order order);// 修改

	public int orderCount(Order order); // 返回所查询总数

	public <T> List<T> queryBySql(T t, String sql);

	public List<Order> queryBySql(Order order, String sql);

	public boolean operateBySql(String sql);

	public boolean orderDeleteByCondition(String condition);// 删除通过condition

	public int getInt(String sql);
}
