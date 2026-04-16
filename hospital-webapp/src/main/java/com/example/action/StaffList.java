package com.example.action;

import com.example.framework.HospitalFramework;
import com.example.framework.HtmlTemplate;
import com.example.model.StaffMember;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/staff_lists")
public class StaffList extends HospitalBaseAction<StaffMember> {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        HtmlTemplate.renderHeader(req, writer, "Staff Directory", "services");

        HospitalFramework.renderTable(writer, getType(), returnData(session));

        try {
            HtmlTemplate.includeFooter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
