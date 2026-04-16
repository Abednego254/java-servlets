package com.example.action;

import com.example.framework.HospitalFramework;
import com.example.framework.HtmlTemplate;
import com.example.model.Doctor;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
@WebServlet(name = "DoctorEnrollment",
    urlPatterns = {"/reg_doctor"},
    initParams = {
        @WebInitParam(name = "pageName", value = "Doctor Enrollment - SanityCare"),
        @WebInitParam(name = "pageHeader", value = "Enroll as a Doctor")
    })
public class DoctorEnrollment extends HospitalBaseAction<Doctor> {
    // Zero logic in body!
}
