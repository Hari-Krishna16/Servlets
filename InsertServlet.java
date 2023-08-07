package de.zeroco.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertServlet extends HttpServlet {
	
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String USER_PASSWORD = "@Chakri007";
	public static final String REGISTER_DRIVER = "com.mysql.jdbc.Driver";
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse responce) throws IOException, ServletException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String contact = request.getParameter("contact");
		String email = request.getParameter("email");
		 PrintWriter writer = responce.getWriter();
		if(!Utility.isBlank(firstName) && !Utility.isBlank(lastName) && !Utility.isBlank(contact) &&  !Utility.isBlank(email)) {
		        Contacts values = new Contacts();
				values.setFirstName(firstName);
				values.setSecondName(lastName);
				values.setContact(contact);
				values.setEmail(email);
				Map<String, Object> tableData = DbUtility.get(DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD), "apm", "contacts_table", Arrays.asList(),
						"contact", Arrays.asList(values.getContact()));
				DbUtility.closeConnection(DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD));
				if(Utility.isBlank(tableData)){
					DbOperations.save(values);
					writer.print("user created");
				}else {
					writer.print("user exist");
				}
		 }else {
			 writer.print("data insufficient");
			 
		 }
		
	}
}
