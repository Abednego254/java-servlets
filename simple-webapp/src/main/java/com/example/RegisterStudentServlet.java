package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ============================================================
 *  RegisterStudentServlet — THE 3RD WAY TO CREATE A SERVLET
 *  BY EXTENDING HttpServlet
 * ============================================================
 *
 *  WHY HttpServlet IS THE MOST POPULAR APPROACH:
 *  ─────────────────────────────────────────────
 *  Remember the chain of inheritance:
 *
 *    Servlet (interface)
 *       └── GenericServlet (abstract class) — handles all 5 methods for you
 *               └── HttpServlet (abstract class) — goes further, adds HTTP awareness
 *                       └── YOUR SERVLET ← you are here
 *
 *  HttpServlet knows about HTTP, so instead of one generic service() method,
 *  it gives you SEPARATE methods for each HTTP request type:
 *
 *    doGet()    → handles  GET  requests  (e.g. loading a page or form)
 *    doPost()   → handles  POST requests  (e.g. submitting a form)
 *    doPut()    → handles  PUT  requests  (e.g. updating a resource)
 *    doDelete() → handles  DELETE requests (e.g. deleting a resource)
 *
 *  How does this work internally?
 *  HttpServlet's service() method reads the HTTP method from the request
 *  and automatically dispatches it to the right doXxx() method.
 *  You NEVER call service() yourself — the container calls it for you.
 *
 *  ALSO NOTICE: The parameters upgrade from:
 *    ServletRequest  → HttpServletRequest   (HTTP-aware: has getParameter, getSession, getCookies...)
 *    ServletResponse → HttpServletResponse  (HTTP-aware: has sendRedirect, setStatus, addCookie...)
 */
public class RegisterStudentServlet extends HttpServlet {

    // ================================================================
    //  doGet() — runs when the browser sends a GET request
    //
    //  A GET request happens when:
    //   - You type a URL into the browser and hit Enter
    //   - You click a link
    //   - A form uses method="get"
    //
    //  In our case: when the student visits /register, they get the FORM.
    // ================================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> RegisterStudentServlet: doGet() called — serving the registration form.");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>Register Student | NexTech College</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #0a0a0f; color: #e0e0e0; min-height: 100vh; }");

        // NAV
        out.println("    nav { display: flex; justify-content: space-between; align-items: center;");
        out.println("          padding: 1.2rem 4rem; background: rgba(15,15,25,0.95);");
        out.println("          border-bottom: 1px solid rgba(99,179,237,0.15); }");
        out.println("    .logo { font-size: 1.5rem; font-weight: 800; color: #63b3ed; }");
        out.println("    .logo span { color: #fff; }");
        out.println("    nav ul { list-style: none; display: flex; gap: 2rem; }");
        out.println("    nav ul li a { color: #a0aec0; text-decoration: none; font-weight: 500; font-size: 0.95rem; transition: color 0.2s; }");
        out.println("    nav ul li a:hover, nav ul li a.active { color: #63b3ed; }");
        out.println("    .nav-cta { background: #63b3ed; color: #0a0a0f !important; padding: 0.5rem 1.2rem; border-radius: 6px; font-weight: 600 !important; }");

