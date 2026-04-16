package com.example.action;

import com.example.framework.HospitalFramework;
import com.example.framework.HtmlTemplate;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/home")
public class HomeServlet implements Servlet {

    // We store the config so getServletConfig() can return it.
    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
        System.out.println("==> HomeServlet: init() called — servlet loaded into memory.");
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> HomeServlet: service() called — serving the home page.");

        // --- GLOBAL VISITOR COUNTER (ServletContext Attribute) ---
        // NOTE: AppLifecycleListener pre-seeds "totalVisitors" to 0 at startup,
        // so we can safely increment without a null-check.
        synchronized(this.getServletConfig().getServletContext()) {
            int total = (Integer) this.getServletConfig().getServletContext().getAttribute("totalVisitors");
            this.getServletConfig().getServletContext().setAttribute("totalVisitors", total + 1);
        }

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printout = response.getWriter();

        // Cast for the template
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HtmlTemplate.renderHeader(httpReq, printout, "SanityCare Hospital", "home");
        
        // ── INCLUDE ──
        // Demonstrating RequestDispatcher.include()
        // We pull the "NoticeBanner" from another servlet and inject it here!
        request.getRequestDispatcher("/notice").include(request, response);

        // ── HERO ──
        printout.println("  <section class='hero'>");
        printout.println("    <div class='badge'>&#127973; Accredited Healthcare Excellence</div>");
        printout.println("    <h1>Your Health Is Our <span>Top Priority</span></h1>");
        printout.println("    <p>SanityCare Hospital provides world-class medical care with compassion, cutting-edge technology, and a team of over 500 specialist doctors dedicated to your well-being.</p>");
        printout.println("    <div class='hero-actions'>");
        printout.println("      <a class='btn-primary' href='/hospital-webapp/appointment'>Book an Appointment</a>");
        printout.println("      <a class='btn-secondary' href='/hospital-webapp/services'>Our Services &rarr;</a>");
        printout.println("    </div>");
        printout.println("  </section>");

        // ── STATS ──
        printout.println("  <div class='stats'>");
        printout.println("    <div class='stat'><div class='stat-number'>500+</div><div class='stat-label'>Specialist Doctors</div></div>");
        printout.println("    <div class='stat'><div class='stat-number'>98%</div><div class='stat-label'>Patient Satisfaction</div></div>");
        printout.println("    <div class='stat'><div class='stat-number'>24/7</div><div class='stat-label'>Emergency Care</div></div>");
        printout.println("    <div class='stat'><div class='stat-number'>40 Yrs</div><div class='stat-label'>Of Trusted Care</div></div>");
        printout.println("  </div>");

        // ── DEPARTMENTS PREVIEW ──
        printout.println("  <section class='depts'>");
        printout.println("    <div class='section-header'>");
        printout.println("      <h2>Our Key Departments</h2>");
        printout.println("      <p>Comprehensive medical specialties under one roof.</p>");
        printout.println("    </div>");
        printout.println("    <div class='cards'>");

        printout.println("      <div class='card'>");
        printout.println("        <div class='card-icon'>&#10084;&#65039;</div>");
        printout.println("        <h3>Cardiology</h3>");
        printout.println("        <p>Advanced heart care — from diagnosis to surgery, handled by Kenya's leading cardiologists.</p>");
        printout.println("      </div>");

        printout.println("      <div class='card'>");
        printout.println("        <div class='card-icon'>&#129504;</div>");
        printout.println("        <h3>Neurology</h3>");
        printout.println("        <p>Expert care for brain, spine, and nervous system conditions using the latest MRI technology.</p>");
        printout.println("      </div>");

        printout.println("      <div class='card'>");
        printout.println("        <div class='card-icon'>&#129455;</div>");
        printout.println("        <h3>Orthopaedics</h3>");
        printout.println("        <p>Bone, joint, and muscle treatments including minimally invasive surgery and physiotherapy.</p>");
        printout.println("      </div>");

        printout.println("      <div class='card'>");
        printout.println("        <div class='card-icon'>&#128118;</div>");
        printout.println("        <h3>Paediatrics</h3>");
        printout.println("        <p>Dedicated child health unit ensuring the best outcomes for infants, children, and adolescents.</p>");
        printout.println("      </div>");

        printout.println("    </div>");
        printout.println("  </section>");

        try {
            // Use the existing httpReq variable
            HttpServletResponse httpRes = (HttpServletResponse) response;
            HtmlTemplate.includeFooter(httpReq, httpRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── 3. destroy() ───────────────────────────────────────────────────────
    @Override
    public void destroy() {
        System.out.println("==> HomeServlet: destroy() called — servlet unloaded from memory.");
    }

    // ── 4. getServletConfig() ──────────────────────────────────────────────
    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    // ── 5. getServletInfo() ────────────────────────────────────────────────
    @Override
    public String getServletInfo() {
        return "SanityCare Hospital Home Page — implements Servlet interface (Way 1)";
    }
}
