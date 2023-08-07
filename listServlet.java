package de.zeroco.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class listServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException {
		List<Map<String, Object>> tableData = DbOperations.list();
		PrintWriter writer = responce.getWriter();
		   if (!Utility.isBlank(tableData)) {
		        writer.println("<html><body><table border='1'>");
		        writer.println("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Contact</th><th>Email</th></tr>");
		        for (Map<String, Object> rowData : tableData) {
		            writer.println("<tr>");
		            writer.println("<td>" + rowData.get("pk_id") + "</td>");
		            writer.println("<td>" + rowData.get("first_name") + "</td>");
		            writer.println("<td>" + rowData.get("last_name") + "</td>");
		            writer.println("<td>" + rowData.get("contact") + "</td>");
		            writer.println("<td>" + rowData.get("email") + "</td>");
		            writer.println("</tr>");
		        }
		        writer.println("</table></body></html>");
		    } else {
		        writer.print("NO data found");
		    }
	}
}
