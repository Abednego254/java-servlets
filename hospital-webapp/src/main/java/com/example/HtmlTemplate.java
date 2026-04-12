package com.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HtmlTemplate {

    /**
     * Renders the common HTML header, CSS, and navigation bar.
     */
    public static void renderHeader(HttpServletRequest request, PrintWriter out, String title, String activeNav) {

        // Check for active session for dynamic navigation
        jakarta.servlet.http.HttpSession session = (request != null) ? request.getSession(false) : null;
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>" + title + "</title>");
        out.println("  <link href='https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700;800&display=swap' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Outfit', sans-serif; }");
        out.println("    body { background: #f8fafc; color: #0f172a; overflow-x: hidden; min-height: 100vh; display: flex; flex-direction: column; }");
        out.println("    .main-content { flex: 1; }");
        
        // Backgrounds
        out.println("    .bg-shape1 { position: absolute; top: -20%; left: -10%; width: 50vw; height: 50vw; background: radial-gradient(circle, rgba(56,189,248,0.15) 0%, rgba(255,255,255,0) 70%); border-radius: 50%; z-index: -1; animation: float 15s ease-in-out infinite; }");
        out.println("    .bg-shape2 { position: absolute; bottom: -10%; right: -10%; width: 40vw; height: 40vw; background: radial-gradient(circle, rgba(52,211,153,0.15) 0%, rgba(255,255,255,0) 70%); border-radius: 50%; z-index: -1; animation: float 20s ease-in-out infinite reverse; }");

        // Animations
        out.println("    @keyframes float { 0% { transform: translateY(0) scale(1); } 50% { transform: translateY(-20px) scale(1.05); } 100% { transform: translateY(0) scale(1); } }");
        out.println("    @keyframes slideUp { from { opacity: 0; transform: translateY(30px); } to { opacity: 1; transform: translateY(0); } }");
        out.println("    @keyframes bounce { 0%, 20%, 50%, 80%, 100% {transform: translateY(0);} 40% {transform: translateY(-20px);} 60% {transform: translateY(-10px);} }");

        // Nav
        out.println("    nav { display: flex; justify-content: space-between; align-items: center; padding: 1.2rem 4rem; background: rgba(255, 255, 255, 0.85); backdrop-filter: blur(12px); -webkit-backdrop-filter: blur(12px); border-bottom: 1px solid rgba(255,255,255,0.5); position: sticky; top:0; z-index:100; box-shadow: 0 4px 20px rgba(0,0,0,0.03); }");
        out.println("    .logo { font-size: 1.8rem; font-weight: 800; color: #0284c7; letter-spacing: -0.5px; }");
        out.println("    .logo span { color: #0f172a; }");
        out.println("    nav ul { list-style: none; display: flex; gap: 2.5rem; align-items: center; }");
        out.println("    nav ul li a { color: #475569; text-decoration: none; font-weight: 600; font-size: 1.05rem; transition: color 0.3s; }");
        out.println("    nav ul li a:hover, nav ul li a.active { color: #0284c7; }");
        out.println("    .nav-cta { background: linear-gradient(135deg, #0ea5e9, #0284c7) !important; color: #fff !important; padding: 0.7rem 1.8rem; border-radius: 30px; font-weight: 700 !important; box-shadow: 0 4px 15px rgba(2, 132, 199, 0.3); transition: transform 0.3s, box-shadow 0.3s !important; }");
        out.println("    .nav-cta:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(2, 132, 199, 0.4); }");

        // Helper classes from Home
        out.println("    .hero { min-height: 85vh; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; padding: 4rem 2rem; position: relative; }");
        out.println("    .badge { display: inline-block; background: #e0f2fe; border: 1px solid #bae6fd; color: #0284c7; padding: 0.5rem 1.2rem; border-radius: 30px; font-size: 0.85rem; font-weight: 700; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 1.5rem; animation: slideUp 0.8s ease-out; }");
        out.println("    .hero h1 { font-size: clamp(3rem, 7vw, 5rem); font-weight: 800; line-height: 1.1; color: #0f172a; margin-bottom: 1.5rem; max-width: 900px; animation: slideUp 1s ease-out; }");
        out.println("    .hero h1 span { background: linear-gradient(135deg, #0ea5e9, #10b981); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }");
        out.println("    .hero p { font-size: 1.2rem; color: #475569; max-width: 650px; line-height: 1.8; margin-bottom: 2.5rem; animation: slideUp 1.2s ease-out; }");
        out.println("    .hero-actions { display: flex; gap: 1.5rem; flex-wrap: wrap; justify-content: center; animation: slideUp 1.4s ease-out; }");
        out.println("    .btn-primary { background: linear-gradient(135deg, #0ea5e9, #0284c7); color: #fff; padding: 1rem 2.5rem; border-radius: 30px; text-decoration: none; font-weight: 700; font-size: 1.1rem; box-shadow: 0 10px 25px rgba(2, 132, 199, 0.3); transition: 0.3s; display: inline-block; border: none; cursor: pointer; }");
        out.println("    .btn-primary:hover { transform: translateY(-3px); box-shadow: 0 15px 30px rgba(2, 132, 199, 0.4); }");
        out.println("    .btn-secondary { background: #fff; color: #0f172a; padding: 1rem 2.5rem; border-radius: 30px; text-decoration: none; font-weight: 700; font-size: 1.1rem; border: 1px solid #e2e8f0; box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05); transition: 0.3s; display: inline-block; }");
        out.println("    .btn-secondary:hover { transform: translateY(-3px); box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1); border-color: #cbd5e1; }");
        out.println("    .stats { display: flex; justify-content: center; gap: 5rem; padding: 4rem 2rem; background: rgba(255,255,255,0.7); backdrop-filter: blur(10px); flex-wrap: wrap; border-top: 1px solid rgba(255,255,255,0.8); border-bottom: 1px solid rgba(255,255,255,0.8); box-shadow: 0 4px 30px rgba(0,0,0,0.02); }");
        out.println("    .stat { text-align: center; transition: 0.3s; }");
        out.println("    .stat:hover { transform: translateY(-5px); }");
        out.println("    .stat-number { font-size: 3.5rem; font-weight: 800; background: linear-gradient(135deg, #0ea5e9, #0284c7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }");
        out.println("    .stat-label { font-size: 0.95rem; font-weight: 700; color: #64748b; margin-top: 0.5rem; text-transform: uppercase; letter-spacing: 1.5px; }");
        out.println("    .depts { padding: 6rem 4rem; position: relative; }");
        out.println("    .section-header { text-align: center; margin-bottom: 4rem; }");
        out.println("    .section-header h2 { font-size: 2.8rem; font-weight: 800; color: #0f172a; margin-bottom: 1rem; }");
        out.println("    .section-header p { color: #64748b; font-size: 1.1rem; max-width: 600px; margin: 0 auto; }");
        out.println("    .cards, .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 2rem; }");
        out.println("    .card, .service-card { background: rgba(255, 255, 255, 0.9); border: 1px solid #f1f5f9; border-radius: 20px; padding: 2.5rem; box-shadow: 0 10px 30px rgba(0,0,0,0.03); transition: 0.4s; display: flex; flex-direction: column; align-items: flex-start; }");
        out.println("    .card:hover, .service-card:hover { transform: translateY(-10px); box-shadow: 0 20px 40px rgba(2, 132, 199, 0.1); border-color: #e0f2fe; }");
        out.println("    .card-icon, .service-icon { font-size: 3rem; margin-bottom: 1.5rem; display: inline-block; padding: 1rem; background: #f8fafc; border-radius: 16px; transition: 0.3s; }");
        out.println("    .card:hover .card-icon, .service-card:hover .service-icon { transform: scale(1.1) rotate(5deg); background: #f0f9ff; }");
        out.println("    .card h3, .service-card h3 { color: #0f172a; font-size: 1.4rem; font-weight: 700; margin-bottom: 1rem; }");
        out.println("    .card p, .service-card p { color: #475569; font-size: 1rem; line-height: 1.7; flex-grow: 1; }");
        
        // Helper classes from Services
        out.println("    .page-header { padding: 6rem 4rem 4rem; text-align: center; position: relative; }");
        out.println("    .services-section { padding: 2rem 4rem 6rem; max-width: 1300px; margin: 0 auto; position: relative; }");
        out.println("    .section-label { font-size: 0.9rem; font-weight: 700; text-transform: uppercase; letter-spacing: 2px; color: #10b981; margin-bottom: 0.6rem; }");
        out.println("    .tag { display: inline-block; background: #f0f9ff; border: 1px solid #bae6fd; color: #0284c7; font-size: 0.8rem; font-weight: 700; padding: 0.4rem 1rem; border-radius: 20px; margin-top: 1.5rem; }");
        out.println("    .cta-banner { margin: 0 4rem 6rem; background: linear-gradient(135deg, rgba(14, 165, 233, 0.05), rgba(16, 185, 129, 0.05)); border: 1px solid #e0f2fe; border-radius: 24px; padding: 4rem; text-align: center; box-shadow: 0 20px 40px rgba(0,0,0,0.02); }");
        out.println("    .cta-banner h2 { font-size: 2.2rem; font-weight: 800; color: #0f172a; margin-bottom: 1rem; }");
        out.println("    .cta-banner p { color: #475569; font-size: 1.1rem; margin-bottom: 2rem; }");

        // Helper classes from Booking Forms
        out.println("    .page-wrap { min-height: calc(100vh - 80px); display: flex; align-items: center; justify-content: center; padding: 4rem 2rem; position: relative; }");
        out.println("    .form-card { background: rgba(255,255,255,0.9); backdrop-filter: blur(15px); border: 1px solid #fff; border-radius: 24px; padding: 3rem 3.5rem; width: 100%; max-width: 650px; box-shadow: 0 20px 50px rgba(0,0,0,0.05); animation: slideUp 0.8s ease-out; }");
        out.println("    .form-card h1 { font-size: 2.2rem; font-weight: 800; color: #0f172a; margin-bottom: 0.5rem; text-align: left; }");
        out.println("    .form-card.center h1 { text-align: center; }");
        out.println("    .form-card .subtitle { color: #64748b; font-size: 1rem; margin-bottom: 2.5rem; line-height: 1.6; }");
        out.println("    .form-card.center .subtitle { text-align: center; }");
        out.println("    .form-group { margin-bottom: 1.6rem; }");
        out.println("    label { display: block; font-size: 0.85rem; font-weight: 700; color: #475569; margin-bottom: 0.5rem; text-transform: uppercase; letter-spacing: 1px; text-align: left; }");
        out.println("    input, select, textarea { width: 100%; background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 12px; padding: 0.9rem 1.2rem; color: #0f172a; font-family: 'Outfit', sans-serif; font-size: 1rem; transition: 0.3s; outline: none; }");
        out.println("    input:focus, select:focus, textarea:focus { background: #fff; border-color: #38bdf8; box-shadow: 0 0 0 4px rgba(56,189,248,0.15); transform: translateY(-2px); }");
        out.println("    textarea { resize: vertical; min-height: 100px; }");
        out.println("    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; }");
        out.println("    .btn-submit { width: 100%; background: linear-gradient(135deg, #0ea5e9, #0284c7); color: #fff; border: none; padding: 1.1rem; border-radius: 12px; font-weight: 700; font-size: 1.1rem; cursor: pointer; margin-top: 1rem; font-family: 'Outfit', sans-serif; transition: 0.3s; box-shadow: 0 10px 25px rgba(2, 132, 199, 0.3); }");
        out.println("    .btn-submit:hover { transform: translateY(-3px); box-shadow: 0 15px 30px rgba(2, 132, 199, 0.4); }");
        out.println("    .divider { border: none; border-top: 1px solid #f1f5f9; margin: 2rem 0; }");

        out.println("    .success-icon { font-size: 5rem; text-align: center; margin-bottom: 1.5rem; animation: bounce 2s infinite; }");
        out.println("    .ref-badge { background: #f0f9ff; border: 1px solid #bae6fd; border-radius: 16px; padding: 1.5rem; text-align: center; margin-bottom: 2.5rem; }");
        out.println("    .ref-badge .label { font-size: 0.85rem; font-weight: 700; text-transform: uppercase; letter-spacing: 1.5px; color: #0284c7; margin-bottom: 0.5rem; }");
        out.println("    .ref-badge .ref { font-size: 2rem; font-weight: 800; color: #0369a1; letter-spacing: 3px; }");
        out.println("    .details { border-top: 1px solid #f1f5f9; padding-top: 2rem; }");
        out.println("    .detail-row { display: flex; justify-content: space-between; padding: 1rem 0; border-bottom: 1px solid #f8fafc; font-size: 1rem; }");
        out.println("    .detail-row .field { color: #64748b; font-weight: 500; }");
        out.println("    .detail-row .value { color: #0f172a; font-weight: 700; text-align: right; }");
        out.println("    .actions { display: flex; gap: 1.5rem; margin-top: 3rem; flex-wrap: wrap; }");
        out.println("    .btn { flex: 1; text-align: center; padding: 1.1rem; border-radius: 12px; text-decoration: none; font-weight: 700; font-size: 1.05rem; transition: 0.3s; }");
        out.println("    .btn-outline { border: 2px solid #e0f2fe; color: #0284c7; background: #fff; }");
        out.println("    .btn-outline:hover { background: #f0f9ff; border-color: #bae6fd; transform: translateY(-3px); }");

        // Footer
        out.println("    footer { text-align: center; padding: 3rem; background: #fff; border-top: 1px solid #f1f5f9; color: #64748b; font-size: 1rem; font-weight: 500; margin-top: auto; }");
        out.println("    footer a { color: #0284c7; text-decoration: none; font-weight: 600; transition: 0.2s; }");
        out.println("    footer a:hover { color: #0ea5e9; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("  <div class='bg-shape1'></div>");
        out.println("  <div class='bg-shape2'></div>");
        out.println("  <div class='main-content'>");
        out.println("    <nav>");
        out.println("      <div class='logo'>Sanity<span>Care</span></div>");
        out.println("      <ul>");
        
        String homeCls = "home".equals(activeNav) ? "class='active'" : "";
        String servicesCls = "services".equals(activeNav) ? "class='active'" : "";
        String appointCls = "appointment".equals(activeNav) ? "class='nav-cta active'" : "class='nav-cta'";
        
        out.println("        <li><a " + homeCls + " href='/hospital-webapp/home'>Home</a></li>");
        out.println("        <li><a " + servicesCls + " href='/hospital-webapp/services'>Our Services</a></li>");

        if (isLoggedIn) {
            String staffCls = "staff".equals(activeNav) ? "class='active'" : "";
            String registerCls = "register".equals(activeNav) ? "class='active'" : "";
            out.println("        <li><a " + staffCls + " href='/hospital-webapp/staff'>Staff Directory</a></li>");
            out.println("        <li><a " + registerCls + " href='/hospital-webapp/register'>New Enrollment</a></li>");
            out.println("        <li><a href='/hospital-webapp/logout' style='color: #ef4444;'>Logout</a></li>");
        } else {
            String loginCls = "login".equals(activeNav) ? "class='active'" : "";
            out.println("        <li><a " + loginCls + " href='/hospital-webapp/login'>Staff Login</a></li>");
        }

        out.println("        <li><a " + appointCls + " href='/hospital-webapp/appointment'>Book Appointment</a></li>");
        out.println("      </ul>");
        out.println("    </nav>");
    }


    /**
     * Renders the common HTML footer.
     * @deprecated Use includeFooter instead for dynamic dispatching.
     */
    public static void renderFooter(PrintWriter out) {
        out.println("  </div>"); // end main-content
        out.println("  <footer>");
        out.println("    <p>&copy; 2026 SanityCare Hospital &nbsp;|&nbsp; <a href='/hospital-webapp/home'>Home</a> &nbsp;|&nbsp; <a href='/hospital-webapp/services'>Services</a> &nbsp;|&nbsp; <a href='/hospital-webapp/appointment'>Book Appointment</a></p>");
        out.println("  </footer>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Includes the dynamic footer from FooterServlet using RequestDispatcher.
     * This follows the trainer's "Modular UI" approach.
     */
    public static void includeFooter(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        request.getRequestDispatcher("/footer").include(request, response);
    }
}
