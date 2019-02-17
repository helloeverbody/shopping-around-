
package dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import dao.OrderDetailDao;
import dao.refector.AbstractByConnDao;
import pojo.OrderDetail;
import util.JDBCUtil;


/**
 * Title: OrderDetailDbcpDaoImpl
 * 
 * @Description: 初步封装的JDBC工具类使用
 * @author yhfu
 * @date 2018年10月23日 上午9:09:13
 */
public class OrderDetailDbcpDaoImpl extends AbstractByConnDao implements OrderDetailDao {

	private QueryRunner runner = null;// 查询运行器
	private Connection connection = null; // 数据库连接对象

	public OrderDetailDbcpDaoImpl(Connection connection) {

		this.connection = connection; // 接收service穿过来的connect对象
		this.runner = new QueryRunner(); // 无参构造，通过connection
	}

	/**
	 * 查找 通过condition查找
	 * @return 实体类集合
	 */
	public List<OrderDetail> orderDetailSelect(OrderDetail orderDetail) {
		String tableName = "order_detail";
		return super.select(orderDetail, tableName, runner, connection);

	}

	/**
	 * 插入
	 * @return 操作是否成功
	 */
	public int orderDetailInsert(OrderDetail orderDetail) {
		String tableName = "order_detail";
		String prefix = "Ordeta"; // 字段前缀
		return super.insert(orderDetail, tableName, prefix, runner, connection);
	}

	/**
	 * 通过id删除
	 * @return 返回操作是否成功
	 */
	public boolean orderDetailDelete(OrderDetail orderDetail) {
		int id = orderDetail.getOrdeta_id();
		String idName = "ordeta_id";
		String tableName = "order_detail";
		return super.delete(id, tableName, idName, runner, connection);
	}

	/**
	 * 更新数据通过id 根据orderDetail中非空的属性更新
	 */
	public boolean orderDetailUpdate(OrderDetail orderDetail) {
		String idName = "ordeta_id";
		String tableName = "order_detail";
		String prefix = "Ordeta";
		return super.update(orderDetail, tableName, idName, prefix, runner, connection);
	}

	/**
	 * 根据id 返回所查询记录总数
	 */
	public int orderDetailCount(OrderDetail orderDetail) {

		String idName = "ordeta_id";
		String tableName = "order_detail";
		String condition = orderDetail.getCondition();
		return super.getAllCount(idName, tableName, condition, runner, connection);

	}

	/**
	 * 根据sql 操作
	 */
	public boolean operateBySql(String sql) {

		return super.operateBysql(sql, runner, connection);

	}

	/**
	 * 通过condition删除，适用批量删除
	 * @param orderDetail
	 * @return
	 */

	/**
	 * 通过condition删除
	 */
	public boolean orderDetailDeleteByCondition(String condition) {
		// TODO 自动生成的方法存根
		String tableName = "order_detail";
		return super.deleteByCondition(tableName, condition, runner, connection);
	}


}
