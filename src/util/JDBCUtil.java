package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;


/**
 * dbcp数据源
 *
 */
public class JDBCUtil {

	private static Properties prop1 = new Properties();
	private static String driverClass;
	private static DataSource myDataSource;

	private JDBCUtil() {
	}

	static {
		try {
			InputStream is1 = DButil.class.getClassLoader().getResourceAsStream("config.properties");
			prop1.load(is1);
			driverClass = prop1.getProperty("driverClass");
			Class.forName(driverClass);

			Properties prop2 = new Properties();

			InputStream is2 = JDBCUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			prop2.load(is2);
			myDataSource = BasicDataSourceFactory.createDataSource(prop2);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DataSource getDataSource() {
		return myDataSource;
	}

	public static Connection getConnection() throws SQLException {
		return myDataSource.getConnection();
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	
}
