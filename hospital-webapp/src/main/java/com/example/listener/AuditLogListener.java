package com.example.listener;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpSessionBindingEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebListener
public class AuditLogListener implements HttpSessionAttributeListener {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Fires when ANY attribute is added to ANY session.
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            String username  = (String) event.getValue();
            String sessionId = event.getSession().getId().substring(0, 8) + "...";
            String time      = LocalDateTime.now().format(FORMATTER);

            System.out.println("╔══════════════════════════════════════════════════════╗");
            System.out.println("║  [AUDIT] LOGIN EVENT                                 ║");
            System.out.println("║  Time      : " + time);
            System.out.println("║  User      : " + username);
            System.out.println("║  Session   : " + sessionId);
            System.out.println("╚══════════════════════════════════════════════════════╝");
        }
    }

    /**
     * Fires when ANY attribute is removed from ANY session.
     * This includes: session.removeAttribute(), session.invalidate(), and session timeout.
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            String username  = (String) event.getValue();
            String sessionId = event.getSession().getId().substring(0, 8) + "...";
            String time      = LocalDateTime.now().format(FORMATTER);

            System.out.println("╔══════════════════════════════════════════════════════╗");
            System.out.println("║  [AUDIT] LOGOUT / SESSION EXPIRED EVENT              ║");
            System.out.println("║  Time      : " + time);
            System.out.println("║  User      : " + username);
            System.out.println("║  Session   : " + sessionId);
            System.out.println("╚══════════════════════════════════════════════════════╝");
        }
    }

    /**
     * Fires when an attribute is REPLACED on a session.
     * e.g. session.setAttribute("user", "newValue") when "user" already exists.
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            System.out.println("==> AuditLogListener: [AUDIT] 'user' attribute was REPLACED in session "
                    + event.getSession().getId().substring(0, 8) + "...");
        }
    }
}
