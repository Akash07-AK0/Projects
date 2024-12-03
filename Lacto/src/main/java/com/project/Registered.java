package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Registered extends HttpServlet {

  
    String url = "jdbc:mysql://localhost:3306/management";
    String user = "root";
    String password = "akash";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String plainPassword = request.getParameter("password");
        String phoneNumber = request.getParameter("number");
        String dob = request.getParameter("dob");

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");

          
            con = DriverManager.getConnection(url, user, password);

            
            String sql = "INSERT INTO users (name, email, password, number, dob) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, plainPassword);  
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, dob);

         
            pstmt.executeUpdate();
            
          
            response.sendRedirect("Welcome.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());

        } finally {
            
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