        // FORM PAGE
        out.println("    .page-wrap { min-height: calc(100vh - 65px); display: flex; align-items: center; justify-content: center;");
        out.println("                 padding: 4rem 2rem;");
        out.println("                 background: radial-gradient(ellipse at 50% 0%, rgba(99,179,237,0.08) 0%, transparent 60%); }");
        out.println("    .form-card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.1);");
        out.println("                 border-radius: 16px; padding: 2.8rem 3rem; width: 100%; max-width: 560px; }");
        out.println("    .form-card h1 { font-size: 1.8rem; font-weight: 800; color: #fff; margin-bottom: 0.4rem; }");
        out.println("    .form-card .subtitle { color: #718096; font-size: 0.9rem; margin-bottom: 2rem; }");
        out.println("    .form-group { margin-bottom: 1.4rem; }");
        out.println("    label { display: block; font-size: 0.85rem; font-weight: 600; color: #a0aec0;");
        out.println("            margin-bottom: 0.45rem; text-transform: uppercase; letter-spacing: 0.5px; }");
        out.println("    input, select { width: 100%; background: rgba(255,255,255,0.05);");
        out.println("                    border: 1px solid rgba(255,255,255,0.12); border-radius: 8px;");
        out.println("                    padding: 0.75rem 1rem; color: #e0e0e0; font-family: 'Inter', sans-serif;");
        out.println("                    font-size: 0.95rem; transition: border-color 0.2s, box-shadow 0.2s; outline: none; }");
        out.println("    input:focus, select:focus { border-color: #63b3ed; box-shadow: 0 0 0 3px rgba(99,179,237,0.15); }");
        out.println("    select option { background: #1a1a2e; color: #e0e0e0; }");
        out.println("    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }");
        out.println("    .btn-submit { width: 100%; background: linear-gradient(135deg, #63b3ed, #a78bfa);");
        out.println("                  color: #0a0a0f; border: none; padding: 0.9rem; border-radius: 8px;");
        out.println("                  font-weight: 700; font-size: 1rem; cursor: pointer; margin-top: 0.5rem;");
        out.println("                  font-family: 'Inter', sans-serif; transition: opacity 0.2s; }");
        out.println("    .btn-submit:hover { opacity: 0.85; }");
        out.println("    .divider { border: none; border-top: 1px solid rgba(255,255,255,0.07); margin: 1.5rem 0; }");
        out.println("    footer { text-align: center; padding: 1.5rem; color: #4a5568; font-size: 0.8rem; }");
        out.println("    footer a { color: #63b3ed; text-decoration: none; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        // NAV
        out.println("  <nav>");
        out.println("    <div class='logo'>Nex<span>Tech</span></div>");
        out.println("    <ul>");
        out.println("      <li><a href='/simple-webapp/hello'>Home</a></li>");
        out.println("      <li><a href='/simple-webapp/about'>About Us</a></li>");
        out.println("      <li><a href='#'>Courses</a></li>");
        out.println("      <li><a class='nav-cta active' href='/simple-webapp/register'>Apply Now</a></li>");
        out.println("    </ul>");
        out.println("  </nav>");

        // FORM
        /*
         * KEY POINT ABOUT THE FORM:
         * action="/simple-webapp/register"  →  which servlet handles the submission (this one!)
         * method="post"                     →  tells the browser to send a POST request
         *                                      so our doPost() method will handle it
         *
         * If method were "get", doGet() would handle it instead.
         */
        out.println("  <div class='page-wrap'>");
        out.println("    <div class='form-card'>");
        out.println("      <h1>&#127979; Student Registration</h1>");
        out.println("      <p class='subtitle'>Fill in your details to join NexTech College of Technology.</p>");
        out.println("      <hr class='divider'>");

        out.println("      <form action='/simple-webapp/register' method='post'>");

        out.println("        <div class='form-row'>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='firstName'>First Name</label>");
        out.println("            <input type='text' id='firstName' name='firstName' placeholder='e.g. John' required>");
        out.println("          </div>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='lastName'>Last Name</label>");
        out.println("            <input type='text' id='lastName' name='lastName' placeholder='e.g. Kamau' required>");
        out.println("          </div>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='email'>Email Address</label>");
        out.println("          <input type='email' id='email' name='email' placeholder='e.g. john@example.com' required>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='phone'>Phone Number</label>");
        out.println("          <input type='tel' id='phone' name='phone' placeholder='e.g. 0712345678' required>");
        out.println("        </div>");

        out.println("        <div class='form-row'>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='dob'>Date of Birth</label>");
        out.println("            <input type='date' id='dob' name='dob' required>");
        out.println("          </div>");
        out.println("          <div class='form-group'>");
        out.println("            <label for='gender'>Gender</label>");
        out.println("            <select id='gender' name='gender' required>");
        out.println("              <option value=''>Select...</option>");
        out.println("              <option value='male'>Male</option>");
        out.println("              <option value='female'>Female</option>");
        out.println("              <option value='other'>Other</option>");
        out.println("            </select>");
        out.println("          </div>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='course'>Course of Study</label>");
        out.println("          <select id='course' name='course' required>");
        out.println("            <option value=''>Select a program...</option>");
        out.println("            <option value='software-engineering'>Software Engineering (BSc)</option>");
        out.println("            <option value='ai-ml'>Artificial Intelligence & ML (BSc)</option>");
        out.println("            <option value='cybersecurity'>Cybersecurity (BSc)</option>");
        out.println("            <option value='cloud-devops'>Cloud & DevOps Engineering (Diploma)</option>");
        out.println("          </select>");
        out.println("        </div>");

        out.println("        <div class='form-group'>");
        out.println("          <label for='nationality'>Nationality</label>");
        out.println("          <input type='text' id='nationality' name='nationality' placeholder='e.g. Kenyan' required>");
        out.println("        </div>");

        out.println("        <button type='submit' class='btn-submit'>Submit Application &rarr;</button>");
        out.println("      </form>");
        out.println("    </div>");
        out.println("  </div>");

        out.println("  <footer><p>&copy; 2026 NexTech College &nbsp;|&nbsp; <a href='/simple-webapp/hello'>Home</a></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }


    // ================================================================
    //  doPost() — runs when the browser sends a POST request
    //
    //  A POST request happens when:
    //   - A form uses method="post" and the user clicks submit
    //
    //  In our case: this receives and processes the registration form data.
    //
    //  request.getParameter("fieldName") reads a form field by its name=""
    //  attribute. This is one of the most used methods in all of Java EE!
    // ================================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> RegisterStudentServlet: doPost() called — processing form submission.");

        // ──────────────────────────────────────────────
        // STEP 1: Read the form data from the request
        //
        // request.getParameter("name") — where "name" matches
        // the name="" attribute on each <input> or <select> in the HTML form.
        // Returns a String, or null if the field wasn't submitted.
        // ──────────────────────────────────────────────
        String firstName   = request.getParameter("firstName");
        String lastName    = request.getParameter("lastName");
        String email       = request.getParameter("email");
        String phone       = request.getParameter("phone");
        String dob         = request.getParameter("dob");
        String gender      = request.getParameter("gender");
        String course      = request.getParameter("course");
        String nationality = request.getParameter("nationality");

        // ──────────────────────────────────────────────
        // STEP 2: Basic null/empty validation
        // In a real app you'd use a proper validator library,
        // but this shows the concept clearly.
        // ──────────────────────────────────────────────
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName  == null || lastName.trim().isEmpty()  ||
            email     == null || email.trim().isEmpty()     ||
            course    == null || course.trim().isEmpty()) {

            // Send back an error response
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red;font-family:sans-serif'>Error: Missing required fields. Please go back and complete the form.</h2>");
            return; // stop processing
        }

        // ──────────────────────────────────────────────
        // STEP 3: Format the course name nicely for display
        // In real life you'd save this to a database here.
        // ──────────────────────────────────────────────
        String courseDisplay;
        switch (course) {
            case "software-engineering": courseDisplay = "Software Engineering (BSc)"; break;
            case "ai-ml":               courseDisplay = "Artificial Intelligence & ML (BSc)"; break;
            case "cybersecurity":       courseDisplay = "Cybersecurity (BSc)"; break;
            case "cloud-devops":        courseDisplay = "Cloud & DevOps Engineering (Diploma)"; break;
            default:                    courseDisplay = course;
        }

        // ──────────────────────────────────────────────
        // STEP 4: Generate a dummy student ID
        // (In reality this would come from a database INSERT)
        // ──────────────────────────────────────────────
        String studentId = "NTC-2026-" + (int)(Math.random() * 90000 + 10000);

        // ──────────────────────────────────────────────
        // STEP 5: Send back a SUCCESS response
        // We write a confirmation page back to the browser.
        // ──────────────────────────────────────────────
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>Registration Successful | NexTech College</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #0a0a0f; color: #e0e0e0;");
        out.println("           min-height: 100vh; display: flex; flex-direction: column; align-items: center;");
        out.println("           justify-content: center; padding: 2rem; }");
        out.println("    .card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.1);");
        out.println("            border-radius: 16px; padding: 3rem; max-width: 600px; width: 100%; }");
        out.println("    .success-icon { font-size: 3.5rem; text-align: center; margin-bottom: 1rem; }");
        out.println("    h1 { font-size: 1.8rem; font-weight: 800; color: #fff; text-align: center; margin-bottom: 0.4rem; }");
        out.println("    .sub { text-align: center; color: #718096; font-size: 0.9rem; margin-bottom: 2rem; }");
        out.println("    .id-badge { background: rgba(99,179,237,0.1); border: 1px solid rgba(99,179,237,0.3);");
        out.println("                border-radius: 10px; padding: 1rem 1.5rem; text-align: center; margin-bottom: 2rem; }");
        out.println("    .id-badge .label { font-size: 0.75rem; text-transform: uppercase; letter-spacing: 1px; color: #718096; }");
        out.println("    .id-badge .id { font-size: 1.6rem; font-weight: 800; color: #63b3ed; letter-spacing: 2px; }");
        out.println("    .details { border-top: 1px solid rgba(255,255,255,0.07); padding-top: 1.5rem; }");
        out.println("    .detail-row { display: flex; justify-content: space-between; padding: 0.6rem 0;");
        out.println("                  border-bottom: 1px solid rgba(255,255,255,0.05); font-size: 0.9rem; }");
        out.println("    .detail-row .field { color: #718096; }");
        out.println("    .detail-row .value { color: #e0e0e0; font-weight: 600; }");
        out.println("    .actions { display: flex; gap: 1rem; margin-top: 2rem; flex-wrap: wrap; }");
        out.println("    .btn { flex: 1; text-align: center; padding: 0.8rem; border-radius: 8px;");
        out.println("           text-decoration: none; font-weight: 600; font-size: 0.9rem; }");
        out.println("    .btn-primary { background: linear-gradient(135deg, #63b3ed, #a78bfa); color: #0a0a0f; }");
        out.println("    .btn-outline { border: 1px solid rgba(99,179,237,0.4); color: #63b3ed; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("  <div class='card'>");
        out.println("    <div class='success-icon'>&#127881;</div>");
        out.println("    <h1>Application Received!</h1>");
        out.println("    <p class='sub'>Welcome to NexTech College, " + firstName + ". Your registration was successful.</p>");

        // Student ID badge
        out.println("    <div class='id-badge'>");
        out.println("      <div class='label'>Your Student ID</div>");
        out.println("      <div class='id'>" + studentId + "</div>");
        out.println("    </div>");

        // Summary table of submitted data
        out.println("    <div class='details'>");
        out.println("      <div class='detail-row'><span class='field'>Full Name</span><span class='value'>" + firstName + " " + lastName + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Email</span><span class='value'>" + email + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Phone</span><span class='value'>" + phone + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Date of Birth</span><span class='value'>" + dob + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Gender</span><span class='value'>" + gender + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Program</span><span class='value'>" + courseDisplay + "</span></div>");
        out.println("      <div class='detail-row'><span class='field'>Nationality</span><span class='value'>" + nationality + "</span></div>");
        out.println("    </div>");

        out.println("    <div class='actions'>");
        out.println("      <a class='btn btn-outline' href='/simple-webapp/register'>Register Another &larr;</a>");
        out.println("      <a class='btn btn-primary' href='/simple-webapp/hello'>Back to Home &rarr;</a>");
        out.println("    </div>");
        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }
}
