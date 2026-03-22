package com.example;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * AboutUsServlet — About Us Page for NexTech College of Technology.
 *
 * Created by EXTENDING GenericServlet (the 2nd way to make a servlet).
 *
 * Key difference from implementing Servlet directly:
 *   - GenericServlet already implements the Servlet interface for you.
 *   - It provides default (empty) implementations of init(), destroy(),
 *     getServletConfig(), and getServletInfo().
 *   - You ONLY need to override the one method that matters: service().
 *   - This makes your code much cleaner — less boilerplate!
 *
 * Registered via web.xml — no @WebServlet annotation needed.
 */
public class AboutUsServlet extends GenericServlet {

    /**
     * service() is the only method we MUST override.
     * GenericServlet handles everything else automatically.
     */
    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> AboutUsServlet: service() called.");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>About Us | NexTech College of Technology</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #0a0a0f; color: #e0e0e0; }");

        // NAV
        out.println("    nav { display: flex; justify-content: space-between; align-items: center;");
        out.println("          padding: 1.2rem 4rem; background: rgba(15,15,25,0.95);");
        out.println("          border-bottom: 1px solid rgba(99,179,237,0.15); position: sticky; top:0; z-index:100; }");
        out.println("    .logo { font-size: 1.5rem; font-weight: 800; color: #63b3ed; letter-spacing: -0.5px; }");
        out.println("    .logo span { color: #fff; }");
        out.println("    nav ul { list-style: none; display: flex; gap: 2rem; }");
        out.println("    nav ul li a { color: #a0aec0; text-decoration: none; font-weight: 500; font-size: 0.95rem; transition: color 0.2s; }");
        out.println("    nav ul li a:hover, nav ul li a.active { color: #63b3ed; }");
        out.println("    .nav-cta { background: #63b3ed; color: #0a0a0f !important; padding: 0.5rem 1.2rem;");
        out.println("               border-radius: 6px; font-weight: 600 !important; }");

        // PAGE HEADER
        out.println("    .page-header { padding: 5rem 4rem 3rem; text-align: center;");
        out.println("                   background: radial-gradient(ellipse at 50% 0%, rgba(167,139,250,0.12) 0%, transparent 65%); }");
        out.println("    .page-header .badge { display: inline-block; background: rgba(167,139,250,0.1);");
        out.println("                          border: 1px solid rgba(167,139,250,0.3); color: #a78bfa;");
        out.println("                          padding: 0.35rem 1rem; border-radius: 20px; font-size: 0.8rem;");
        out.println("                          font-weight: 600; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 1.2rem; }");
        out.println("    .page-header h1 { font-size: clamp(2rem, 5vw, 3.5rem); font-weight: 800; color: #fff; margin-bottom: 1rem; }");
        out.println("    .page-header h1 span { background: linear-gradient(135deg, #a78bfa, #63b3ed);");
        out.println("                            -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }");
        out.println("    .page-header p { color: #a0aec0; font-size: 1.1rem; max-width: 600px; margin: 0 auto; line-height: 1.7; }");

        // MISSION
        out.println("    .section { padding: 4rem; }");
        out.println("    .section-alt { background: rgba(255,255,255,0.02); border-top: 1px solid rgba(255,255,255,0.06);");
        out.println("                   border-bottom: 1px solid rgba(255,255,255,0.06); }");
        out.println("    .section-inner { max-width: 1100px; margin: 0 auto; }");
        out.println("    .section-label { font-size: 0.75rem; font-weight: 700; text-transform: uppercase; letter-spacing: 2px;");
        out.println("                     color: #a78bfa; margin-bottom: 0.6rem; }");
        out.println("    .section h2 { font-size: 2rem; font-weight: 800; color: #fff; margin-bottom: 1rem; }");
        out.println("    .section p { color: #a0aec0; line-height: 1.8; font-size: 1rem; max-width: 720px; }");

        // VALUES
        out.println("    .values-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));");
        out.println("                   gap: 1.25rem; margin-top: 2.5rem; }");
        out.println("    .value-card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.08);");
        out.println("                  border-radius: 12px; padding: 1.6rem; transition: border-color 0.25s, transform 0.25s; }");
        out.println("    .value-card:hover { border-color: rgba(167,139,250,0.4); transform: translateY(-3px); }");
        out.println("    .value-icon { font-size: 1.8rem; margin-bottom: 0.8rem; }");
        out.println("    .value-card h3 { color: #fff; font-size: 1rem; font-weight: 700; margin-bottom: 0.4rem; }");
        out.println("    .value-card p { color: #718096; font-size: 0.875rem; line-height: 1.6; }");

        // TEAM
        out.println("    .team-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));");
        out.println("                 gap: 1.5rem; margin-top: 2.5rem; }");
        out.println("    .team-card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.08);");
        out.println("                 border-radius: 12px; padding: 1.8rem; text-align: center;");
        out.println("                 transition: border-color 0.25s, transform 0.25s; }");
        out.println("    .team-card:hover { border-color: rgba(99,179,237,0.4); transform: translateY(-3px); }");
        out.println("    .avatar { width: 70px; height: 70px; border-radius: 50%; margin: 0 auto 1rem;");
        out.println("              display: flex; align-items: center; justify-content: center;");
        out.println("              font-size: 1.8rem; background: rgba(99,179,237,0.1); border: 2px solid rgba(99,179,237,0.25); }");
        out.println("    .team-card h3 { color: #fff; font-size: 1rem; font-weight: 700; margin-bottom: 0.25rem; }");
        out.println("    .team-card .role { color: #63b3ed; font-size: 0.8rem; font-weight: 600; margin-bottom: 0.5rem; }");
        out.println("    .team-card p { color: #718096; font-size: 0.8rem; line-height: 1.5; }");

        // CTA
        out.println("    .cta { text-align: center; padding: 5rem 2rem; }");
        out.println("    .cta h2 { font-size: 2rem; font-weight: 800; color: #fff; margin-bottom: 1rem; }");
        out.println("    .cta p { color: #718096; margin-bottom: 2rem; }");
        out.println("    .btn-primary { background: linear-gradient(135deg, #a78bfa, #63b3ed); color: #0a0a0f;");
        out.println("                   padding: 0.85rem 2rem; border-radius: 8px; text-decoration: none;");
        out.println("                   font-weight: 700; font-size: 1rem; display: inline-block; }");
        out.println("    .btn-primary:hover { opacity: 0.85; }");

        // FOOTER
        out.println("    footer { text-align: center; padding: 2rem; background: rgba(0,0,0,0.3);");
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
        out.println("      <li><a class='active' href='/simple-webapp/about'>About Us</a></li>");
        out.println("      <li><a href='#'>Courses</a></li>");
        out.println("      <li><a href='#'>Admissions</a></li>");
        out.println("      <li><a class='nav-cta' href='#'>Apply Now</a></li>");
        out.println("    </ul>");
        out.println("  </nav>");

        // PAGE HEADER
        out.println("  <div class='page-header'>");
        out.println("    <div class='badge'>&#127979; Our Story</div>");
        out.println("    <h1>Building <span>Tomorrow's Tech Leaders</span> Since 2006</h1>");
        out.println("    <p>NexTech College was founded with a single mission: to close the skills gap in Africa's technology sector through hands-on, industry-led education.</p>");
        out.println("  </div>");

        // MISSION
        out.println("  <div class='section section-inner' style='padding: 4rem;'>");
        out.println("    <div class='section-inner'>");
        out.println("      <div class='section-label'>Our Mission</div>");
        out.println("      <h2>We Don't Just Teach Technology. We Build Technologists.</h2>");
        out.println("      <p>At NexTech, we believe that the best education combines rigorous academic theory with real-world application. Every student graduates with a portfolio of live projects, industry certifications, and connections to our 45+ partner companies — from local startups to global tech giants.</p>");
        out.println("    </div>");
        out.println("  </div>");

        // VALUES
        out.println("  <div class='section section-alt'>");
        out.println("    <div class='section-inner'>");
        out.println("      <div class='section-label'>What We Stand For</div>");
        out.println("      <h2>Our Core Values</h2>");
        out.println("      <div class='values-grid'>");

        out.println("        <div class='value-card'>");
        out.println("          <div class='value-icon'>&#128161;</div>");
        out.println("          <h3>Innovation First</h3>");
        out.println("          <p>We challenge students to question the status quo and build solutions that have never existed before.</p>");
        out.println("        </div>");

        out.println("        <div class='value-card'>");
        out.println("          <div class='value-icon'>&#129309;</div>");
        out.println("          <h3>Community & Inclusion</h3>");
        out.println("          <p>Technology should be for everyone. We actively support students from underprivileged backgrounds.</p>");
        out.println("        </div>");

        out.println("        <div class='value-card'>");
        out.println("          <div class='value-icon'>&#127919;</div>");
        out.println("          <h3>Industry Relevance</h3>");
        out.println("          <p>Our curriculum is reviewed every semester with our industry partners to ensure graduates are job-ready on day one.</p>");
        out.println("        </div>");

        out.println("        <div class='value-card'>");
        out.println("          <div class='value-icon'>&#127775;</div>");
        out.println("          <h3>Excellence Always</h3>");
        out.println("          <p>We hold ourselves and our students to the highest standards — in code quality, communication, and character.</p>");
        out.println("        </div>");

        out.println("      </div>");
        out.println("    </div>");
        out.println("  </div>");

        // TEAM
        out.println("  <div class='section'>");
        out.println("    <div class='section-inner'>");
        out.println("      <div class='section-label'>Leadership Team</div>");
        out.println("      <h2>Meet the People Behind NexTech</h2>");
        out.println("      <div class='team-grid'>");

        out.println("        <div class='team-card'>");
        out.println("          <div class='avatar'>&#128104;&#8205;&#128187;</div>");
        out.println("          <h3>Dr. James Ochieng</h3>");
        out.println("          <div class='role'>Principal & Founder</div>");
        out.println("          <p>PhD Computer Science, MIT. 20+ years building tech education ecosystems across Africa.</p>");
        out.println("        </div>");

        out.println("        <div class='team-card'>");
        out.println("          <div class='avatar'>&#128105;&#8205;&#128187;</div>");
        out.println("          <h3>Prof. Amina Wanjiku</h3>");
        out.println("          <div class='role'>Head of Software Engineering</div>");
        out.println("          <p>Former Google engineer. Specializes in distributed systems and cloud architecture.</p>");
        out.println("        </div>");

        out.println("        <div class='team-card'>");
        out.println("          <div class='avatar'>&#129489;&#8205;&#128300;</div>");
        out.println("          <h3>Mr. David Kamau</h3>");
        out.println("          <div class='role'>Head of Cybersecurity</div>");
        out.println("          <p>Certified Ethical Hacker (CEH). Previously led security ops at Safaricom.</p>");
        out.println("        </div>");

        out.println("        <div class='team-card'>");
        out.println("          <div class='avatar'>&#128105;&#8205;&#128640;</div>");
        out.println("          <h3>Ms. Grace Mutua</h3>");
        out.println("          <div class='role'>Head of AI & Data Science</div>");
        out.println("          <p>MSc Machine Learning, UCL. Research interests in NLP and computer vision.</p>");
        out.println("        </div>");

        out.println("      </div>");
        out.println("    </div>");
        out.println("  </div>");

        // CTA
        out.println("  <div class='cta'>");
        out.println("    <h2>Ready to Start Your Journey?</h2>");
        out.println("    <p>Join 12,000+ students building the future at NexTech College.</p>");
        out.println("    <a class='btn-primary' href='/simple-webapp/hello'>&#8592; Back to Home</a>");
        out.println("  </div>");

        // FOOTER
        out.println("  <footer>");
        out.println("    <p>&copy; 2026 NexTech College of Technology &nbsp;|&nbsp; <a href='/simple-webapp/hello'>Home</a> &nbsp;|&nbsp; <a href='/simple-webapp/about'>About Us</a></p>");
        out.println("  </footer>");

        out.println("</body>");
        out.println("</html>");
    }
}
