package de.zeroco.servlets;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DbOperations {

	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String USER_PASSWORD = "@Chakri007";
	public static final String REGISTER_DRIVER = "com.mysql.jdbc.Driver";
	
	public static int save(Contacts values) {
		int id = 0;
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		id = DbUtility.getGeneratedKey(connection, "apm", "contacts_table",
				Arrays.asList("first_name", "last_name", "contact", "email"),
				Arrays.asList(values.getFirstName(), values.getSecondName(), values.getContact(), values.getEmail()));
		DbUtility.closeConnection(connection);
		return id;
	}

	public static Object delete(Contacts values) {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		DbUtility.delete(connection, "apm", "contacts_table", "pk_id", values.getId());
		DbUtility.closeConnection(connection);
		return null;
	}

	public static String update(List<String> columns, List<String> values) {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		Map<String, Object> tableData = DbUtility.get(connection, "apm", "contacts_table", Arrays.asList(),
				columns.get(0), Arrays.asList(values.get(0)));
		DbUtility.update(connection, "apm", "contacts_table", Arrays.asList(columns.get(0)),
				Arrays.asList(values.get(1)), "pk_id", tableData.get("pk_id"));
		DbUtility.closeConnection(connection);
		return null;
	}

	public static Map<String, Object> get(Contacts values) {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		Map<String, Object> tableData = DbUtility.get(connection, "apm", "contacts_table", Arrays.asList(), "pk_id",
				Arrays.asList(values.getId()));
		DbUtility.closeConnection(connection);
		if ((Utility.isBlank(tableData))) {
			return null;
		} else {
			return tableData;
		}
	}
	
	public static List<Map<String, Object>> list(){
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
	    List<Map<String, Object>> tableData = DbUtility.list("apm", "contacts_table", Arrays.asList());
	    DbUtility.closeConnection(connection);
		if ((Utility.isBlank(tableData))) {
			return null;
		} else {
			return tableData;
		}
	}
}
