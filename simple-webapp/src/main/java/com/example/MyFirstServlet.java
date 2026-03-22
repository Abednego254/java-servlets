package com.example;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * MyFirstServlet — Welcome Page for NexTech College of Technology.
 * Created by IMPLEMENTING the Servlet interface directly.
 * Registered via web.xml (traditional approach).
 */
public class MyFirstServlet implements Servlet {

    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
        System.out.println("==> MyFirstServlet (Welcome Page): init() called.");
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>NexTech College of Technology</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #0a0a0f; color: #e0e0e0; }");

        // --- NAV ---
        out.println("    nav { display: flex; justify-content: space-between; align-items: center;");
        out.println("          padding: 1.2rem 4rem; background: rgba(15,15,25,0.95);");
        out.println("          border-bottom: 1px solid rgba(99,179,237,0.15); position: sticky; top:0; z-index:100; }");
        out.println("    .logo { font-size: 1.5rem; font-weight: 800; color: #63b3ed; letter-spacing: -0.5px; }");
        out.println("    .logo span { color: #fff; }");
        out.println("    nav ul { list-style: none; display: flex; gap: 2rem; }");
        out.println("    nav ul li a { color: #a0aec0; text-decoration: none; font-weight: 500; font-size: 0.95rem;");
        out.println("                  transition: color 0.2s; }");
        out.println("    nav ul li a:hover { color: #63b3ed; }");
        out.println("    .nav-cta { background: #63b3ed; color: #0a0a0f !important; padding: 0.5rem 1.2rem;");
        out.println("               border-radius: 6px; font-weight: 600 !important; transition: background 0.2s !important; }");
        out.println("    .nav-cta:hover { background: #90cdf4 !important; color: #0a0a0f !important; }");

        // --- HERO ---
        out.println("    .hero { min-height: 85vh; display: flex; flex-direction: column; justify-content: center;");
        out.println("            align-items: center; text-align: center; padding: 4rem 2rem;");
        out.println("            background: radial-gradient(ellipse at 50% 40%, rgba(99,179,237,0.12) 0%, transparent 70%); }");
        out.println("    .badge { display: inline-block; background: rgba(99,179,237,0.1); border: 1px solid rgba(99,179,237,0.3);");
        out.println("             color: #63b3ed; padding: 0.35rem 1rem; border-radius: 20px; font-size: 0.8rem;");
        out.println("             font-weight: 600; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 1.5rem; }");
        out.println("    .hero h1 { font-size: clamp(2.5rem, 6vw, 4.5rem); font-weight: 800; line-height: 1.1;");
        out.println("               color: #fff; margin-bottom: 1.5rem; max-width: 800px; }");
        out.println("    .hero h1 span { background: linear-gradient(135deg, #63b3ed, #a78bfa); -webkit-background-clip: text;");
        out.println("                    -webkit-text-fill-color: transparent; background-clip: text; }");
        out.println("    .hero p { font-size: 1.15rem; color: #a0aec0; max-width: 580px; line-height: 1.7; margin-bottom: 2.5rem; }");
        out.println("    .hero-actions { display: flex; gap: 1rem; flex-wrap: wrap; justify-content: center; }");
        out.println("    .btn-primary { background: linear-gradient(135deg, #63b3ed, #a78bfa); color: #0a0a0f;");
        out.println("                   padding: 0.85rem 2rem; border-radius: 8px; text-decoration: none;");
        out.println("                   font-weight: 700; font-size: 1rem; transition: opacity 0.2s; }");
        out.println("    .btn-primary:hover { opacity: 0.85; }");
        out.println("    .btn-secondary { border: 1px solid rgba(99,179,237,0.4); color: #63b3ed;");
        out.println("                     padding: 0.85rem 2rem; border-radius: 8px; text-decoration: none;");
        out.println("                     font-weight: 600; font-size: 1rem; transition: background 0.2s; }");
        out.println("    .btn-secondary:hover { background: rgba(99,179,237,0.08); }");

        // --- STATS ---
        out.println("    .stats { display: flex; justify-content: center; gap: 4rem; padding: 3rem 2rem;");
        out.println("             border-top: 1px solid rgba(255,255,255,0.06); border-bottom: 1px solid rgba(255,255,255,0.06);");
        out.println("             background: rgba(255,255,255,0.02); flex-wrap: wrap; }");
        out.println("    .stat { text-align: center; }");
        out.println("    .stat-number { font-size: 2.2rem; font-weight: 800; color: #63b3ed; }");
        out.println("    .stat-label { font-size: 0.85rem; color: #718096; margin-top: 0.2rem; text-transform: uppercase; letter-spacing: 0.5px; }");

