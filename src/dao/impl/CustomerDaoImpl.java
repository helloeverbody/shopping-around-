package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CustomerDao;
import dao.exception.DaoException;
import pojo.Customer;
import util.DButil;
import util.RSUtil;



public class CustomerDaoImpl implements CustomerDao {
	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 定义构造方法，实例化的时候完成连接的注入
	 */
	public CustomerDaoImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询记录
	 * 
	 * @throws SQLException
	 */
	public List<Customer> customerSelect(Customer customer) {
		String sql = "select * from customer where 1=1 ";
		String condition = customer.getCondition();
		if (condition != null && !condition.equals("")) {
			sql += condition;
		}
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			customerList = RSUtil.setMetaData(rs, Customer.class);

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
		}
		return customerList;

	}

	/**
	 * 增加记录
	 * 
	 * @return 成功返回id，失败返回-1
	 */
	public int customerInsert(Customer customer) {
		int id = -1; // 主键，返回id 默认为-1；
		String sql = null;
			sql = RSUtil.createInsertSql(Customer.class, "customer");
			System.out.println(sql);
		try {
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			Object object = customer;
			RSUtil.insertPrepareStatement(pst, object);
			pst.executeUpdate();
			ResultSet resultSet = pst.getGeneratedKeys(); // 获取主键
			if (resultSet.next()) {
				id = resultSet.getInt(1); // 插入成功，返回id
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			// throw new DaoException(e.getMessage(), e);
		}

		return id;

	}


	/**
	 * 删除记录
	 */
	public boolean customerDelete(Customer customer) {
		String sql = "delete from customer where cust_id=?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, customer.getCust_id());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
		}

		return true;
	}

	@Override
	public boolean customerDeleteByCondition(Customer customer) {
		return false;
	}

	/**
	 * 批量删除记录
	 */
	public boolean customerBatchDelete(int[] cust_ids) {
		return false;
	}

	/**
	 * 修改记录
	 */
	public boolean customerUpdate(Customer customer) {
			String sql = RSUtil.createUpdateSql(Customer.class, "customer");
			System.out.println(sql);
		try {
			pst = conn.prepareStatement(sql);
			RSUtil.updatePrepareStatement(pst, customer);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
		}


	}



	public static void main(String[] args) {
		Connection connection = DButil.getConnection();
		CustomerDao custmerDao = new CustomerDaoImpl(connection);
		Customer customer = new Customer();
		customer.setCust_name("已修改");
		customer.setCust_sex(0);
		customer.setCust_account("0");
		customer.setCust_password("0");
		customer.setCust_address("0");
		customer.setCust_birthday("0");
		customer.setCust_head("0");
		customer.setCust_phone("0");
		customer.setCust_id(18);
		// List<Customer> customers = custmerDao.customerSelect(customer);
		// System.out.println(customers);
		
		
			try {
			if (custmerDao.customerInsert(customer) != -1) {
					connection.commit();
					System.out.println("success!");
				} else {
					System.out.println("false!");
				}
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();

			}



	}

	@Override
	public int customerCount(Customer customer) {
		String sql = "select count(*) from customer ";
		int count = -1; // 记录总数，初始值为-1
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			count = rs.getInt(1);

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
		}
		return count;

	}


}
