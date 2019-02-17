package dao.impl.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CustomerDao;
import dao.impl.CustomerDaoImpl;
import pojo.Customer;
import util.JDBCUtil;


/**
 * Title: DBUtilsDaoTest
 * Description: 测试Dbutils类
 * @author yhfu
 * @date 2018年10月20日/下午10:55:52
 */
public class DBUtilsDaoTest {


	static void queryCustomerByConnection(CustomerDao customerDao, Customer customer) {
		List<Customer> customerList = new ArrayList<Customer>();
		customerList = customerDao.customerSelect(customer);
		System.out.println("customerList:" + customerList);
	}

	static int insertCustomer(CustomerDao customerDao, Customer customer) {
		return customerDao.customerInsert(customer);
	}

	static boolean deleteCustomer(CustomerDao customerDao, Customer customer) {
		return customerDao.customerDelete(customer);
	}

	static boolean updateCustomer(CustomerDao customerDao, Customer customer) {
		return customerDao.customerUpdate(customer);
	}

	public static void main(String[] args) throws SQLException {
		CustomerDao customerDao = new CustomerDaoImpl(JDBCUtil.getConnection());
		Customer customer = new Customer();
		customer.setCondition("");
		customer.setCust_name("测试");
		customer.setCust_address("美国");
		customer.setCust_sex(2);
		customer.setCust_account("123456");
		// customer.setCust_id(40);

		System.out.println("result: " + customerDao.customerInsert(customer));
		// QueryRunner runner = new QueryRunner(JDBCUtil.getDataSource());
		// String sql = "insert into customer(cust_sex) value(?) ";
		// Connection conn = null;
		// try {
		// conn = JDBCUtil.getConnection();
		// conn.setAutoCommit(true);
		// } catch (SQLException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }

		// try {
		// System.out.println(runner.insert(sql, new ScalarHandler<Object>(),
		// "d"));
		//
		// // int x = 1 / 0; // 模拟程序出错
		// // conn.commit();
		//
		// } catch (SQLException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// System.out.println("-1");
		//
		// // conn.rollback();
		//
		// }

		// 测试不通过数据源排序
		// queryCustomerByConnection(customerDao, customer);
		// 插入
		// System.out.println(insertCustomer(customerDao, customer));

		// 删除
		// System.out.println(deleteCustomer(customerDao, customer));

		// 输出修改语句
		// System.out.println(RSUtil.createUpdateSql(Customer.class,
		// "customer"));

		// 修改
		// System.out.println(updateCustomer(customerDao, customer));

	}

}
