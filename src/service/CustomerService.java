package service;

import java.util.List;

import pojo.Customer;


/**
 * 客户业务层接口
 *
 */
public interface CustomerService {
	public List<Customer> customerFind(Customer customer); // 查找业务

	public int customerAdd(Customer customer); // 增加业务

	public boolean customerDel(Customer customer); // 删除业务

	public boolean customerDelByCondition(Customer customer); // 删除业务


	public boolean customerBatchDel(int[] ids); // 批量删除业务

	public boolean customerSave(Customer customer); // 保存业务

	public int customerCount(Customer customer); // 查询数据总数

}
