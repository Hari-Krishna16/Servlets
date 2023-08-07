package de.zeroco.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String USER_PASSWORD = "@Chakri007";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String rowName = request.getParameter("rowValue");
       String oldValue = request.getParameter("oldValue");
	   String newValue = request.getParameter("newValue");
	   PrintWriter writer = response.getWriter();
	   if(Utility.isBlank(rowName) && (Utility.isBlank(oldValue) && (Utility.isBlank(newValue)))) {
		   List<String> columns = Arrays.asList(rowName); 
	       List<String> values = Arrays.asList(oldValue,newValue);
	       Map<String, Object> tableData = DbUtility.get(DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD), "apm", "contacts_table", Arrays.asList(),
					columns.get(0), Arrays.asList(values.get(0)));
	       DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
	       if(!Utility.isBlank(tableData)) {
	    	   DbOperations.update(columns, values);
	    	   writer.print("updated sucesssfully");
	       }else {
	    	   writer.print("update failed");
	       }
	   }else {
		   writer.print("no data found");
	   }
      
       
	}

}
