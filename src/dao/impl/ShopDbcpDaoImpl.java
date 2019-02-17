
package dao.impl;

import org.apache.commons.dbutils.QueryRunner;

import dao.ShopDao;
import dao.refector.AbstractDao;
import pojo.Shop;
import util.JDBCUtil;

import java.util.List;

/**
 * @Title: ShopDbcpDaoImpl
 * @Description: 管理员Dao层实现类
 * @author yhfu
 * @date 2018年10月24日 下午3:47:37
 */
public class ShopDbcpDaoImpl extends AbstractDao implements ShopDao {

	private QueryRunner runner = null;// 查询运行器

	public ShopDbcpDaoImpl() {
		// this.runner = new QueryRunner(); //无参构造，通过connection
		this.runner = new QueryRunner(JDBCUtil.getDataSource()); // 通过数据源构造
		
	}

	/**
	 * 查找 通过condition查找
	 * @return 实体类集合
	 */
	public List<Shop> shopSelect(Shop shop) {

		return super.select(shop, "shop", runner);

	}

	/**
	 * 插入
	 * @return 操作是否成功
	 */
	public int shopInsert(Shop shop) {

		return super.insert(shop, "shop", runner);
	}

	/**
	 * 通过id删除
	 * @return 返回操作是否成功
	 */
	public boolean shopDelete(Shop shop) {
		int id = shop.getShop_id();
		String idName = "shop_id";
		String tableName = "shop";
		return super.delete(id, tableName, idName, runner);
	}

	/**
	 * 更新数据通过id 根据shop中非空的属性更新
	 */
	public boolean shopUpdate(Shop shop) {
		return super.update(shop, "shop", "shop_id", runner);
	}

	/**
	 * 根据id 返回所查询记录总数
	 */
	public int shopCount(Shop shop) {

		String idName = "shop_id";
		String tableName = "shop";
		String condition = shop.getCondition();



		return super.getAllCount(idName, tableName, condition, runner);

	}

	public static void main(String[] args) {
		Object[][] params = new Object[10][];
		for(int i=0;i<10;i++){
			params[i] = new Object[]{i,"batch"};
		}
		for (Object[] objects :params) {
			for (int i=0; i<objects.length; i++) {
				System.out.printf(objects[i].toString());
			}
			System.out.println();
		}
	}



}
