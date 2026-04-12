package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demonstrates RequestDispatcher.include()
 * This servlet serves a small HTML fragment that can be included in other pages.
 */
public class NoticeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        // Note: When including, we don't send full HTML tags like <html> or <body>
        // because this fragment will be injected into an existing page.
        out.println("<div style='background: linear-gradient(135deg, #fef3c7, #fde68a); border: 1px solid #f59e0b; padding: 1.5rem; border-radius: 16px; margin: 2rem 4rem; text-align: center; box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);'>");
        out.println("  <div style='display: inline-block; background: #f59e0b; color: white; padding: 0.3rem 0.8rem; border-radius: 20px; font-size: 0.75rem; font-weight: 800; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 0.8rem;'>Health Notice</div>");
        out.println("  <h3 style='color: #92400e; font-size: 1.25rem; font-weight: 700; margin-bottom: 0.5rem;'>Free Flu Vaccination Drive</h3>");
        out.println("  <p style='color: #b45309; font-size: 0.95rem; font-weight: 500;'>Join us this Saturday from 8 AM to 4 PM in the main lobby for free vaccinations. Stay safe, stay healthy!</p>");
        out.println("</div>");
    }
}
