package com.project;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/management";
    private static final String USER = "root";
    private static final String PASSWORD = "akash";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String plainPassword = request.getParameter("password");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, plainPassword);

            
            rs = pstmt.executeQuery();

            
            if (rs.next()) {
                out.println("<html><body>");
                out.println("<h1>Login Successful</h1>");
                out.println("<p>Redirecting to Welcome page...</p>");
                out.println("</body></html>");
                response.sendRedirect("Welcome.html");
            } else {
                out.println("<html><body>");
                out.println("<h1>Login Failed</h1>");
                out.println("<p>Invalid email or password. Please try again.</p>");
                out.println("<a href='Login.html'>Go back to login page</a>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

