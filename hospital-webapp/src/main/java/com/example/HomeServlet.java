package com.example;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

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

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printout = response.getWriter();

        printout.println("<!DOCTYPE html>");
        printout.println("<html lang='en'>");
        printout.println("<head>");
        printout.println("  <meta charset='UTF-8'>");
        printout.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        printout.println("  <title>SanityCare Hospital</title>");
        printout.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap' rel='stylesheet'>");
        printout.println("  <style>");
        printout.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        printout.println("    body { font-family: 'Inter', sans-serif; background: #050d1a; color: #e0e6f0; }");

        // --- NAV ---
        printout.println("    nav { display: flex; justify-content: space-between; align-items: center;");
        printout.println("          padding: 1.2rem 4rem; background: rgba(5,13,30,0.97);");
        printout.println("          border-bottom: 1px solid rgba(56,189,248,0.15); position: sticky; top:0; z-index:100; }");
        printout.println("    .logo { font-size: 1.5rem; font-weight: 800; color: #38bdf8; letter-spacing: -0.5px; }");
        printout.println("    .logo span { color: #fff; }");
        printout.println("    nav ul { list-style: none; display: flex; gap: 2rem; }");
        printout.println("    nav ul li a { color: #94a3b8; text-decoration: none; font-weight: 500; font-size: 0.95rem; transition: color 0.2s; }");
        printout.println("    nav ul li a:hover, nav ul li a.active { color: #38bdf8; }");
        printout.println("    .nav-cta { background: #38bdf8; color: #050d1a !important; padding: 0.5rem 1.2rem;");
        printout.println("               border-radius: 6px; font-weight: 700 !important; transition: background 0.2s !important; }");
        printout.println("    .nav-cta:hover { background: #7dd3fc !important; color: #050d1a !important; }");

        // --- HERO ---
        printout.println("    .hero { min-height: 85vh; display: flex; flex-direction: column; justify-content: center;");
        printout.println("            align-items: center; text-align: center; padding: 4rem 2rem;");
        printout.println("            background: radial-gradient(ellipse at 50% 40%, rgba(56,189,248,0.1) 0%, transparent 70%); }");
        printout.println("    .badge { display: inline-block; background: rgba(56,189,248,0.1); border: 1px solid rgba(56,189,248,0.3);");
        printout.println("             color: #38bdf8; padding: 0.35rem 1rem; border-radius: 20px; font-size: 0.8rem;");
        printout.println("             font-weight: 600; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 1.5rem; }");
        printout.println("    .hero h1 { font-size: clamp(2.5rem, 6vw, 4.5rem); font-weight: 800; line-height: 1.1;");
        printout.println("               color: #fff; margin-bottom: 1.5rem; max-width: 820px; }");
        printout.println("    .hero h1 span { background: linear-gradient(135deg, #38bdf8, #34d399);");
        printout.println("                    -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }");
        printout.println("    .hero p { font-size: 1.15rem; color: #94a3b8; max-width: 600px; line-height: 1.7; margin-bottom: 2.5rem; }");
        printout.println("    .hero-actions { display: flex; gap: 1rem; flex-wrap: wrap; justify-content: center; }");
        printout.println("    .btn-primary { background: linear-gradient(135deg, #38bdf8, #34d399); color: #050d1a;");
        printout.println("                   padding: 0.85rem 2rem; border-radius: 8px; text-decoration: none;");
        printout.println("                   font-weight: 700; font-size: 1rem; transition: opacity 0.2s; }");
        printout.println("    .btn-primary:hover { opacity: 0.85; }");
        printout.println("    .btn-secondary { border: 1px solid rgba(56,189,248,0.4); color: #38bdf8;");
        printout.println("                     padding: 0.85rem 2rem; border-radius: 8px; text-decoration: none;");
        printout.println("                     font-weight: 600; font-size: 1rem; transition: background 0.2s; }");
        printout.println("    .btn-secondary:hover { background: rgba(56,189,248,0.08); }");

        // --- STATS ---
        printout.println("    .stats { display: flex; justify-content: center; gap: 4rem; padding: 3rem 2rem;");
        printout.println("             border-top: 1px solid rgba(255,255,255,0.06); border-bottom: 1px solid rgba(255,255,255,0.06);");
        printout.println("             background: rgba(255,255,255,0.02); flex-wrap: wrap; }");
        printout.println("    .stat { text-align: center; }");
        printout.println("    .stat-number { font-size: 2.2rem; font-weight: 800; color: #38bdf8; }");
        printout.println("    .stat-label { font-size: 0.85rem; color: #64748b; margin-top: 0.2rem; text-transform: uppercase; letter-spacing: 0.5px; }");

        // --- DEPARTMENTS ---
        printout.println("    .depts { padding: 5rem 4rem; }");
        printout.println("    .section-header { text-align: center; margin-bottom: 3rem; }");
        printout.println("    .section-header h2 { font-size: 2.2rem; font-weight: 800; color: #fff; margin-bottom: 0.75rem; }");
        printout.println("    .section-header p { color: #64748b; font-size: 1rem; }");
        printout.println("    .cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 1.5rem; }");
        printout.println("    .card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.08);");
        printout.println("            border-radius: 12px; padding: 1.8rem; transition: border-color 0.25s, transform 0.25s; }");
        printout.println("    .card:hover { border-color: rgba(56,189,248,0.4); transform: translateY(-4px); }");
        printout.println("    .card-icon { font-size: 2rem; margin-bottom: 1rem; }");
        printout.println("    .card h3 { color: #fff; font-size: 1.05rem; font-weight: 700; margin-bottom: 0.5rem; }");
        printout.println("    .card p { color: #64748b; font-size: 0.875rem; line-height: 1.6; }");

        // --- FOOTER ---
        printout.println("    footer { text-align: center; padding: 2.5rem; background: rgba(0,0,0,0.4);");
        printout.println("             border-top: 1px solid rgba(255,255,255,0.06); color: #475569; font-size: 0.875rem; }");
        printout.println("    footer a { color: #38bdf8; text-decoration: none; }");
        printout.println("  </style>");
        printout.println("</head>");
        printout.println("<body>");

        // ── NAV ──
        printout.println("  <nav>");
        printout.println("    <div class='logo'>Sanity<span>Care</span></div>");
        printout.println("    <ul>");
        printout.println("      <li><a class='active' href='/hospital-webapp/home'>Home</a></li>");
        printout.println("      <li><a href='/hospital-webapp/services'>Our Services</a></li>");
        printout.println("      <li><a class='nav-cta' href='/hospital-webapp/appointment'>Book Appointment</a></li>");
        printout.println("    </ul>");
        printout.println("  </nav>");

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

        // ── FOOTER ──
        printout.println("  <footer>");
        printout.println("    <p>&copy; 2026 SanityCare Hospital &nbsp;|&nbsp; <a href='/hospital-webapp/services'>Our Services</a> &nbsp;|&nbsp; <a href='/hospital-webapp/appointment'>Book Appointment</a></p>");
        printout.println("  </footer>");

        printout.println("</body>");
        printout.println("</html>");
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
