package com.example.action;

import com.example.framework.HtmlTemplate;
import com.example.service.AppointmentService;
import com.example.service.AppointmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/appointment")
public class BookAppointmentServlet extends HttpServlet {

    private AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("==> BookAppointmentServlet: doGet() called — serving the booking form.");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HtmlTemplate.renderHeader(request, out, "Book Appointment | SanityCare Hospital", "appointment");

        out.println("  <div class='page-wrap'>");
        out.println("    <div class='form-card'>");
        out.println("      <h1>&#128197; Book an Appointment</h1>");
        out.println("      <p class='subtitle'>Fill in the form below and our team will confirm your slot within the hour.</p>");
        out.println("      <hr class='divider'>");

        out.println("      <form action='./appointment' method='post'>");
        
        out.println("        <div class='form-row'>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='firstName'>First Name</label>");
        out.println("            <input type='text' id='firstName' name='firstName' placeholder='e.g. Alice' required>");
        out.println("          </div>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='lastName'>Last Name</label>");
        out.println("            <input type='text' id='lastName' name='lastName' placeholder='e.g. Mwangi' required>");
        out.println("          </div>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='email'>Email Address</label>");
        out.println("          <input type='email' id='email' name='email' placeholder='e.g. alice@example.com' required>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='phone'>Phone Number</label>");
        out.println("          <input type='tel' id='phone' name='phone' placeholder='e.g. 0712345678' required>");
        out.println("        </div>");

        out.println("        <div class='form-row'>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='appointmentDate'>Preferred Date</label>");
        out.println("            <input type='date' id='appointmentDate' name='appointmentDate' required>");
        out.println("          </div>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='appointmentTime'>Preferred Time</label>");
        out.println("            <select id='appointmentTime' name='appointmentTime' required>");
        out.println("              <option value=''>Select...</option>");
        out.println("              <option value='08:00'>08:00 AM</option>");
        out.println("              <option value='09:00'>09:00 AM</option>");
        out.println("              <option value='10:00'>10:00 AM</option>");
        out.println("              <option value='11:00'>11:00 AM</option>");
        out.println("              <option value='14:00'>02:00 PM</option>");
        out.println("              <option value='15:00'>03:00 PM</option>");
        out.println("              <option value='16:00'>04:00 PM</option>");
        out.println("            </select>");
        out.println("          </div>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='department'>Department / Specialty</label>");
        out.println("          <select id='department' name='department' required>");
        out.println("            <option value=''>Select a department...</option>");
        out.println("            <option value='cardiology'>Cardiology</option>");
        out.println("            <option value='neurology'>Neurology &amp; Neurosurgery</option>");
        out.println("            <option value='orthopaedics'>Orthopaedics &amp; Trauma</option>");
        out.println("            <option value='paediatrics'>Paediatrics</option>");
        out.println("            <option value='obs-gynae'>Obstetrics &amp; Gynaecology</option>");
        out.println("            <option value='oncology'>Oncology</option>");
        out.println("            <option value='general'>General Medicine (GP)</option>");
        out.println("          </select>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='reason'>Brief Reason / Symptoms</label>");
        out.println("          <textarea id='reason' name='reason' placeholder='Briefly describe your symptoms or reason for visit...' required></textarea>");
        out.println("        </div>");

        String error = request.getParameter("error");
        if ("wrong_passcode".equals(error)) {
            out.println("<p style='background:#fee2e2; border: 1px solid #ef4444; color:#b91c1c; padding: 1rem; border-radius: 12px; font-weight: 600; margin-bottom: 2rem;'>&#9888; Access Denied: Incorrect Admin Passcode.</p>");
        }

        out.println("        <div class='form-group'>");
        out.println("          <label for='passcode'>Admin Passcode</label>");
        out.println("          <input type='password' id='passcode' name='passcode' placeholder='Enter global master password...' required>");
        out.println("          <p style='font-size: 0.75rem; color: #64748b; margin-top: 0.5rem;'>Hint: Try SanityAdmin2026</p>");
        out.println("        </div>");

        out.println("        <button type='submit' class='btn-submit'>Confirm Booking &rarr;</button>");
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
        System.out.println("==> BookAppointmentServlet: doPost() called — processing appointment form.");

        String firstName       = request.getParameter("firstName");
        String lastName        = request.getParameter("lastName");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String department      = request.getParameter("department");
        String reason          = request.getParameter("reason");
        String enteredPasscode = request.getParameter("passcode");

        String globalKey = getServletContext().getInitParameter("globalAdminKey");
        if (globalKey != null && !globalKey.equals(enteredPasscode)) {
            System.out.println("==> BookAppointmentServlet: Invalid global passcode.");
            response.sendRedirect("./appointment?error=wrong_passcode");
            return;
        }

        if (firstName == null || firstName.trim().isEmpty()) {
            response.sendRedirect("./appointment?error=missing_fields");
            return;
        }

        try {
            String appointmentRef = appointmentService.bookAppointment(firstName, lastName, email, phone, appointmentDate, appointmentTime, department, reason);
            request.setAttribute("type", "appointment");
            request.setAttribute("name", firstName);
            request.setAttribute("ref", appointmentRef);
            request.getRequestDispatcher("/confirmation").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
