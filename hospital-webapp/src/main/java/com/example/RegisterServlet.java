package com.example;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demonstrates ServletConfig
 * This servlet reads a 'secret password' from web.xml to restrict registration.
 */
@WebServlet(urlPatterns = {"/register"}, initParams = {
    @WebInitParam(name = "registrationSecret", value = "Sanity2026"),
    @WebInitParam(name = "pageName", value = "Staff Registration | SanityCare"),
    @WebInitParam(name = "pageHeader", value = "Hospital Staff Enrollment")
})
public class RegisterServlet extends HttpServlet {

    private String secretKey;
    private String pageName;
    private String pageHeader;
    private StaffService staffService = new StaffServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // FETCH GLOBAL CONFIGURATION from ServletContext
        this.secretKey = config.getServletContext().getInitParameter("globalAdminKey");
        this.pageName = config.getInitParameter("pageName");
        this.pageHeader = config.getInitParameter("pageHeader");
        
        System.out.println("==> RegisterServlet: Loaded global key from ServletContext.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // NOTE: Session protection is now handled centrally by AuthenticationFilter.
        // No manual session check needed here.

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Use the title from ServletConfig if available
        String title = (this.pageName != null) ? this.pageName : "Staff Registration | SanityCare";
        HtmlTemplate.renderHeader(request, out, title, "register");

        out.println("  <div class='page-wrap'>");
        out.println("    <div class='form-card'>");
        
        // Use the header from ServletConfig if available
        String header = (this.pageHeader != null) ? this.pageHeader : "&#128100; Staff Registration";
        out.println("      <h1>" + header + "</h1>");
        out.println("      <p class='subtitle'>Please enter the details for the new staff member below.</p>");
        out.println("      <hr class='divider'>");

        // If there was an error in a previous attempt, show it
        String error = request.getParameter("error");
        if ("wrong_key".equals(error)) {
            out.println("<p style='background:#fee2e2; border: 1px solid #ef4444; color:#b91c1c; padding: 1rem; border-radius: 12px; font-weight: 600; margin-bottom: 2rem;'>&#9888; Access Denied: Incorrect Secret Key.</p>");
        }

        out.println("      <form action='/hospital-webapp/register' method='post'>");
        out.println("        <div class='form-group'>");
        out.println("          <label for='fullName'>Full Name</label>");
        out.println("          <input type='text' id='fullName' name='fullName' placeholder='e.g. Dr. John Doe' required>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='email'>Email Address</label>");
        out.println("          <input type='email' id='email' name='email' placeholder='e.g. j.doe@sanitycare.com' required>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='secret'>Secret Registration Key</label>");
        out.println("          <input type='password' id='secret' name='secret' placeholder='Enter the HR password...' required>");
        out.println("          <p style='font-size: 0.75rem; color: #64748b; margin-top: 0.5rem;'>Hint: Enter the global master password (" + this.secretKey + ")</p>");
        out.println("        </div>");

        out.println("        <button type='submit' class='btn-submit'>Complete Registration &rarr;</button>");
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

        // NOTE: Session protection is now handled centrally by AuthenticationFilter.
        // No manual session check needed here.

        String fullName = request.getParameter("fullName");
        String email    = request.getParameter("email");
        String enteredSecret = request.getParameter("secret");

        // VALIDATION using ServletContext value
        if (this.secretKey != null && this.secretKey.equals(enteredSecret)) {
            // SUCCESS: Save to StaffService and redirect to staff directory
            StaffMember newMember = new StaffMember(fullName, email);
            staffService.addStaff(newMember);

            System.out.println("==> RegisterServlet: Successfully registered and saved staff member: " + fullName);
            response.sendRedirect("/hospital-webapp/staff");
        } else {
            // FAILURE: Redirect back with error
            response.sendRedirect("/hospital-webapp/register?error=wrong_key");
        }
    }
}