        // --- COURSES ---
        out.println("    .courses { padding: 5rem 4rem; }");
        out.println("    .section-header { text-align: center; margin-bottom: 3rem; }");
        out.println("    .section-header h2 { font-size: 2.2rem; font-weight: 800; color: #fff; margin-bottom: 0.75rem; }");
        out.println("    .section-header p { color: #718096; font-size: 1rem; }");
        out.println("    .cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 1.5rem; }");
        out.println("    .card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.08);");
        out.println("            border-radius: 12px; padding: 1.8rem; transition: border-color 0.25s, transform 0.25s; }");
        out.println("    .card:hover { border-color: rgba(99,179,237,0.4); transform: translateY(-4px); }");
        out.println("    .card-icon { font-size: 2rem; margin-bottom: 1rem; }");
        out.println("    .card h3 { color: #fff; font-size: 1.1rem; font-weight: 700; margin-bottom: 0.5rem; }");
        out.println("    .card p { color: #718096; font-size: 0.9rem; line-height: 1.6; margin-bottom: 1rem; }");
        out.println("    .card-meta { font-size: 0.8rem; color: #63b3ed; font-weight: 600; }");

        // --- FOOTER ---
        out.println("    footer { text-align: center; padding: 2.5rem; background: rgba(0,0,0,0.3);");
        out.println("             border-top: 1px solid rgba(255,255,255,0.06); color: #4a5568; font-size: 0.875rem; }");
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
        out.println("      <li><a href='#'>Admissions</a></li>");
        out.println("      <li><a class='nav-cta' href='#'>Apply Now</a></li>");
        out.println("    </ul>");
        out.println("  </nav>");

        // HERO
        out.println("  <section class='hero'>");
        out.println("    <div class='badge'>&#128640; Ranked #1 Tech College in East Africa</div>");
        out.println("    <h1>Shape the Future with <span>World-Class Technology</span> Education</h1>");
        out.println("    <p>NexTech College of Technology equips the next generation of innovators, developers, and engineers with the skills to lead in a digital world.</p>");
        out.println("    <div class='hero-actions'>");
        out.println("      <a class='btn-primary' href='#'>Explore Programs</a>");
        out.println("      <a class='btn-secondary' href='/simple-webapp/about'>About Us &rarr;</a>");
        out.println("    </div>");
        out.println("  </section>");

        // STATS
        out.println("  <div class='stats'>");
        out.println("    <div class='stat'><div class='stat-number'>12,000+</div><div class='stat-label'>Students Enrolled</div></div>");
        out.println("    <div class='stat'><div class='stat-number'>94%</div><div class='stat-label'>Employment Rate</div></div>");
        out.println("    <div class='stat'><div class='stat-number'>45+</div><div class='stat-label'>Industry Partners</div></div>");
        out.println("    <div class='stat'><div class='stat-number'>20 Yrs</div><div class='stat-label'>Of Excellence</div></div>");
        out.println("  </div>");

        // COURSES
        out.println("  <section class='courses'>");
        out.println("    <div class='section-header'>");
        out.println("      <h2>Our Flagship Programs</h2>");
        out.println("      <p>Industry-aligned curricula built with top technology companies.</p>");
        out.println("    </div>");
        out.println("    <div class='cards'>");

        out.println("      <div class='card'>");
        out.println("        <div class='card-icon'>&#128187;</div>");
        out.println("        <h3>Software Engineering</h3>");
        out.println("        <p>From algorithms to cloud-native applications. Master full-stack development with Java, Spring Boot, and React.</p>");
        out.println("        <div class='card-meta'>3 Years &bull; Bachelor of Technology</div>");
        out.println("      </div>");

        out.println("      <div class='card'>");
        out.println("        <div class='card-icon'>&#129302;</div>");
        out.println("        <h3>Artificial Intelligence & ML</h3>");
        out.println("        <p>Dive deep into machine learning, neural networks, and AI ethics. Build intelligent systems that solve real problems.</p>");
        out.println("        <div class='card-meta'>3 Years &bull; Bachelor of Technology</div>");
        out.println("      </div>");

        out.println("      <div class='card'>");
        out.println("        <div class='card-icon'>&#128274;</div>");
        out.println("        <h3>Cybersecurity</h3>");
        out.println("        <p>Defend digital infrastructure. Learn ethical hacking, network security, and digital forensics from industry veterans.</p>");
        out.println("        <div class='card-meta'>3 Years &bull; Bachelor of Technology</div>");
        out.println("      </div>");

        out.println("      <div class='card'>");
        out.println("        <div class='card-icon'>&#9729;</div>");
        out.println("        <h3>Cloud & DevOps Engineering</h3>");
        out.println("        <p>Master AWS, Azure, Docker, Kubernetes, and CI/CD pipelines. The most in-demand skills in today's job market.</p>");
        out.println("        <div class='card-meta'>2 Years &bull; Diploma</div>");
        out.println("      </div>");

        out.println("    </div>");
        out.println("  </section>");

        // FOOTER
        out.println("  <footer>");
        out.println("    <p>&copy; 2026 NexTech College of Technology. All rights reserved. &nbsp;|&nbsp; <a href='/simple-webapp/about'>About Us</a></p>");
        out.println("  </footer>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void destroy() {
        System.out.println("==> MyFirstServlet (Welcome Page): destroy() called.");
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    @Override
    public String getServletInfo() {
        return "NexTech College Welcome Page - implements Servlet interface";
    }
}
