package service.impl;

import java.util.List;

import dao.CustomerDao;
import dao.impl.CustomerDbcpDaoImpl;
import pojo.Customer;
import service.CustomerService;


/**
 * @Title: CustomerServiceImpl
 * @Description: 用户业务实现
 * @author yhfu
 * @date 2018年10月22日/上午10:13:16
 */
public class CustomerServiceImpl implements CustomerService {

	/**
	 * 查找客户业务
	 */
	public List<Customer> customerFind(Customer customer) {

		CustomerDao customerDao = new CustomerDbcpDaoImpl();

		return customerDao.customerSelect(customer);
	}

	/**
	 * 增加客户业务
	 */
	public int customerAdd(Customer customer) {
		
		CustomerDao customerDao = new CustomerDbcpDaoImpl();
		
		return customerDao.customerInsert(customer);
	}

	/**
	 * 删除客户业务
	 */
	public boolean customerDel(Customer customer) {
		CustomerDao customerDao = new CustomerDbcpDaoImpl();

		return customerDao.customerDelete(customer);
	}

	/**
	 * 删除通过condition
	 * @param customer
	 * @return
	 */
	public boolean customerDelByCondition(Customer customer) {

		CustomerDao customerDao = new CustomerDbcpDaoImpl();
		return customerDao.customerDeleteByCondition(customer);
	}

	/**
	 * 保存客户业务
	 */
	public boolean customerSave(Customer customer) {
		CustomerDao customerDao = new CustomerDbcpDaoImpl();

		return customerDao.customerUpdate(customer);
	}

	/**
	 * 批量删除业务
	 * @param ids
	 * @return
	 */
	public boolean customerBatchDel(int[] ids) {
		CustomerDao customerDao = new CustomerDbcpDaoImpl();

		return customerDao.customerBatchDelete(ids);
	}

	/**
	 * 查询客户总数业务
	 */
	public int customerCount(Customer customer) {
		CustomerDao customerDao = new CustomerDbcpDaoImpl();
		return customerDao.customerCount(customer);
	}

}
