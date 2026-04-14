package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demonstrates RequestDispatcher.forward()
 * This servlet summarizes the operation performed and renders a success message.
 */
@WebServlet("/confirmation")
public class ConfirmationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve data passed from the forwarding servlet
        String type = (String) request.getAttribute("type"); // "appointment" or "registration"
        String name = (String) request.getAttribute("name");

        String title = "Success | SanityCare Hospital";
        HtmlTemplate.renderHeader(request, out, title, "");

        out.println("  <div class='page-wrap'>");
        out.println("  <div class='form-card center'>");
        out.println("    <div class='success-icon'>&#127881;</div>");

        if ("appointment".equals(type)) {
            String ref = (String) request.getAttribute("ref");
            out.println("    <h1>Booking Successful!</h1>");
            out.println("    <p class='subtitle'>Thank you, " + name + ". Your resonance is being processed.</p>");
            out.println("    <div class='ref-badge'>");
            out.println("      <div class='label'>Reference Number</div>");
            out.println("      <div class='ref'>" + ref + "</div>");
            out.println("    </div>");
        } else if ("registration".equals(type)) {
            out.println("    <h1>Staff Registered!</h1>");
            out.println("    <p class='subtitle'>Welcome aboard, " + name + ". Your staff account has been activated.</p>");
        } else {
            out.println("    <h1>Operation Complete</h1>");
            out.println("    <p class='subtitle'>The task has been successfully handled.</p>");
        }

        out.println("    <div class='actions'>");
        out.println("      <a class='btn btn-primary' href='/hospital-webapp/home'>Return to Home Page &rarr;</a>");
        out.println("    </div>");
        out.println("  </div>");
        out.println("  </div>");

        try {
            HtmlTemplate.includeFooter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

