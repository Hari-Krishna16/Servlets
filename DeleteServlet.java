package de.zeroco.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String USER_PASSWORD = "@Chakri007";
	public static final String REGISTER_DRIVER = "com.mysql.jdbc.Driver";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		int id = Integer.parseInt(userId);
		PrintWriter writer = response.getWriter();
		Contacts values = new Contacts();
		values.setId(id);
		Map<String, Object> tableData = DbUtility.get(DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD), "apm", "contacts_table", Arrays.asList("pk_id"),
				"pk_id", Arrays.asList(values.getId()));
		DbUtility.closeConnection(DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD));
		if ((!Utility.isBlank(tableData))) {
			DbOperations.delete(values);
			writer.print("deleted sucessfully");
		} else {
			writer.print("Enter Valid User Id");
		}

	}

}
