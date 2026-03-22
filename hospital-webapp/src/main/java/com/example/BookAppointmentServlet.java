package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ============================================================
 *  BookAppointmentServlet — Appointment Booking Page
 *
 *  WAY 3: EXTENDING HttpServlet
 * ============================================================
 *
 *  The Servlet Inheritance Chain (remember this!):
 *
 *    Servlet (interface)
 *       └── GenericServlet (abstract class)  ← Way 2 stopped here
 *               └── HttpServlet (abstract class)  ← Way 3 uses this
 *                       └── BookAppointmentServlet  ← YOU ARE HERE
 *
 *  WHY HttpServlet is the MOST POPULAR approach:
 *  ─────────────────────────────────────────────
 *  HttpServlet knows about the HTTP protocol. Instead of a single
 *  service() method that handles ALL types of requests, it gives you
 *  a SEPARATE method for each HTTP verb:
 *
 *    doGet()    → handles GET  requests  (loading a page, clicking a link)
 *    doPost()   → handles POST requests  (submitting a form)
 *    doPut()    → handles PUT  requests  (updating a resource — REST APIs)
 *    doDelete() → handles DELETE requests (deleting a resource — REST APIs)
 *
 *  HOW it works internally:
 *  HttpServlet's own service() reads the HTTP method from the request
 *  and dispatches to the correct doXxx() automatically. You never call
 *  service() yourself.
 *
 *  ALSO NOTICE the parameter upgrade:
 *    ServletRequest  →  HttpServletRequest   (has getParameter, getSession, getCookies...)
 *    ServletResponse →  HttpServletResponse  (has sendRedirect, setStatus, addCookie...)
 *
 *  Registered via web.xml → URL pattern: /appointment
 */
public class BookAppointmentServlet extends HttpServlet {

    // ================================================================
    //  doGet() — runs when the browser sends a GET request to /appointment
    //
    //  A GET request happens when:
    //   - The user types the URL and hits Enter
    //   - The user clicks a link pointing to /appointment
    //
    //  Our response: serve the appointment BOOKING FORM as HTML.
    // ================================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> BookAppointmentServlet: doGet() called — serving the booking form.");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>Book Appointment | SanityCare Hospital</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #050d1a; color: #e0e6f0; min-height: 100vh; }");

        // NAV
        out.println("    nav { display: flex; justify-content: space-between; align-items: center;");
        out.println("          padding: 1.2rem 4rem; background: rgba(5,13,30,0.97);");
        out.println("          border-bottom: 1px solid rgba(56,189,248,0.15); }");
        out.println("    .logo { font-size: 1.5rem; font-weight: 800; color: #38bdf8; }");
        out.println("    .logo span { color: #fff; }");
        out.println("    nav ul { list-style: none; display: flex; gap: 2rem; }");
        out.println("    nav ul li a { color: #94a3b8; text-decoration: none; font-weight: 500; font-size: 0.95rem; transition: color 0.2s; }");
        out.println("    nav ul li a:hover, nav ul li a.active { color: #38bdf8; }");
        out.println("    .nav-cta { background: #38bdf8; color: #050d1a !important; padding: 0.5rem 1.2rem; border-radius: 6px; font-weight: 700 !important; }");

