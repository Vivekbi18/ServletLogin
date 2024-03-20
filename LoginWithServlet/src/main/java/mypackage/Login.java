package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/loginForm")
public class Login extends HttpServlet{
 @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
	 PrintWriter out = resp.getWriter();
     
	 String email = req.getParameter("email1");
	 String pass = req.getParameter("pass1");
	 
	 try {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servletcrud","root","2022");
		 PreparedStatement ps = connection.prepareStatement("SELECT * FROM register WHERE email = ? AND password = ?");
		 ps.setString(1, email);
		 ps.setString(2, pass);
		 
		 ResultSet rs = ps.executeQuery();
		 
		 if(rs.next()) {
			HttpSession session = req.getSession();
			session.setAttribute("user_name",rs.getString("name"));
			 resp.setContentType("text/html");
				RequestDispatcher rd = req.getRequestDispatcher("/Profile.jsp");
			 out.print("<h3 style ='color:green'>Login Successfully</h3>");
			rd.include(req, resp);
		 }
		 else {
			 resp.setContentType("text/html");
			 out.print("<h3 style ='color:red'>Incorrect username or password</h3>");
			 RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
			 rd.include(req, resp);
		 }
		 
		 
	} catch (Exception e) {
	out.print("Exception occured : "+e);
	}
	
	 
}
}
