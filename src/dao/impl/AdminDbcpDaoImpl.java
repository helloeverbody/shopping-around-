
package dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import dao.AdminDao;
import dao.refector.AbstractDao;
import pojo.Admin;
import util.DButil;
import util.JDBCUtil;


/**
 * @Title: AdminDbcpDaoImpl
 * @Description: 管理员Dao层实现类
 * @author yhfu
 * @date 2018年10月24日 下午3:47:37
 */
public class AdminDbcpDaoImpl extends AbstractDao implements AdminDao {

	private QueryRunner runner = null;// 查询运行器

	public AdminDbcpDaoImpl() {
		// this.runner = new QueryRunner(); //无参构造，通过connection
		this.runner = new QueryRunner(JDBCUtil.getDataSource()); // 通过数据源构造
		
	}

	/**
	 * 查找 通过condition查找
	 * @return 实体类集合
	 */
	public List<Admin> adminSelect(Admin admin) {

		return super.select(admin, "admin", runner);

	}

	/**
	 * 插入
	 * @return 操作是否成功
	 */
	public int adminInsert(Admin admin) {

		return super.insert(admin,"admin", runner);
	}

	/**
	 * 通过id删除
	 * @return 返回操作是否成功
	 */
	public boolean adminDelete(Admin admin) {
		int id = admin.getAdmi_id();
		String idName = "admi_id";
		String tableName = "admin";
		return super.delete(id, tableName, idName, runner);
	}

	/**
	 * 更新数据通过id 根据admin中非空的属性更新
	 */
	public boolean adminUpdate(Admin admin) {
		return super.update(admin, "admin", "admi_id", runner);
	}

	/**
	 * 根据id 返回所查询记录总数
	 */
	public int adminCount(Admin admin) {

		String idName = "admi_id";
		String tableName = "admin";
		String condition = admin.getCondition();



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
