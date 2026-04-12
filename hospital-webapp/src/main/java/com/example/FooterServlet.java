package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * FooterServlet — Modernized trainer's pattern.
 * This servlet renders the common footer for the entire hospital webapp.
 * It is meant to be included in other pages using dispatchers.
 */
public class FooterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        PrintWriter out = response.getWriter();

        // FETCH GLOBAL DATA from ServletContext
        String city     = getServletContext().getInitParameter("hospitalCity");
        String email    = getServletContext().getInitParameter("hospitalEmail");
        Integer visitors = (Integer) getServletContext().getAttribute("totalVisitors");
        Integer active   = (Integer) getServletContext().getAttribute("activeSessions");
        int count       = (visitors != null) ? visitors : 0;
        int onlineCount = (active   != null) ? active   : 0;

        out.println("  </div> <!-- end main-content from header -->");
        out.println("  <footer style='text-align: center; padding: 4rem 2rem; background: #fff; border-top: 1px solid #f1f5f9; color: #64748b;'>");
        out.println("    <div style='max-width: 1200px; margin: 0 auto; display: flex; flex-direction: column; gap: 1.5rem;'>");
        out.println("       <p style='font-size: 1.6rem; font-weight: 800; color: #0284c7;'>Sanity<span>Care</span></p>");
        out.println("       <p style='font-weight: 500;'>&copy; 2026 SanityCare Hospital. Your Health, Our Heart.</p>");

        // DISPLAY GLOBAL METADATA (from ServletContext init-params)
        out.println("       <p style='font-size: 0.9rem; color: #94a3b8;'>Serving " + city + " | Contact: <a href='mailto:" + email + "' style='color: #0284c7;'>" + email + "</a></p>");

        // DISPLAY GLOBAL STATE (Visitor Counter + Active Sessions — from Listeners)
        out.println("       <div style='display: flex; gap: 1rem; justify-content: center; flex-wrap: wrap;'>");
        out.println("           <div style='background: #f8fafc; padding: 0.8rem 1.5rem; border-radius: 12px;'>");
        out.println("               <span style='font-weight: 700; color: #0284c7;'>&#128065; Total Page Visits:</span> " + count);
        out.println("           </div>");
        out.println("           <div style='background: #ecfdf5; border: 1px solid #bbf7d0; padding: 0.8rem 1.5rem; border-radius: 12px;'>");
        out.println("               <span style='font-weight: 700; color: #059669;'>&#128994; Users Online:</span> " + onlineCount);
        out.println("           </div>");
        out.println("       </div>");

        out.println("       <div style='display: flex; justify-content: center; gap: 2rem;'>");
        out.println("           <a href='/hospital-webapp/home' style='color: #64748b; text-decoration: none;'>Home</a>");
        out.println("           <a href='/hospital-webapp/services' style='color: #64748b; text-decoration: none;'>Services</a>");
        out.println("           <a href='/hospital-webapp/appointment' style='color: #0284c7; font-weight: 700; text-decoration: none;'>Bookings</a>");
        out.println("       </div>");
        out.println("    </div>");
        out.println("  </footer>");
        out.println("</body>");
        out.println("</html>");
    }
}
