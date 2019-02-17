
package dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import dao.ProductDao;
import dao.refector.AbstractDao;
import pojo.Product;
import util.JDBCUtil;


/**
 * Title: ProductDbcpDaoImpl
 * 
 * @Description: 初步封装的JDBC工具类使用
 * @author yhfu
 * @date 2018年10月23日 上午9:14:44
 */
public class ProductDbcpDaoImpl extends AbstractDao implements ProductDao {

	private QueryRunner runner = null;// 查询运行器

	public ProductDbcpDaoImpl() {
		// this.runner = new QueryRunner(); //无参构造，通过connection
		this.runner = new QueryRunner(JDBCUtil.getDataSource()); // 通过数据源构造
		
	}

	/**
	 * 查找 通过condition查找
	 * @return 实体类集合
	 */
	public List<Product> productSelect(Product product) {

		return super.select(product, "product", runner);

	}

	/**
	 * 插入
	 * @return 操作是否成功
	 */
	public int productInsert(Product product) {

		return super.insert(product, "product", runner);
	}

	/**
	 * 通过id删除
	 * @return 返回操作是否成功
	 */
	public boolean productDelete(Product product) {
		int id = product.getProd_id();
		String idName = "prod_id";
		String tableName = "product";
		return super.delete(id, tableName, idName, runner);
	}

	/**
	 * 删除通过condition 可批量删除
	 * @param product
	 * @return
	 */
	public boolean productDeleteByCondition(Product product) {
		String tableName = "product";
		return super.deleteByCondition(tableName,product.getCondition(),runner);
	}

	/**
	 * 更新数据通过id 根据product中非空的属性更新
	 */
	public boolean productUpdate(Product product) {
		return super.update(product, "product", "prod_id", runner);
	}

	/**
	 * 根据id 返回所查询记录总数
	 */
	public int productCount(Product product) {

		String idName = "prod_id";
		String tableName = "product";
		String condition = product.getCondition();
		return super.getAllCount(idName, tableName, condition, runner);

	}

	@Override
	public List<Product> prodShopSelect(Product product) {

		String tableName = "product,shop";

		return super.select(product, tableName, runner);
	}
}