        // FORM LAYOUT
        out.println("    .page-wrap { min-height: calc(100vh - 65px); display: flex; align-items: center; justify-content: center;");
        out.println("                 padding: 4rem 2rem;");
        out.println("                 background: radial-gradient(ellipse at 50% 0%, rgba(56,189,248,0.07) 0%, transparent 60%); }");
        out.println("    .form-card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.1);");
        out.println("                 border-radius: 16px; padding: 2.8rem 3rem; width: 100%; max-width: 580px; }");
        out.println("    .form-card h1 { font-size: 1.8rem; font-weight: 800; color: #fff; margin-bottom: 0.4rem; }");
        out.println("    .form-card .subtitle { color: #64748b; font-size: 0.9rem; margin-bottom: 2rem; }");
        out.println("    .form-group { margin-bottom: 1.4rem; }");
        out.println("    label { display: block; font-size: 0.85rem; font-weight: 600; color: #94a3b8;");
        out.println("            margin-bottom: 0.45rem; text-transform: uppercase; letter-spacing: 0.5px; }");
        out.println("    input, select, textarea { width: 100%; background: rgba(255,255,255,0.05);");
        out.println("                    border: 1px solid rgba(255,255,255,0.12); border-radius: 8px;");
        out.println("                    padding: 0.75rem 1rem; color: #e0e6f0; font-family: 'Inter', sans-serif;");
        out.println("                    font-size: 0.95rem; transition: border-color 0.2s, box-shadow 0.2s; outline: none; }");
        out.println("    input:focus, select:focus, textarea:focus { border-color: #38bdf8; box-shadow: 0 0 0 3px rgba(56,189,248,0.15); }");
        out.println("    select option { background: #0f172a; color: #e0e6f0; }");
        out.println("    textarea { resize: vertical; min-height: 80px; }");
        out.println("    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }");
        out.println("    .btn-submit { width: 100%; background: linear-gradient(135deg, #38bdf8, #34d399);");
        out.println("                  color: #050d1a; border: none; padding: 0.9rem; border-radius: 8px;");
        out.println("                  font-weight: 700; font-size: 1rem; cursor: pointer; margin-top: 0.5rem;");
        out.println("                  font-family: 'Inter', sans-serif; transition: opacity 0.2s; }");
        out.println("    .btn-submit:hover { opacity: 0.85; }");
        out.println("    .divider { border: none; border-top: 1px solid rgba(255,255,255,0.07); margin: 1.5rem 0; }");
        out.println("    footer { text-align: center; padding: 1.5rem; color: #475569; font-size: 0.8rem; }");
        out.println("    footer a { color: #38bdf8; text-decoration: none; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        // NAV
        out.println("  <nav>");
        out.println("    <div class='logo'>Sanity<span>Care</span></div>");
        out.println("    <ul>");
        out.println("      <li><a href='/hospital-webapp/home'>Home</a></li>");
        out.println("      <li><a href='/hospital-webapp/services'>Our Services</a></li>");
        out.println("      <li><a class='nav-cta active' href='/hospital-webapp/appointment'>Book Appointment</a></li>");
        out.println("    </ul>");
        out.println("  </nav>");

        /*
         * KEY POINT ABOUT THE FORM:
         * action="/hospital-webapp/appointment" → which URL handles the submission (this servlet!)
         * method="post"                         → tells the browser to send a POST request
         *                                         so our doPost() method will be called
         *
         * If method were "get", doGet() would handle submission instead —
         * and the form data would be visible in the URL (not safe for personal info).
         */
        out.println("  <div class='page-wrap'>");
        out.println("    <div class='form-card'>");
        out.println("      <h1>&#128197; Book an Appointment</h1>");
        out.println("      <p class='subtitle'>Fill in the form below and our team will confirm your slot within the hour.</p>");
        out.println("      <hr class='divider'>");

        out.println("      <form action='/hospital-webapp/appointment' method='post'>");

        // Row: First + Last name
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

        // Email
        out.println("        <div class='form-group'>");
        out.println("          <label for='email'>Email Address</label>");
        out.println("          <input type='email' id='email' name='email' placeholder='e.g. alice@example.com' required>");
        out.println("        </div>");

        // Phone
        out.println("        <div class='form-group'>");
        out.println("          <label for='phone'>Phone Number</label>");
        out.println("          <input type='tel' id='phone' name='phone' placeholder='e.g. 0712345678' required>");
        out.println("        </div>");

        // Row: Appointment date + Preferred time
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

        // Department
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

        // Reason / Symptoms
        out.println("        <div class='form-group'>");
        out.println("          <label for='reason'>Brief Reason / Symptoms</label>");
        out.println("          <textarea id='reason' name='reason' placeholder='Briefly describe your symptoms or reason for visit...' required></textarea>");
        out.println("        </div>");

        out.println("        <button type='submit' class='btn-submit'>Confirm Booking &rarr;</button>");
        out.println("      </form>");
        out.println("    </div>");
        out.println("  </div>");

        out.println("  <footer><p>&copy; 2026 SanityCare Hospital &nbsp;|&nbsp; <a href='/hospital-webapp/home'>Home</a></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }


    // ================================================================
    //  doPost() — runs when the browser sends a POST request to /appointment
    //
    //  A POST request happens when the user fills the form above and
    //  clicks "Confirm Booking". The browser sends the form fields in
    //  the HTTP request body (not visible in the URL — safer!).
    //
    //  request.getParameter("fieldName") reads any form field by its
    //  name="" attribute. This is the most-used method in Java EE!
    // ================================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> BookAppointmentServlet: doPost() called — processing appointment form.");

        // ── STEP 1: Read the submitted form data ──────────────────────────
        // request.getParameter("name") matches the name="" on each input/select/textarea
        String firstName       = request.getParameter("firstName");
        String lastName        = request.getParameter("lastName");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String department      = request.getParameter("department");
        String reason          = request.getParameter("reason");

        // ── STEP 2: Basic validation ──────────────────────────────────────
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName  == null || lastName.trim().isEmpty()  ||
            email     == null || email.trim().isEmpty()     ||
            department == null || department.trim().isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red;font-family:sans-serif'>Error: Missing required fields. Please go back and complete the form.</h2>");
            return;
        }

        // ── STEP 3: Format department name for display ────────────────────
        String deptDisplay;
        switch (department) {
            case "cardiology":    deptDisplay = "Cardiology";                  break;
            case "neurology":     deptDisplay = "Neurology & Neurosurgery";    break;
            case "orthopaedics":  deptDisplay = "Orthopaedics & Trauma";       break;
            case "paediatrics":   deptDisplay = "Paediatrics";                 break;
            case "obs-gynae":     deptDisplay = "Obstetrics & Gynaecology";    break;
            case "oncology":      deptDisplay = "Oncology";                    break;
            case "general":       deptDisplay = "General Medicine (GP)";       break;
            default:              deptDisplay = department;
        }

        // ── STEP 4: Generate a unique appointment reference ───────────────
        // In a real system this would come from auto-increment in a database.
        String appointmentRef = "SCH-" + (int)(Math.random() * 900000 + 100000);

        // ── STEP 5: Send the confirmation page back to the browser ─────────
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>Appointment Confirmed | SanityCare Hospital</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #050d1a; color: #e0e6f0;");
        out.println("           min-height: 100vh; display: flex; flex-direction: column; align-items: center;");
        out.println("           justify-content: center; padding: 2rem; }");
        out.println("    .card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.1);");
        out.println("            border-radius: 16px; padding: 3rem; max-width: 620px; width: 100%; }");
        out.println("    .success-icon { font-size: 3.5rem; text-align: center; margin-bottom: 1rem; }");
        out.println("    h1 { font-size: 1.8rem; font-weight: 800; color: #fff; text-align: center; margin-bottom: 0.4rem; }");
        out.println("    .sub { text-align: center; color: #64748b; font-size: 0.9rem; margin-bottom: 2rem; }");
        out.println("    .ref-badge { background: rgba(56,189,248,0.1); border: 1px solid rgba(56,189,248,0.3);");
        out.println("                 border-radius: 10px; padding: 1rem 1.5rem; text-align: center; margin-bottom: 2rem; }");
        out.println("    .ref-badge .label { font-size: 0.75rem; text-transform: uppercase; letter-spacing: 1px; color: #64748b; }");
        out.println("    .ref-badge .ref { font-size: 1.6rem; font-weight: 800; color: #38bdf8; letter-spacing: 2px; }");
        out.println("    .details { border-top: 1px solid rgba(255,255,255,0.07); padding-top: 1.5rem; }");
        out.println("    .detail-row { display: flex; justify-content: space-between; padding: 0.6rem 0;");
        out.println("                  border-bottom: 1px solid rgba(255,255,255,0.05); font-size: 0.9rem; }");
        out.println("    .detail-row .field { color: #64748b; }");
        out.println("    .detail-row .value { color: #e0e6f0; font-weight: 600; }");
        out.println("    .actions { display: flex; gap: 1rem; margin-top: 2rem; flex-wrap: wrap; }");
        out.println("    .btn { flex: 1; text-align: center; padding: 0.8rem; border-radius: 8px;");
        out.println("           text-decoration: none; font-weight: 600; font-size: 0.9rem; }");
        out.println("    .btn-primary { background: linear-gradient(135deg, #38bdf8, #34d399); color: #050d1a; }");
        out.println("    .btn-outline { border: 1px solid rgba(56,189,248,0.4); color: #38bdf8; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("  <div class='card'>");
        out.println("    <div class='success-icon'>&#127881;</div>");
        out.println("    <h1>Appointment Confirmed!</h1>");
        out.println("    <p class='sub'>Thank you, " + firstName + ". Your appointment has been received and will be confirmed shortly.</p>");

        // Reference number badge
        out.println("    <div class='ref-badge'>");
        out.println("      <div class='label'>Your Appointment Reference</div>");
        out.println("      <div class='ref'>" + appointmentRef + "</div>");
        out.println("    </div>");

        // Summary of submitted data
        out.println("    <div class='details'>");
        out.println("      <div class='detail-row'><span class='field'>Full Name</span><span class='value'>" + firstName + " " + lastName + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Email</span><span class='value'>" + email + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Phone</span><span class='value'>" + phone + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Date</span><span class='value'>" + appointmentDate + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Time</span><span class='value'>" + appointmentTime + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Department</span><span class='value'>" + deptDisplay + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Reason</span><span class='value'>" + reason + "</span></div>");
        out.println("    </div>");

        out.println("    <div class='actions'>");
        out.println("      <a class='btn btn-outline' href='/hospital-webapp/appointment'>New Booking &larr;</a>");
        out.println("      <a class='btn btn-primary' href='/hospital-webapp/home'>Back to Home &rarr;</a>");
        out.println("    </div>");
        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }
}
