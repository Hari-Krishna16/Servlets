package de.zeroco.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtility {

	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String USER_PASSWORD = "@Chakri007";
	public static final String REGISTER_DRIVER = "com.mysql.jdbc.Driver";

	public static void main(String[] args) throws SQLException {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
//		System.out.println(getGeneratedKey(connection, "zerocode", "students_table",
//				Arrays.asList("student_name", "student_city"), Arrays.asList("Mohan", "America")));
//		System.out.println(update("zerocode", "employee_table", Arrays.asList("employee_name"),
//				Arrays.asList("Bahubali"), "pk_id", 6));
//		System.out.println(delete(connection, "apm", "contacts_table", "pk_id", 3));
//		System.out.println(update(connection, "zerocode", "employee_table", Arrays.asList("employee_name"),
//				Arrays.asList("Bahubali"), "pk_id", 1));
//		System.out.println(list("zerocode", "employee_table", Arrays.asList()));
		DbUtility.closeConnection(connection);
	}

	/**
	 * this method is used to establish the connection between application and
	 * database
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param url
	 * @param user
	 * @param password
	 * @return connection
	 * @throws SQLException
	 */
	public static Connection getConnection(String url, String user, String password) {
		if ((Utility.isBlank(url) || Utility.isBlank(user)) || Utility.isBlank(password)) {
			return null;
		}
		Connection connect = null;
		try {
			Class.forName(REGISTER_DRIVER);
			connect = DriverManager.getConnection(DATABASE_URL, USER, USER_PASSWORD);
			connect.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connect;
	}

	/**
	 * this method are used to close the connection between application and database
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param connection
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (!connection.isClosed()) {
				connection.setAutoCommit(true);
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to insert the data into table and get the pk_id of values
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param connection
	 * @param schema
	 * @param tableName
	 * @param columns
	 * @param value
	 * @return value of pk_id
	 * @throws SQLException
	 */
	public static int getGeneratedKey(Connection connection, String schema, String tableName, List<String> columns,
			List<Object> value) {
		if ((Utility.isBlank(schema) || Utility.isBlank(tableName)) || (Utility.isBlank(columns))
				|| Utility.isBlank(value))
			return 0;
		PreparedStatement statement;
		int rowId = 0;
		try {
			statement = connection.prepareStatement(QueryBuilder.getInsertQuery(schema, tableName, columns),
					Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < columns.size(); i++) {
				statement.setObject(i + 1, value.get(i));
			}
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				rowId = resultSet.getInt(1);
			}
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowId;
	}

	/**
	 * this method is used to get the Table in listOf maps
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param schema
	 * @param tableName
	 * @param columns
	 * @return list of maps
	 * @throws NullPointerException
	 */
	public static List<Map<String, Object>> list(String schema, String tableName, List<String> columns) {
		if ((Utility.isBlank(schema) && Utility.isBlank(tableName)) && Utility.isBlank(columns))
			return null;
		List<Map<String, Object>> listOfMaps = new ArrayList<>();
		String columnName = "";
		Object columnValue = "";
		int countColumns = 0;
		Connection connection = null;
		try {
			connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
			PreparedStatement statement = connection
					.prepareStatement(QueryBuilder.getListQuery(schema, tableName, columns));
			ResultSet set = statement.executeQuery();
			ResultSetMetaData metaData = set.getMetaData();
			countColumns = metaData.getColumnCount();
			while (set.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= countColumns; i++) {
					columnName = metaData.getColumnName(i);
					columnValue = set.getObject(i);
					map.put(columnName, columnValue);
				}
				listOfMaps.add(map);
			}
			statement.close();
			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtility.closeConnection(connection);
		}

		return listOfMaps;
	}

	/**
	 * this method is used to get the data from the table in database
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param schema
	 * @param tableName
	 * @param columns
	 * @param conditionColumn
	 * @param values
	 * @return map
	 * @throws SQLException
	 */
	public static Map<String, Object> get(Connection connection, String schema, String tableName, List<String> columns,
			String conditionColumn, List<Object> values) {
		if ((Utility.isBlank(schema) || Utility.isBlank(tableName))
				|| (Utility.isBlank(conditionColumn) || (Utility.isBlank(values))))
			return null;
		Map<String, Object> map = new HashMap<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection
					.prepareStatement(QueryBuilder.getQuery(schema, tableName, columns, conditionColumn, values));
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					map.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
			}
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * this method is used to update values of a table in database
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param schema
	 * @param tableName
	 * @param columns
	 * @param values
	 * @param conditionColumn
	 * @param value
	 * @return number of rows updated
	 * @throws SQLException
	 */
	public static int update(Connection connection, String schema, String tableName, List<String> columns, List<String> values,
			String conditionColumn, Object value) {
		if ((Utility.isBlank(schema) && Utility.isBlank(tableName)) && (Utility.isBlank(columns))
				&& (Utility.isBlank(conditionColumn)) && (Utility.isBlank(values)) && (Utility.isBlank(value)))
			return 0;
		connection = getConnection(DATABASE_URL, USER, USER_PASSWORD);
		int effectedRows = 0;
		try {
			String query = QueryBuilder.getUpdateQuery(schema, tableName, columns, conditionColumn, value);
			PreparedStatement statement = connection.prepareStatement(query);
			for (int i = 0; i < values.size(); i++) {
				for (String key : values) {
					statement.setObject(i + 1, key);
				}
			}
			effectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return effectedRows;
	}

	/**
	 * this method is used to delete row data from a table in database
	 * 
	 * @author HariKrishna k
	 * @since 2023-07-20
	 * @param schema
	 * @param tableName
	 * @param conditionColumn
	 * @param value
	 * @return number of rows deleted
	 * @throws SQLException
	 */
	public static int delete(Connection connection, String schema, String tableName, String conditionColumn,
			Object value) {
		if ((Utility.isBlank(schema) || Utility.isBlank(tableName))
				|| (Utility.isBlank(conditionColumn) || (Utility.isBlank(value))))
			return 0;
		int rowsDeleted = 0;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(QueryBuilder.getDeleteQuery(schema, tableName, conditionColumn));
			statement.setObject(1, value);
			rowsDeleted = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowsDeleted;
	}

}