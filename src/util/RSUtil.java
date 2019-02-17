package util;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.exception.DaoException;


public class RSUtil {

	/**
	 * 将数据反射到实体类中
	 * 
	 * @param rs
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> setMetaData(ResultSet rs, Class<T> clazz) {
		List<T> tList = new ArrayList<T>();
		T t = null;
		try {
			while (rs.next()) {
				t = clazz.newInstance();
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++) {
					String name = rsmd.getColumnName(i);
					Field field = clazz.getDeclaredField(name);
					field.setAccessible(true);
					field.set(t, rs.getObject(name));
				}
				tList.add(t);
			}
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException
				| IllegalArgumentException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
		}
		return tList;
	}

	public static <T> String createInsertSql(Class<T> clazz, String tableName) {
		String sql = "insert into " + tableName + "(";
		Field field[] = clazz.getDeclaredFields();
		String sqlEnd = "";
		for (int i = 1; i < field.length - 1; i++) {
			sql = sql + field[i].getName() + ",";
			sqlEnd = sqlEnd + "?,";
		}
		sql = sql + field[field.length - 1].getName() + ") values(" + sqlEnd + "?)";

		return sql;
	}

	public static <T> void insertPrepareStatement(PreparedStatement pst, T t) {
		Class<? extends Object> clazz = t.getClass();
		Field field[] = clazz.getDeclaredFields();
		try {
			for (int i = 1; i < field.length; i++) {
				field[i].setAccessible(true);
				pst.setObject(i, field[i].get(t));
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
		}
	}

	public static <T> String createUpdateSql(Class<T> clazz, String tableName) {
		String sql = "update " + tableName + " set ";
		Field field[] = clazz.getDeclaredFields();
		for (int i = 1; i < field.length - 1; i++) {
			sql = sql + field[i].getName() + "=?,";

		}
		sql = sql + field[field.length - 1].getName() + "=? where " + field[0].getName() + "=? ";

		return sql;
	}

	public static <T> void updatePrepareStatement(PreparedStatement pst, T t) {
		Class<? extends Object> clazz = t.getClass();
		Field field[] = clazz.getDeclaredFields();
		try {
			for (int i = 1; i < field.length; i++) {
				field[i].setAccessible(true);
				pst.setObject(i, field[i].get(t));
				field[0].setAccessible(true);
				pst.setObject(field.length, field[0].get(t));
			}
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new DaoException(e.getMessage(), e);
			}
	}

}
