
package dao.impl;

import java.util.List;

import com.google.gson.Gson;

import dao.CustomerDao;
import dao.refector.AbstractDao;
import pojo.Customer;
import util.JDBCUtil;

import org.apache.commons.dbutils.QueryRunner;


/**
 * Title: CustomerDbcpDaoImpl
 *
 * @author yhfu
 * @Description: 初步封装的JDBC工具类使用
 * @date 2018年10月23日 上午9:09:13
 */
public class CustomerDbcpDaoImpl extends AbstractDao implements CustomerDao {

    private QueryRunner runner = null;// 查询运行器

    public CustomerDbcpDaoImpl() {
        // this.runner = new QueryRunner(); //无参构造，通过connection
        this.runner = new QueryRunner(JDBCUtil.getDataSource()); // 通过数据源构造

    }

    /**
     * 查找 通过condition查找
     *
     * @return 实体类集合
     */
    public List<Customer> customerSelect(Customer customer) {

        return super.select(customer, "customer", runner);

    }

    /**
     * 插入
     *
     * @return 操作是否成功
     */
    public int customerInsert(Customer customer) {

        return super.insert(customer, "customer", runner);
    }

    /**
     * 通过id删除
     *
     * @return 返回操作是否成功
     */
    public boolean customerDelete(Customer customer) {
        int id = customer.getCust_id();
        String idName = "cust_id";
        String tableName = "customer";
        return super.delete(id, tableName, idName, runner);
    }
    /**
     * 通过condition删除
     * @return 返回操作是否成功
     */
    public boolean customerDeleteByCondition(Customer customer) {
        String tableName = "customer";
        return super.deleteByCondition(tableName,customer.getCondition(),runner);
    }

    /**
     * 通过id批量删除
     *
     * @param cust_ids
     * @return
     */
    public boolean customerBatchDelete(int[] cust_ids) {
        String idName = "cust_id";
        String tableName = "customer";
        return super.batchDelete(cust_ids, tableName, idName, runner);
    }

    /**
     * 更新数据通过id 根据customer中非空的属性更新
     */
    public boolean customerUpdate(Customer customer) {
        return super.update(customer, "customer", "cust_id", runner);
    }

    /**
     * 根据id 返回所查询记录总数
     */
    public int customerCount(Customer customer) {

        String idName = "cust_id";
        String tableName = "customer";
        String condition = customer.getCondition();
        return super.getAllCount(idName, tableName, condition, runner);

    }

    public static void main(String[] args) {
        String jsonString = "(1,2,3,4,5,6,7,8,9,10)";

        Customer customer = new Customer();
        customer.setCondition(" and cust_id in "+jsonString);
        CustomerDao customerDao = new CustomerDbcpDaoImpl();
        boolean result = customerDao.customerDeleteByCondition(customer);
        System.out.println("result:" + result);

    }

}
