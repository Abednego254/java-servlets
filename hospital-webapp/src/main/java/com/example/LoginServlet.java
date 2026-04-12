package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        HtmlTemplate.renderHeader(request, out, "Staff Login | SanityCare", "login");

        out.println("  <div class='page-wrap'>");
        out.println("    <div class='form-card'>");
        out.println("      <h1>&#128274; Staff Login</h1>");
        out.println("      <p class='subtitle'>Access the protected staff directory and registration tools.</p>");
        out.println("      <hr class='divider'>");

        String error = request.getParameter("error");
        if ("invalid".equals(error)) {
            out.println("<p style='background:#fee2e2; border: 1px solid #ef4444; color:#b91c1c; padding: 1rem; border-radius: 12px; font-weight: 600; margin-bottom: 2rem;'>&#9888; Login Failed: Invalid username or password.</p>");
        } else if ("auth_required".equals(error)) {
            out.println("<p style='background:#fef3c7; border: 1px solid #f59e0b; color:#92400e; padding: 1rem; border-radius: 12px; font-weight: 600; margin-bottom: 2rem;'>&#128274; Authentication Required: Please login to continue.</p>");
        }

        out.println("      <form action='/hospital-webapp/login' method='post'>");
        out.println("        <div class='form-group'>");
        out.println("          <label for='username'>Username</label>");
        out.println("          <input type='text' id='username' name='username' placeholder='Username (try: admin)' required>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='password'>Password</label>");
        out.println("          <input type='password' id='password' name='password' placeholder='Enter global master password...' required>");
        out.println("        </div>");

        out.println("        <button type='submit' class='btn-submit'>Sign In &rarr;</button>");
        out.println("      </form>");
        out.println("    </div>");
        out.println("  </div>");

        try {
            HtmlTemplate.includeFooter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Validation: Username 'admin', Password from Global Context
        String globalKey = getServletContext().getInitParameter("globalAdminKey");

        if ("admin".equalsIgnoreCase(user) && globalKey != null && globalKey.equals(pass)) {
            // SUCCESS: Create session
            HttpSession session = request.getSession(); // Default is true (creates if not exists)
            session.setAttribute("user", "Administrator");
            
            System.out.println("==> LoginServlet: User logged in. Session ID: " + session.getId());
            response.sendRedirect("/hospital-webapp/staff");
        } else {
            // FAILURE: Redirect back
            response.sendRedirect("/hospital-webapp/login?error=invalid");
        }
    }
}
