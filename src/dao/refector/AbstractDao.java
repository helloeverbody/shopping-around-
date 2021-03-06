package dao.refector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Title: AbstractDao
 * Description: 
 * @author yhfu
 * @date 2018年10月21日/下午1:16:51
 */
public abstract class AbstractDao {

	/**
	 * @description 计算该表总数
	 * @param idName
	 * @param tableName
	 * @param contion:查询条件
	 * @param runner
	 * @return 返回该表总记录数 返回-1失败
	 */
	public int getAllCount(String idName, String tableName, String contion, QueryRunner runner) {
		return getAllCount(idName,tableName,contion,runner,null);
	}

	/**
	 * 通过查询条件查找，得到全部属性
	 * @param t	具体的实体类
	 * @param tableName 表名
	 * @param runner Dbutils中SQL语句的操作对象
	 * @return 实体类集合
	 */
	public <T> List<T> select(T t, String tableName, QueryRunner runner) {
		return select(t,tableName,runner,null);
	}
	
	
	/**
	 * 插入
	 * @param t
	 * @param tableName
	 * @param runner
	 * @return
	 */
	public <T> int insert(T t, String tableName, QueryRunner runner) {
		int id = -1; // 返回插入的id，默认-1: 插入失败；
		String sql = "insert into " + tableName;
		String sqlKey = "( ";
		String sqlValue = ") values( ";
		List<Object> objectList = new ArrayList<Object>();

		Class<? extends Object> clazz = t.getClass();
		Method[] methods = clazz.getMethods();

		Class<? extends Object> superClazz = t.getClass().getSuperclass();
		Method[] superMethods = superClazz.getMethods();

		List<Method> realMeathodList = new ArrayList<Method>();
		realMeathodList = Arrays.asList(methods);
		List<Method> realMeathodList2 = new ArrayList<Method>(realMeathodList);

		for (Method method1 : superMethods) {
			for (Iterator<Method> iterator = realMeathodList2.iterator(); iterator.hasNext();) {
				Method method2 = (Method) iterator.next();
				if (method1.getName().equals(method2.getName())) {
					iterator.remove();
				}
			}
		}

		String methodName = null;
		for (Method method : realMeathodList2) {
			methodName = method.getName().substring(0, 3);
			//System.out.println("methodName: " + method.getName());
			try {
				if (methodName.equals("get") && method.invoke(t) != null) {
					sqlKey = sqlKey + method.getName().substring(3).toLowerCase() + ",";
					sqlValue = sqlValue + "?,";
					objectList.add(method.invoke(t));
					// System.out.println("methodName: " + method.getName());
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		sqlKey = sqlKey.substring(0, sqlKey.length() - 1);
		sqlValue = sqlValue.substring(0, sqlValue.length() - 1) + ")";
		sql = sql + sqlKey + sqlValue;
		System.out.println(sql);
		Object[] objects = objectList.toArray();

		try {
			id = new Long(runner.insert(sql, new ScalarHandler<Long>(), objects)).intValue();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			id = -1;
			// throw new DaoException(e.getMessage(), e);
		}
		System.out.println("sql: " + sql);
		System.out.println("新增id：" + id);
		return id;
	}
	
	/**
	 * 删除，通过id
	 * 
	 * @param id
	 *            字段id
	 * @param idName
	 *            id字段名
	 * @param runner
	 *            DBUtils中SQL语句的操作对象
	 * @return boolean
	 */
	public boolean delete(int id, String tableName, String idName, QueryRunner runner) {
		return delete(id,tableName,idName,runner,null);
	}

	/**
	 * 批量删除
	 * @param tableName
	 * @param idName
	 * @param runner
	 * @return
	 */
	public boolean batchDelete(int[] ids, String tableName, String idName, QueryRunner runner) {
		String sql = "delete from " + tableName + " where " + idName + " = ?";
		System.out.println("sql: " + sql);
		Object[][] id = new Object[ids.length][];
		for(int i=0; i<ids.length; i++){
			id[i] = new Object[]{ids[i]};
		}
		try {

			int[] results = runner.batch(sql,id);
			/*for(int i=0; i<results.length; i++){
				System.out.printf(results[i]+" ");
			}*/
			return results.length>0;

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改,通过id
	 * @param t
	 * @param tableName
	 * @param runner
	 * @return
	 */
	public <T> boolean update(T t, String tableName, String idName, QueryRunner runner) {
		String sql = "update " + tableName + " set";
		String sqlValue = " ";
		String sqlUpdateBy = " where " + idName + "=? ";

		List<Object> objectList = new ArrayList<Object>();

		Class<? extends Object> clazz = t.getClass();
		Method[] methods = clazz.getMethods();

		Class<? extends Object> superClazz = t.getClass().getSuperclass();
		Method[] superMethods = superClazz.getMethods();

		List<Method> realMeathodList = new ArrayList<Method>();
		realMeathodList = Arrays.asList(methods);
		List<Method> realMeathodList2 = new ArrayList<Method>(realMeathodList);

		for (Method method1 : superMethods) {
			for (Iterator<Method> iterator = realMeathodList2.iterator(); iterator.hasNext();) {
				Method method2 = (Method) iterator.next();
				if (method1.getName().equals(method2.getName())) {
					iterator.remove();
				}
			}
		}

		String methodName = null;
		// 拼接获取id的方法名
		String getIdMethodName = "get" + idName.substring(0, 1).toUpperCase() + idName.substring(1);

		try {

			for (Method method : realMeathodList2) {
				methodName = method.getName().substring(0, 3);
				//System.out.println("methodName: " + method.getName());

				if (methodName.equals("get") && method.invoke(t) != null && !method.getName().equals(getIdMethodName)) {
					sqlValue = sqlValue + method.getName().substring(3).toLowerCase() + "=?,";
					objectList.add(method.invoke(t));
				}
			}
			Method getId = clazz.getMethod(getIdMethodName);
			int id = (int) getId.invoke(t);
			objectList.add(id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		// 去掉末尾的","
		sqlValue = sqlValue.substring(0, sqlValue.length() - 1);
		sql = sql + sqlValue + sqlUpdateBy;
		System.out.println(sql);
		String oString = null;

		Object[] objects = objectList.toArray();
		// for (int i = 0; i < objects.length; i++) {
		// oString = "\'" + objects[i].toString() + "\'";
		// objects[i] = oString;
		// }
		System.out.println("sql: " + sql);
		try {
			return runner.update(sql, objects) > 0;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
			// throw new DaoException(e.getMessage(), e);
		}
	}

	/**
	 * 删除，通过condition
	 * 

	 * @param runner
	 *            DBUtils中SQL语句的操作对象
	 * @return boolean
	 */
	public boolean deleteByCondition(String tableName, String condition, QueryRunner runner) {
		return deleteByCondition(tableName,condition,runner,null);
	}

	public <T> boolean update(T t, String tableName, String idName, String prefix, QueryRunner runner) {
		return update(t,tableName,idName,prefix,runner,null);
	}

	public <T> int insert(T t, String tableName, String prefix, QueryRunner runner) {
		return insert(t, tableName, prefix, runner, null);
	}
//***********************************通过connection****************************************


	/**
	 * @param idName
	 * @param tableName
	 * @param contion:查询条件
	 * @param runner
	 * @return 返回该表总记录数 返回-1失败
	 * @description 计算该表总数
	 */
	public int getAllCount(String idName, String tableName, String contion, QueryRunner runner, Connection connection) {
		String sql = "select count(DISTINCT " + idName + ") from " + tableName + " where 1=1 " + contion;
		System.out.println("sql: " + sql);
		int count = -1;
		try {

			if(connection ==null){
				count = new Long(runner.query(sql, new ScalarHandler<Long>())).intValue();
			}else{
				count = new Long(runner.query(connection, sql, new ScalarHandler<Long>())).intValue();
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return count;

	}

	/**
	 * 通过查询条件查找，得到全部属性
	 *
	 * @param t         具体的实体类
	 * @param tableName 表名
	 * @param runner    Dbutils中SQL语句的操作对象
	 * @return 实体类集合
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> select(T t, String tableName, QueryRunner runner, Connection connection) {

		String sql = "select * from " + tableName + " where 1=1 ";

		Class<? extends Object> clazz = t.getClass();
		try {
			Method getCondition = clazz.getMethod("getCondition");

			if (getCondition.invoke(t) != null && !getCondition.invoke(t).equals("")) {
				sql = sql + getCondition.invoke(t);
			}
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		System.out.println("sql: " + sql);
		try {
			// return runner.(JDBCUtil.getConnection(), sql, new
			// BeanListHandler<Customer>(Customer.class));
			if(connection == null){
				return runner.query(sql, new BeanListHandler<T>((Class<T>) clazz));
			}else{
				return runner.query(connection, sql, new BeanListHandler<T>((Class<T>) clazz));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			List<T> tList = new ArrayList<T>();
			return tList; // 返回空集合
		}

	}


	/**
	 * 插入
	 *
	 * @param t
	 * @param tableName
	 * @param prefix:字段前缀
	 * @param runner
	 * @return
	 */
	public <T> int insert(T t, String tableName, String prefix, QueryRunner runner, Connection connection) {
		int id = -1; // 返回插入的id，默认-1: 插入失败；
		String sql = "insert into " + tableName;
		String sqlKey = "( ";
		String sqlValue = ") values( ";
		List<Object> objectList = new ArrayList<Object>();

		Class<? extends Object> clazz = t.getClass();
		Method[] methods = clazz.getMethods();

		Class<? extends Object> superClazz = t.getClass().getSuperclass();
		Method[] superMethods = superClazz.getMethods();

		List<Method> realMeathodList = new ArrayList<Method>();
		realMeathodList = Arrays.asList(methods);
		List<Method> realMeathodList2 = new ArrayList<Method>(realMeathodList);

		for (Method method1 : superMethods) {
			for (Iterator<Method> iterator = realMeathodList2.iterator(); iterator.hasNext(); ) {
				Method method2 = (Method) iterator.next();
				if (method1.getName().equals(method2.getName())) {
					iterator.remove();
				}
			}
		}

		String methodName = null;
		for (Method method : realMeathodList2) {
			methodName = method.getName();
			//System.out.println("methodName: " + method.getName());
			try {
				if (methodName.contains("get" + prefix) && !methodName.contains("get" + prefix + "_id")
						&& method.invoke(t) != null) {
					sqlKey = sqlKey + method.getName().substring(3).toLowerCase() + ",";
					sqlValue = sqlValue + "?,";
					objectList.add(method.invoke(t));
					//System.out.println("methodName: " + method.getName());
					System.out.println(methodName + ":" + method.invoke(t));
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		sqlKey = sqlKey.substring(0, sqlKey.length() - 1);
		sqlValue = sqlValue.substring(0, sqlValue.length() - 1) + ")";
		sql = sql + sqlKey + sqlValue;
		System.out.println(sql);
		Object[] objects = objectList.toArray();

		try {



			if(connection == null ){
				id = new Long(runner.insert( sql, new ScalarHandler<Long>(), objects)).intValue();
			}else{
				id = new Long(runner.insert(connection, sql, new ScalarHandler<Long>(), objects)).intValue();
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			id = -1;
			// throw new DaoException(e.getMessage(), e);
		}
		System.out.println("sql: " + sql);
		System.out.println("新增id：" + id);
		return id;
	}


	/**
	 * 删除，通过id
	 *
	 * @param id         字段id
	 * @param tableName  id字段名
	 * @param idName
	 * @param runner     DBUtils中SQL语句的操作对象
	 * @param connection
	 * @return
	 */
	public boolean delete(int id, String tableName, String idName, QueryRunner runner, Connection connection) {
		String sql = "delete from " + tableName + " where " + idName + " = " + id;
		System.out.println("sql: " + sql);
		try {

			if(connection == null){
				return runner.update( sql) > 0;
			}else{
				return runner.update(connection, sql) > 0;
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改,通过id
	 *
	 * @param t
	 * @param tableName
	 * @param runner
	 * @return
	 */
	public <T> boolean update(T t, String tableName, String idName, String prefix, QueryRunner runner,
							  Connection connection) {
		String sql = "update " + tableName + " set";
		String sqlValue = " ";
		String sqlUpdateBy = " where " + idName + "=? ";

		List<Object> objectList = new ArrayList<Object>();

		Class<? extends Object> clazz = t.getClass();
		Method[] methods = clazz.getMethods();

		Class<? extends Object> superClazz = t.getClass().getSuperclass();
		Method[] superMethods = superClazz.getMethods();

		List<Method> realMeathodList = new ArrayList<Method>();
		realMeathodList = Arrays.asList(methods);
		List<Method> realMeathodList2 = new ArrayList<Method>(realMeathodList);

		for (Method method1 : superMethods) {
			for (Iterator<Method> iterator = realMeathodList2.iterator(); iterator.hasNext(); ) {
				Method method2 = (Method) iterator.next();
				if (method1.getName().equals(method2.getName())) {
					iterator.remove();
				}
			}
		}

		String methodName = null;
		// 拼接获取id的方法名
		String getIdMethodName = "get" + idName.substring(0, 1).toUpperCase() + idName.substring(1);

		try {

			for (Method method : realMeathodList2) {
				methodName = method.getName();
				//System.out.println("methodName: " + method.getName());

				if (methodName.contains("get" + prefix) && method.invoke(t) != null
						&& !method.getName().equals(getIdMethodName)) {
					sqlValue = sqlValue + method.getName().substring(3).toLowerCase() + "=?,";
					objectList.add(method.invoke(t));
					System.out.println(methodName + ":" + method.invoke(t));
				}
			}
			Method getId = clazz.getMethod(getIdMethodName);
			int id = (int) getId.invoke(t);
			objectList.add(id);
			System.out.println(objectList);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		// 去掉末尾的","
		sqlValue = sqlValue.substring(0, sqlValue.length() - 1);
		sql = sql + sqlValue + sqlUpdateBy;
		System.out.println(sql);
		String oString = null;

		Object[] objects = objectList.toArray();
		// for (int i = 0; i < objects.length; i++) {
		// oString = "\'" + objects[i].toString() + "\'";
		// objects[i] = oString;
		// }
		System.out.println("sql: " + sql);
		try {

			if(connection == null ){
				return runner.update( sql, objects) > 0;
			}else{
				return runner.update(connection, sql, objects) > 0;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
			// throw new DaoException(e.getMessage(), e);
		}
	}



	/**
	 * 删除，通过condition
	 *
	 * @param tableName
	 * @param condition
	 * @param runner     DBUtils中SQL语句的操作对象
	 * @param connection
	 * @return
	 */
	public boolean deleteByCondition(String tableName, String condition, QueryRunner runner, Connection connection) {
		String sql = "delete from " + tableName + " where 1=1 " + condition;
		System.out.println("sql: " + sql);
		try {

			if(connection == null){
				return runner.update( sql) > 0;
			}else{
				return runner.update(connection, sql) > 0;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 查询，通过sql
	 *
	 * @param t
	 * @param sql
	 * @param runner     DBUtils中SQL语句的操作对象
	 * @param connection
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySql(T t, String sql, QueryRunner runner, Connection connection) {
		System.out.println("sql: " + sql);
		Class<? extends Object> clazz = t.getClass();
		try {
			if(connection == null){
				return runner.query(sql, new BeanListHandler<T>((Class<T>) clazz));
			}else{
				return runner.query(connection, sql, new BeanListHandler<T>((Class<T>) clazz));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 操作，通过sql
	 *
	 * @param sql
	 * @param runner     DBUtils中SQL语句的操作对象
	 * @param connection
	 * @return
	 */
	public boolean operateBysql(String sql, QueryRunner runner, Connection connection) {
		System.out.println("sql: " + sql);
		try {

			if(connection == null){
				return runner.update(sql) > 0;
			}else {
				return runner.update(connection, sql) > 0;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 批量删除
	 * @param tableName
	 * @param idName
	 * @param runner
	 * @return
	 */
	public boolean batchDelete(int[] ids, String tableName, String idName, QueryRunner runner,Connection connection) {
		String sql = "delete from " + tableName + " where " + idName + " = ?";
		System.out.println("sql: " + sql);
		Object[][] id = new Object[ids.length][];
		for(int i=0; i<ids.length; i++){
			id[i] = new Object[]{ids[i]};
		}
		try {
			int[] results;
			if(connection == null){
				results = runner.batch(sql,id);
			}{
				results = runner.batch(connection,sql,id);
			}
			/*for(int i=0; i<results.length; i++){
				System.out.printf(results[i]+" ");
			}*/
			return results.length>0;

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		String sql = null ;
		for(int i=0; i<90; i++){
			sql += Integer.toString(4795+i)+",";
		}
		System.out.println(sql);
	}

}
