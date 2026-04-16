package com.example.action;

import com.example.framework.HtmlTemplate;
import jakarta.servlet.GenericServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/services")
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

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HtmlTemplate.renderHeader(httpReq, out, "Our Services | SanityCare Hospital", "services");

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
        out.println("    <a class='btn-primary' href='./appointment'>Book an Appointment &rarr;</a>");
        out.println("  </div>");

        try {
            HttpServletResponse httpRes = (HttpServletResponse) response;
            HtmlTemplate.includeFooter(httpReq, httpRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
