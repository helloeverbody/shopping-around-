package service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.OrderDao;
import dao.OrderDetailDao;
import dao.impl.OrderDbcpDaoImpl;
import dao.impl.OrderDetailDbcpDaoImpl;
import pojo.Order;
import pojo.OrderDetail;
import service.OrderService;
import util.JDBCUtil;


/**
 * @Title: OrderServiceImpl
 * @Description: 用户业务实现
 * @author yhfu
 * @date 2018年10月22日/上午10:13:16
 */
public class OrderServiceImpl implements OrderService {

	//private Connection connection = null; // 数据库连接对象

	/**
	 * 查找订单业务
	 */
	public List<Order> orderFind(Order order) {
		List<Order> orderList = new ArrayList<Order>();
		String condition = order.getCondition();
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			OrderDao orderDao = new OrderDbcpDaoImpl(connection);
			String sql = "SELECT *,(SELECT COUNT(*) from order_detail WHERE orde_id = order_detail.ordeta_orde_id) as count "
					+ "FROM `order_detail`,`orders`  where 1=1 ";
			if (condition != null) { // 如果查询条件不为空，则通过查询条件查询
				sql += condition; // "and orde_id = ordeta_orde_id ORDER BY
									// ordeta_orde_id ";
				Integer limitInt = order.getLimit();
				Integer start = order.getPages();
				if(limitInt !=null && start !=null ){

					String sql1 = "SELECT sum(i) from (SELECT count(1) i FROM order_detail GROUP BY ordeta_orde_id  LIMIT "+0+","+(start)+") as c";
					String sql2 = "SELECT sum(i) from (SELECT count(1) i FROM order_detail GROUP BY ordeta_orde_id  LIMIT "+start+","+limitInt+") as c";;
					System.out.println("sql1 = " + sql1);
					System.out.println("sql2 = " + sql2);
					if(start!=0){
						start= orderDao.getInt(sql1);
					}
					limitInt = orderDao.getInt(sql2);
					sql += " limit " + start + "," + limitInt; // condition
				}

			} else {
				sql += "and orde_id = ordeta_orde_id ORDER BY ordeta_orde_id ";
			}

			System.out.println(sql);
			//OrderDao orderDao = new OrderDbcpDaoImpl(connection);

			orderList = orderDao.queryBySql(order, sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		return orderList;

	}

	/**
	 * 增加订单业务
	 */
	public int orderAdd(Order order) {
		
		int orde_id = -1;		//订单id
		String values ="";
		OrderDetail orderDetail = new OrderDetail();
		Connection connection = null;
		
		try {
			//获得数据库连接
			connection = JDBCUtil.getConnection();
			//开启事务
			connection.setAutoCommit(false);
			 
			
			
			OrderDao orderDao = new OrderDbcpDaoImpl(connection);
			orde_id =  orderDao.orderInsert(order);
			
			
			for(int i=0; i<order.getDetails().size(); i++){
				orderDetail = order.getDetails().get(i);
				String value = "("
					+orde_id+","
					+orderDetail.getOrdeta_prod_id()+","
						+ "\'" + orderDetail.getOrdeta_prod_name() + "\',"
					+orderDetail.getOrdeta_prod_price()+","
					+orderDetail.getOrdeta_prod_count()
					+")";
				values += value;
				if(i != order.getDetails().size()-1){
					values += ",";
				}
			}
			String sql = "insert into order_detail (ordeta_orde_id, ordeta_prod_id, ordeta_prod_name,ordeta_prod_price,ordeta_prod_count)"
					+ " values " + values; // 订单详情的增加sql语句
			
			OrderDetailDao orderDetailDao = new OrderDetailDbcpDaoImpl(connection);
			
			orderDetailDao.operateBySql(sql);
			if(orde_id == -1){		//插入失败回滚
				connection.rollback();
			}
			// int i = 1 / 0; // 模拟异常
			//提交事务
			connection.commit();
			
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			//出现异常之后就回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			return -1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		return orde_id;
	}

	/**
	 * 删除订单业务
	 */
	public boolean orderDel(Order order) {
		Connection connection = null;
		boolean result = false;
		try {
			connection = JDBCUtil.getConnection();
			// 开启事务
			connection.setAutoCommit(false);
			OrderDao orderDao = new OrderDbcpDaoImpl(connection);
			if (orderDao.orderDelete(order)) { // 删除订单成功
				OrderDetailDao orderDetailDao = new OrderDetailDbcpDaoImpl(connection);
				String condition = " and ordeta_orde_id = " + order.getOrde_id();
				orderDetailDao.orderDetailDeleteByCondition(condition);

				result = true;
			} else { // 删除失败
				connection.rollback(); // 回滚事务
				result = false;
			}

			// int i = 1 / 0; // 模拟程序出错
			connection.commit(); // 提交事务


		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				System.out.println("回滚");
				connection.rollback();
				result = false;

			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			return result;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 保存订单业务
	 */
	public boolean orderSave(Order order) {
		Connection connection = null;
		boolean result = false;
		try {
			connection = JDBCUtil.getConnection();
			// 开启事务
			connection.setAutoCommit(false);
			OrderDao orderDao = new OrderDbcpDaoImpl(connection);
			if(orderDao.orderUpdate(order)) {
				result = true;
			}			
//			if (orderDao.orderUpdate(order) && order.getDetails()!=null) { // 订单主表修改
//
//				for (int i = 0; i < order.getDetails().size(); i++) { // 订单详细表修改
//					OrderDetail orderDetail = new OrderDetail();
//					orderDetail = order.getDetails().get(i);
//					OrderDetailDao orderDetailDao = new OrderDetailDbcpDaoImpl(connection);
//					result = orderDetailDao.orderDetailUpdate(orderDetail);
//					if (!result) {
//						return result;
//					}
//				}
//
//				result = true;
//			}
			// 提交事务
			connection.commit();

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();

			try {
				// 事务回滚
				connection.rollback();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 查询订单总数业务
	 */
	public int orderCount(Order order) {
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			OrderDao orderDao = new OrderDbcpDaoImpl(connection);
			return orderDao.orderCount(order);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过condition删除
	 * @param order
	 * @return
	 */
	public boolean orderDelByCondition(Order order) {
		Connection connection = null;
		boolean flag = false;
		OrderDao orderDao;
		OrderDetailDao orderDetailDao;
		String orderCondition = order.getCondition(); //删除条件
		String detailCondition = orderCondition.replace("orde_id","ordeta_orde_id"); //删除条件
		System.out.println("orderCondition:"+orderCondition);
		System.out.println("detailCondition:"+detailCondition);

		try {
			connection = JDBCUtil.getConnection();
			connection.setAutoCommit(false);  //开启事务
			orderDao = new OrderDbcpDaoImpl(connection);
			orderDetailDao = new OrderDetailDbcpDaoImpl(connection);
			if(orderDao.orderDeleteByCondition(orderCondition)){
				if(!orderDetailDao.orderDetailDeleteByCondition(detailCondition)){
					connection.rollback();
					flag = false;
				}
			}else{
				connection.rollback();
			}
			connection.commit();              //提交事务


		} catch (SQLException e) {
			try {
				connection.rollback();  //回滚事务
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}


		return flag;
	}

	public static void main(String[] args) {
		// 查询
		// List<Order> orderList = new ArrayList<Order>();
		// Order order = new Order();
		// OrderService orderService = new OrderServiceImpl();
		//
		// orderList = orderService.orderFind(order);
		// System.out.println(orderList);
		//
		// for (Order order2 : orderList) {
		// System.out.println("orderDetail:" + order2.getDetails());
		// }

		// 增加
		// Order order = new Order();
		// order.setOrde_amount(100);
		// order.setOrde_cust_id(1);
		// order.setOrde_number("123123123");
		// order.setOrde_pay("1");
		// order.setOrde_shop_id(88);
		// order.setOrde_status("1");
		// List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		// for (int i = 0; i < 3; i++) {
		// OrderDetail orderDetail = new OrderDetail();
		// orderDetail.setOrdeta_prod_count(1);
		// orderDetail.setOrdeta_prod_id(5);
		// orderDetail.setOrdeta_prod_name("商品测试号5");
		// orderDetail.setOrdeta_prod_price(10);
		//
		// orderDetailList.add(orderDetail);
		// }
		//
		// order.setDetails(orderDetailList);
		//
		// OrderService orderService = new OrderServiceImpl();
		// int id = orderService.orderAdd(order);
		// System.out.println("id:" + id);

		// 删除
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		Order order = new Order();
		order.setOrde_id(2);
		order.setOrde_amount(100d);
		order.setOrde_cust_id(1);
		order.setOrde_head("http://pi886wsdq.bkt.clouddn.com/img_seach_shop_head.png");
		order.setOrde_name("一号店");
		order.setOrde_number("yixiugai");
		order.setOrde_pay("1");
		order.setOrde_shop_id(99);
		order.setOrde_status("1");
		for (int i = 0; i < 4; i++) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrdeta_prod_count(1);
			orderDetail.setOrdeta_prod_id(5);
			orderDetail.setOrdeta_prod_name("已修改6");
			orderDetail.setOrdeta_prod_price(10d);
			orderDetail.setOrdeta_id(i + 3);
			orderDetailList.add(orderDetail);
		}
		order.setDetails(orderDetailList);
		OrderService orderService = new OrderServiceImpl();

		// boolean isDelete = orderService.orderSave(order);

		// System.out.println("id:" + isDelete);
		String orderJson = new Gson().toJson(order);
		System.out.println(orderJson);


	}



}
