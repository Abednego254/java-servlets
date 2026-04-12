package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class StaffListServlet extends HttpServlet {

    private StaffService staffService = new StaffServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // NOTE: Session protection is now handled centrally by AuthenticationFilter.
        // No manual session check needed here.

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        HtmlTemplate.renderHeader(request, out, "Staff Directory | SanityCare Hospital", "staff");

        out.println("  <div class='page-header'>");
        out.println("    <div class='section-label'>Internal Resources</div>");
        out.println("    <h1>Staff Directory</h1>");
        out.println("    <p>A dynamic list of registered hospital personnel.</p>");
        out.println("  </div>");

        out.println("  <div class='services-section'>");
        out.println("    <div style='background: white; border-radius: 24px; padding: 2rem; border: 1px solid #e2e8f0; box-shadow: 0 4px 20px rgba(0,0,0,0.02); overflow: hidden;'>");
        out.println("      <table style='width: 100%; border-collapse: separate; border-spacing: 0;'>");
        out.println("        <thead>");
        out.println("          <tr style='background: #f8fafc;'>");
        out.println("            <th style='text-align: left; padding: 1.2rem; color: #64748b; font-weight: 700; border-bottom: 2px solid #e2e8f0;'>FULL NAME</th>");
        out.println("            <th style='text-align: left; padding: 1.2rem; color: #64748b; font-weight: 700; border-bottom: 2px solid #e2e8f0;'>EMAIL ADDRESS</th>");
        out.println("            <th style='text-align: left; padding: 1.2rem; color: #64748b; font-weight: 700; border-bottom: 2px solid #e2e8f0;'>STATUS</th>");
        out.println("          </tr>");
        out.println("        </thead>");
        out.println("        <tbody>");

        List<StaffMember> members = staffService.getAllStaff();
        for (StaffMember member : members) {
            out.println("          <tr>");
            out.println("            <td style='padding: 1.2rem; border-bottom: 1px solid #f1f5f9; font-weight: 600; color: #0f172a;'>" + member.getFullName() + "</td>");
            out.println("            <td style='padding: 1.2rem; border-bottom: 1px solid #f1f5f9; color: #475569;'>" + member.getEmail() + "</td>");
            out.println("            <td style='padding: 1.2rem; border-bottom: 1px solid #f1f5f9;'><span style='background: #ecfdf5; color: #059669; padding: 0.3rem 0.8rem; border-radius: 20px; font-size: 0.75rem; font-weight: 700; text-transform: uppercase;'>Active</span></td>");
            out.println("          </tr>");
        }

        if (members.isEmpty()) {
            out.println("          <tr><td colspan='3' style='text-align: center; padding: 3rem; color: #94a3b8;'>No staff members registered yet.</td></tr>");
        }

        out.println("        </tbody>");
        out.println("      </table>");

        out.println("      <div style='margin-top: 2rem; text-align: right;'>");
        out.println("        <a href='/hospital-webapp/register' class='btn-primary' style='padding: 0.8rem 1.5rem; font-size: 0.9rem;'>+ Register New Staff</a>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </div>");

        try {
            HtmlTemplate.includeFooter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
