package com.example.action;

import com.example.framework.HospitalFramework;
import com.example.framework.HtmlTemplate;
import com.example.model.StaffMember;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
@WebServlet(name = "StaffEnrollmentServlet",
    urlPatterns = {"/staff_enroll"},
    initParams = {
        @WebInitParam(name = "pageName", value = "Staff Enrollment - SanityCare"),
        @WebInitParam(name = "pageHeader", value = "Join our Medical Staff")
    })
public class StaffEnrollmentServlet extends HospitalBaseAction<StaffMember> {
    // Zero logic in body!
}
