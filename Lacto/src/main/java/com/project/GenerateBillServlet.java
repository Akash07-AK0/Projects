package com.project;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GenerateBill")
public class GenerateBillServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

       
        String[] items = request.getParameterValues("item");
        double total = 0.0;

        
        out.println("<html><body>");
        out.println("<h1>Bill Summary</h1>");
        out.println("<ul>");

        if (items != null) {
            for (String item : items) {
               
                String[] parts = item.split(":");
                if (parts.length == 2) {
                    String itemName = parts[0];
                    double itemPrice = Double.parseDouble(parts[1]);
                    total += itemPrice;

                   
                    out.println("<li>" + itemName + " - $" + itemPrice + "</li>");
                }
            }
        }

        out.println("</ul>");
        out.println("<h2>Total: $" + total + "</h2>");
        out.println("</body></html>");
    }
}