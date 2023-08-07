package de.zeroco.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Contacts values = new Contacts();
		String userId = request.getParameter("userId");
		PrintWriter writer = response.getWriter();
		if(!Utility.isBlank(userId)) {
			int id = Integer.parseInt(userId);
			values.setId(id);
			Map<String, Object> tableData = DbOperations.get(values);
			if (!Utility.isBlank(tableData)) {
				  writer.println("<html><body><table border='1'>");
			        writer.println("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Contact</th><th>Email</th></tr>");
			        writer.println("<tr>");
			        writer.println("<td>" + tableData.get("pk_id") + "</td>");
			        writer.println("<td>" + tableData.get("first_name") + "</td>");
			        writer.println("<td>" + tableData.get("last_name") + "</td>");
			        writer.println("<td>" + tableData.get("contact") + "</td>");
			        writer.println("<td>" + tableData.get("email") + "</td>");
			        writer.println("</tr>");
			        writer.println("</table></body></html>");
			} else {
				writer.print("No data found for the given userId.");
			}
		}else {
			writer.print("no data found");
		}
	
	}
}
