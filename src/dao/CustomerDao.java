package dao;

import java.util.List;

import pojo.Customer;


public interface CustomerDao {

	public List<Customer> customerSelect(Customer customer);// 查询

	public int customerInsert(Customer customer);// 添加 添加成功返回id，否则返回-1

	public boolean customerDelete(Customer customer);// 删除

	public boolean customerDeleteByCondition(Customer customer);// 删除通过condition

	public boolean customerBatchDelete(int[] cust_ids);// 批量删除

	public boolean customerUpdate(Customer customer);// 修改

	public int customerCount(Customer customer); // 返回所查询总数
}
