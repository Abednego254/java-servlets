package com.example.action;

import com.example.framework.HospitalFramework;
import com.example.framework.HospitalTable;
import com.example.framework.HtmlTemplate;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class HospitalBaseAction<T> extends HttpServlet {

    @SuppressWarnings("unchecked")
    public T serializeForm(Map<String, String[]> requestMap) {
        try {
            T clazzInstance = this.getType().getDeclaredConstructor().newInstance();

            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
                @Override
                public Object convert(String value, Class clazz) {
                    if (clazz.isEnum()) {
                        return Enum.valueOf(clazz, value);
                    } else if (clazz == Date.class) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            return dateFormat.parse(value);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        return super.convert(value, clazz);
                    }
                }
            });

            beanUtilsBean.populate(clazzInstance, requestMap);
            return clazzInstance;

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e ) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<T> register;
        if (session.getAttribute(this.dbName()) == null)
            register = new ArrayList<>();
        else
            register = (List<T>) session.getAttribute(this.dbName());

        try {
            register.add(this.serializeForm(req.getParameterMap()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        session.setAttribute(this.dbName(), register);

        if (this.getType().isAnnotationPresent(HospitalTable.class)) {
            resp.sendRedirect(this.getType().getAnnotation(HospitalTable.class).tableUrl());
        } else {
            resp.sendRedirect("./home");
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ServletConfig config = getServletConfig();
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        String pageTitle = config.getInitParameter("pageName") != null ? config.getInitParameter("pageName") : this.getType().getSimpleName() + " Registration";
        String pageHeader = config.getInitParameter("pageHeader") != null ? config.getInitParameter("pageHeader") : this.getType().getSimpleName() + " Registration";

        HtmlTemplate.renderHeader(req, writer, pageTitle, "register");

        writer.println("<div class='page-wrap'>");
        writer.println("  <div class='form-card'>");
        writer.println("    <h1>" + pageHeader + "</h1>");
        writer.println("    <p class='subtitle'>Generated via Generic Reflection Engine.</p>");
        writer.println("    <hr class='divider'>");

        HospitalFramework.renderForm(writer, this.getType());

        writer.println("  </div>");
        writer.println("</div>");

        try {
            HtmlTemplate.includeFooter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Class<T> getType() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superClass.getActualTypeArguments()[0];
    }

    public String dbName(){
        return this.getType().getSimpleName().toUpperCase() + "_DB";
    }

    public List<T> returnData(HttpSession session){
        List<T> register;
        if (session.getAttribute(this.dbName()) == null)
            register = new ArrayList<>();
        else
            register = (List<T>) session.getAttribute(this.dbName());
        return register;
    }
}
