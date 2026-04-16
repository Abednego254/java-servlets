package com.example.framework;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HospitalFramework {

    public static void renderForm(PrintWriter out, Class<?> clazz) {
        if (!clazz.isAnnotationPresent(HospitalForm.class)) return;

        HospitalForm formAnnot = clazz.getAnnotation(HospitalForm.class);

        out.println("  <div class='page-wrap'>");
        out.println("    <div class='form-card'>");
        out.println("      <h1>" + formAnnot.label() + "</h1>");
        out.println("      <p class='subtitle'>Generated via Generic Reflection Engine.</p>");
        out.println("      <hr class='divider'>");

        out.println("      <form action='" + formAnnot.actionUrl() + "' method='" + formAnnot.method() + "'>");

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(HospitalFormField.class)) continue;

            HospitalFormField fieldInfo = field.getAnnotation(HospitalFormField.class);
            String fieldName = fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name();

            out.println("        <div class='form-group'>");
            out.println("          <label for='" + fieldName + "'>" + fieldInfo.label() + "</label>");
            out.println("          <input type='" + fieldInfo.type() + "' id='" + fieldName + "' name='" + fieldName + "' " +
                    "placeholder='" + fieldInfo.placeholder() + "' " + (fieldInfo.required() ? "required" : "") + ">");
            out.println("        </div>");
        }

        out.println("        <button type='submit' class='btn-submit'>" + formAnnot.label() + " &rarr;</button>");
        out.println("      </form>");
        out.println("    </div>");
        out.println("  </div>");
    }

    public static void renderTable(PrintWriter out, Class<?> clazz, List<?> dataList) {
        if (!clazz.isAnnotationPresent(HospitalTable.class)) return;

        HospitalTable tableAnnot = clazz.getAnnotation(HospitalTable.class);

        out.println("  <div class='page-header'>");
        out.println("    <div class='section-label'>Management Directory</div>");
        out.println("    <h1>" + tableAnnot.label() + "</h1>");
        out.println("    <p>Automated listing generated from @HospitalTable metadata.</p>");
        out.println("  </div>");

        out.println("  <div class='services-section'>");
        out.println("    <div style='background: white; border-radius: 24px; padding: 2rem; border: 1px solid #e2e8f0; box-shadow: 0 4px 20px rgba(0,0,0,0.02); overflow: hidden;'>");
        out.println("      <table style='width: 100%; border-collapse: separate; border-spacing: 0;'>");
        out.println("        <thead>");
        out.println("          <tr style='background: #f8fafc;'>");

        List<Field> annotatedFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(HospitalTableCol.class)) {
                annotatedFields.add(field);
                HospitalTableCol col = field.getAnnotation(HospitalTableCol.class);
                out.println("            <th style='text-align: left; padding: 1.2rem; color: #64748b; font-weight: 700; border-bottom: 2px solid #e2e8f0;'>" + col.label().toUpperCase() + "</th>");
            }
        }
        out.println("            <th style='text-align: left; padding: 1.2rem; color: #64748b; font-weight: 700; border-bottom: 2px solid #e2e8f0;'>STATUS</th>");
        out.println("          </tr>");
        out.println("        </thead>");
        out.println("        <tbody>");

        for (Object data : dataList) {
            out.println("          <tr>");
            for (Field field : annotatedFields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(data);
                    out.println("            <td style='padding: 1.2rem; border-bottom: 1px solid #f1f5f9; font-weight: 600; color: #0f172a;'>" + (value != null ? value.toString() : "") + "</td>");
                } catch (IllegalAccessException e) {
                    out.println("            <td style='padding: 1.2rem; border-bottom: 1px solid #f1f5f9; color: #ef4444;'>Error</td>");
                }
            }
            out.println("            <td style='padding: 1.2rem; border-bottom: 1px solid #f1f5f9;'><span style='background: #ecfdf5; color: #059669; padding: 0.3rem 0.8rem; border-radius: 20px; font-size: 0.75rem; font-weight: 700; text-transform: uppercase;'>Active</span></td>");
            out.println("          </tr>");
        }

        if (dataList.isEmpty()) {
            out.println("          <tr><td colspan='" + (annotatedFields.size() + 1) + "' style='text-align: center; padding: 3rem; color: #94a3b8;'>No records found.</td></tr>");
        }

        out.println("        </tbody>");
        out.println("      </table>");

        // Back link
        String addUrl = (tableAnnot.registerUrl() != null && !tableAnnot.registerUrl().isEmpty()) ? tableAnnot.registerUrl() : 
                        (clazz.isAnnotationPresent(HospitalForm.class) ? clazz.getAnnotation(HospitalForm.class).actionUrl() : "#");
        
        out.println("      <div style='margin-top: 2rem; text-align: right;'>");
        out.println("        <a href='" + addUrl + "' class='btn-primary' style='padding: 0.8rem 1.5rem; font-size: 0.9rem;'>+ Add " + tableAnnot.label() + "</a>");
        out.println("      </div>");

        out.println("    </div>");
        out.println("  </div>");
    }
}
