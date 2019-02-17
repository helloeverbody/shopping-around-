
package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.OrderDao;
import dao.refector.AbstractByConnDao;
import pojo.Order;
import pojo.OrderDetail;
import util.DateFormatUtil;
import util.JDBCUtil;

/**
 * Title: OrderDbcpDaoImpl
 *
 * @author yhfu
 * @Description: 初步封装的JDBC工具类使用
 * @date 2018年10月23日 上午9:09:13
 */
public class OrderDbcpDaoImpl extends AbstractByConnDao implements OrderDao {

    private QueryRunner runner = null;// 查询运行器
    private Connection connection = null; // 数据库连接对象

    public OrderDbcpDaoImpl(Connection connection) {

        this.connection = connection; // 接收service穿过来的connect对象
        this.runner = new QueryRunner(); // 无参构造，通过connection
    }

    /**
     * 查找 通过condition查找
     *
     * @return 实体类集合
     */
    public List<Order> orderSelect(Order order) {
        String tableName = "orders";
        return super.select(order, tableName, runner, connection);

    }

    /**
     * 插入
     *
     * @return 操作是否成功
     */
    public int orderInsert(Order order) {
        String tableName = "orders";
        String prefix = "Orde";

        return super.insert(order, tableName, prefix, runner, connection);
    }

    /**
     * 通过id删除
     *
     * @return 返回操作是否成功
     */
    public boolean orderDelete(Order order) {
        int id = order.getOrde_id();
        String idName = "orde_id";
        String tableName = "orders";
        return super.delete(id, tableName, idName, runner, connection);
    }

    /**
     * 更新数据通过id 根据order中非空的属性更新
     */
    public boolean orderUpdate(Order order) {
        String tableName = "orders";
        String prefix = "Orde";
        String idName = "orde_id";
        return super.update(order, tableName, idName, prefix, runner, connection);
    }

    /**
     * 根据id 返回所查询记录总数
     */
    public int orderCount(Order order) {

        String idName = "orde_id";
        String tableName = "orders,order_detail";
        String condition = order.getCondition();
        return super.getAllCount(idName, tableName, condition, runner, connection);

    }

    /**
     * 根据sql 返回所查询记录list
     */
    public <T> List<T> queryBySql(T t, String sql) {

        return super.queryBySql(t, sql, runner, connection);

    }

    /**
     * 根据sql 操作
     */
    public boolean operateBySql(String sql) {

        return super.operateBysql(sql, runner, connection);

    }

    /**
     * 通过condition删除，适用批量删除
     * @return
     */
    public boolean orderDeleteByCondition(String condition) {
        String tableName = "orders";
        return super.deleteByCondition(tableName,condition ,runner, connection);
    }

    /**
     * 根据sql 返回所查询记录list
     */
    public List<Order> queryBySql(Order order, String sql) {

        List<Order> orderList = new ArrayList<Order>();
        try {
            orderList = runner.query(connection, sql, new MyHandler());
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();

        }
        return orderList;

    }

    public int getInt(String sql){
        int i = -1;
        try {
        	System.out.println(runner.query(connection, sql, new ScalarHandler<>()).toString());
            i = Integer.parseInt(runner.query(connection, sql, new ScalarHandler<>()).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public class MyHandler implements ResultSetHandler<List<Order>> {
        @Override
        public List<Order> handle(ResultSet rs) throws SQLException {
            // 封装数据，数据从 Resultset 中获取
            List<Order> list = new ArrayList<Order>();

            while (rs.next()) {
                int length = rs.getInt("count");
                List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
                System.out.println("length:" + length);
                for (int i = 0; i < length; i++) {
                    //System.out.println("for run");
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrdeta_id(rs.getInt("ordeta_id"));
                    orderDetail.setOrdeta_orde_id(rs.getInt("ordeta_orde_id"));
                    orderDetail.setOrdeta_prod_id(rs.getInt("ordeta_prod_id"));
                    orderDetail.setOrdeta_prod_name(rs.getString("ordeta_prod_name"));
                    orderDetail.setOrdeta_prod_price(rs.getDouble("ordeta_prod_price"));
                    orderDetail.setOrdeta_prod_count(rs.getInt("ordeta_prod_count"));


                    orderDetailList.add(orderDetail);
                    System.out.println("循环"+i);
                    if (i == length - 1) {
                        System.out.println("break");
                        System.out.println(orderDetailList.toString());
                        break;
                    }
                    rs.next();
                }

                Order order = new Order();
                order.setOrde_id(rs.getInt("orde_id"));
                order.setOrde_number(rs.getString("orde_number"));
                order.setOrde_shop_id(rs.getInt("orde_shop_id"));
                order.setOrde_cust_id(rs.getInt("orde_cust_id"));
                order.setOrde_status(rs.getString("orde_status"));
                order.setOrde_amount(rs.getDouble("orde_amount"));
                order.setOrde_pay(rs.getString("orde_pay"));

                order.setOrde_head(rs.getString("orde_head"));
                order.setOrde_name(rs.getString("orde_name"));

                order.setOrde_create_time(DateFormatUtil.getDateFormat(rs.getTimestamp("orde_create_time")));
                order.setOrde_pay_status(rs.getString("orde_pay_status"));
                order.setOrde_receiver(rs.getString("orde_receiver"));
                order.setOrde_phone(rs.getString("orde_phone"));
                order.setDetails(orderDetailList);


                list.add(order);
            }
            return list;
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        connection.setAutoCommit(true);
        OrderDbcpDaoImpl orderDbcpDaoImpl = new OrderDbcpDaoImpl(connection);
        String sql = "SELECT sum(i) from (SELECT count(1) i FROM order_detail GROUP BY ordeta_orde_id  LIMIT 0,4) as c";
        int i = orderDbcpDaoImpl.getInt(sql);
        System.out.println("i = " + i);
    }



}
