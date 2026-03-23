package com.example;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ServicesServlet extends GenericServlet {

    /**
     * service() is the ONLY method we must override.
     * GenericServlet handles all lifecycle boilerplate for us.
     */
    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        System.out.println("==> ServicesServlet: service() called — serving the services page.");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>Our Services | SanityCare Hospital</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("    body { font-family: 'Inter', sans-serif; background: #050d1a; color: #e0e6f0; }");

        // NAV
        out.println("    nav { display: flex; justify-content: space-between; align-items: center;");
        out.println("          padding: 1.2rem 4rem; background: rgba(5,13,30,0.97);");
        out.println("          border-bottom: 1px solid rgba(56,189,248,0.15); position: sticky; top:0; z-index:100; }");
        out.println("    .logo { font-size: 1.5rem; font-weight: 800; color: #38bdf8; }");
        out.println("    .logo span { color: #fff; }");
        out.println("    nav ul { list-style: none; display: flex; gap: 2rem; }");
        out.println("    nav ul li a { color: #94a3b8; text-decoration: none; font-weight: 500; font-size: 0.95rem; transition: color 0.2s; }");
        out.println("    nav ul li a:hover, nav ul li a.active { color: #38bdf8; }");
        out.println("    .nav-cta { background: #38bdf8; color: #050d1a !important; padding: 0.5rem 1.2rem; border-radius: 6px; font-weight: 700 !important; }");

        // PAGE HEADER
        out.println("    .page-header { padding: 5rem 4rem 3rem; text-align: center;");
        out.println("                   background: radial-gradient(ellipse at 50% 0%, rgba(52,211,153,0.1) 0%, transparent 65%); }");
        out.println("    .badge { display: inline-block; background: rgba(52,211,153,0.1); border: 1px solid rgba(52,211,153,0.3);");
        out.println("             color: #34d399; padding: 0.35rem 1rem; border-radius: 20px; font-size: 0.8rem;");
        out.println("             font-weight: 600; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 1.2rem; }");
        out.println("    .page-header h1 { font-size: clamp(2rem, 5vw, 3.5rem); font-weight: 800; color: #fff; margin-bottom: 1rem; }");
        out.println("    .page-header h1 span { background: linear-gradient(135deg, #34d399, #38bdf8);");
        out.println("                           -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }");
        out.println("    .page-header p { color: #94a3b8; font-size: 1.05rem; max-width: 600px; margin: 0 auto; line-height: 1.7; }");

        // SERVICES GRID
        out.println("    .services-section { padding: 4rem; max-width: 1200px; margin: 0 auto; }");
        out.println("    .section-label { font-size: 0.75rem; font-weight: 700; text-transform: uppercase; letter-spacing: 2px;");
        out.println("                     color: #34d399; margin-bottom: 0.6rem; }");
        out.println("    .services-section h2 { font-size: 1.8rem; font-weight: 800; color: #fff; margin-bottom: 2rem; }");
        out.println("    .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 1.5rem; }");
        out.println("    .service-card { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.08);");
        out.println("                    border-radius: 14px; padding: 2rem; transition: border-color 0.25s, transform 0.25s; }");
        out.println("    .service-card:hover { border-color: rgba(52,211,153,0.4); transform: translateY(-4px); }");
        out.println("    .service-icon { font-size: 2.2rem; margin-bottom: 1rem; }");
        out.println("    .service-card h3 { color: #fff; font-size: 1.05rem; font-weight: 700; margin-bottom: 0.5rem; }");
        out.println("    .service-card p { color: #64748b; font-size: 0.875rem; line-height: 1.65; margin-bottom: 1rem; }");
        out.println("    .tag { display: inline-block; background: rgba(56,189,248,0.1); border: 1px solid rgba(56,189,248,0.25);");
        out.println("           color: #38bdf8; font-size: 0.75rem; font-weight: 600; padding: 0.25rem 0.7rem; border-radius: 12px; }");

        // CTA BANNER
        out.println("    .cta-banner { margin: 0 4rem 4rem; background: linear-gradient(135deg, rgba(56,189,248,0.08), rgba(52,211,153,0.08));");
        out.println("                  border: 1px solid rgba(56,189,248,0.2); border-radius: 16px; padding: 3rem; text-align: center; }");
        out.println("    .cta-banner h2 { font-size: 1.8rem; font-weight: 800; color: #fff; margin-bottom: 0.75rem; }");
        out.println("    .cta-banner p { color: #94a3b8; margin-bottom: 1.8rem; }");
        out.println("    .btn-primary { background: linear-gradient(135deg, #38bdf8, #34d399); color: #050d1a;");
        out.println("                   padding: 0.85rem 2rem; border-radius: 8px; text-decoration: none;");
        out.println("                   font-weight: 700; font-size: 1rem; display: inline-block; transition: opacity 0.2s; }");
        out.println("    .btn-primary:hover { opacity: 0.85; }");

        // FOOTER
        out.println("    footer { text-align: center; padding: 2rem; background: rgba(0,0,0,0.4);");
        out.println("             border-top: 1px solid rgba(255,255,255,0.06); color: #475569; font-size: 0.875rem; }");
        out.println("    footer a { color: #38bdf8; text-decoration: none; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        // NAV
        out.println("  <nav>");
        out.println("    <div class='logo'>Sanity<span>Care</span></div>");
        out.println("    <ul>");
        out.println("      <li><a href='/hospital-webapp/home'>Home</a></li>");
        out.println("      <li><a class='active' href='/hospital-webapp/services'>Our Services</a></li>");
        out.println("      <li><a class='nav-cta' href='/hospital-webapp/appointment'>Book Appointment</a></li>");
        out.println("    </ul>");
        out.println("  </nav>");

        // PAGE HEADER
        out.println("  <div class='page-header'>");
        out.println("    <div class='badge'>&#127973; Our Specialties</div>");
        out.println("    <h1>Comprehensive <span>Medical Services</span></h1>");
        out.println("    <p>From routine check-ups to complex surgical procedures, SanityCare Hospital offers end-to-end healthcare under one roof.</p>");
        out.println("  </div>");

        // SERVICES GRID
        out.println("  <div class='services-section'>");
        out.println("    <div class='section-label'>What We Offer</div>");
        out.println("    <h2>Our Medical Departments &amp; Services</h2>");
        out.println("    <div class='grid'>");

        // Card 1
        out.println("      <div class='service-card'>");
        out.println("        <div class='service-icon'>&#10084;&#65039;</div>");
        out.println("        <h3>Cardiology</h3>");
        out.println("        <p>Heart disease diagnosis, angioplasty, bypass surgery, echocardiograms, and long-term cardiac rehabilitation programs.</p>");
        out.println("        <span class='tag'>Available 24/7</span>");
        out.println("      </div>");

        // Card 2
        out.println("      <div class='service-card'>");
        out.println("        <div class='service-icon'>&#129504;</div>");
        out.println("        <h3>Neurology &amp; Neurosurgery</h3>");
        out.println("        <p>Treatment of stroke, epilepsy, Parkinson's, brain tumors, and spinal cord conditions using advanced imaging technology.</p>");
        out.println("        <span class='tag'>Specialist Consult</span>");
        out.println("      </div>");

        // Card 3
        out.println("      <div class='service-card'>");
        out.println("        <div class='service-icon'>&#129455;</div>");
        out.println("        <h3>Orthopaedics &amp; Trauma</h3>");
        out.println("        <p>Fracture management, joint replacement (knee, hip), sports injury treatment, and comprehensive physiotherapy services.</p>");
        out.println("        <span class='tag'>Walk-in Welcome</span>");
        out.println("      </div>");

        // Card 4
        out.println("      <div class='service-card'>");
        out.println("        <div class='service-icon'>&#128118;</div>");
        out.println("        <h3>Paediatrics</h3>");
        out.println("        <p>Newborn care, immunisation programmes, developmental assessments, and specialist management of childhood illnesses.</p>");
        out.println("        <span class='tag'>Child-Friendly Ward</span>");
        out.println("      </div>");

        // Card 5
        out.println("      <div class='service-card'>");
        out.println("        <div class='service-icon'>&#128105;&#8205;&#9877;&#65039;</div>");
        out.println("        <h3>Obstetrics &amp; Gynaecology</h3>");
        out.println("        <p>Antenatal care, high-risk pregnancy management, labour &amp; delivery, gynaecological surgery, and family planning.</p>");
        out.println("        <span class='tag'>Women's Health Unit</span>");
        out.println("      </div>");

        // Card 6
        out.println("      <div class='service-card'>");
        out.println("        <div class='service-icon'>&#128300;</div>");
        out.println("        <h3>Oncology</h3>");
        out.println("        <p>Comprehensive cancer care including chemotherapy, radiotherapy, immunotherapy, and palliative support services.</p>");
        out.println("        <span class='tag'>MDT Approach</span>");
        out.println("      </div>");

        out.println("    </div>");
        out.println("  </div>");

        // CTA BANNER
        out.println("  <div class='cta-banner'>");
        out.println("    <h2>Ready to See a Specialist?</h2>");
        out.println("    <p>Book your appointment online in under 2 minutes. Our team will confirm within the hour.</p>");
        out.println("    <a class='btn-primary' href='/hospital-webapp/appointment'>Book an Appointment &rarr;</a>");
        out.println("  </div>");

        // FOOTER
        out.println("  <footer>");
        out.println("    <p>&copy; 2026 SanityCare Hospital &nbsp;|&nbsp; <a href='/hospital-webapp/home'>Home</a> &nbsp;|&nbsp; <a href='/hospital-webapp/services'>Services</a> &nbsp;|&nbsp; <a href='/hospital-webapp/appointment'>Book Appointment</a></p>");
        out.println("  </footer>");

        out.println("</body>");
        out.println("</html>");
    }
}
