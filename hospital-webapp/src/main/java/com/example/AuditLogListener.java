package com.example;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * AuditLogListener — implements HttpSessionAttributeListener.
 *
 * Fires whenever an attribute is ADDED, REMOVED, or REPLACED on any HttpSession.
 * We use this to create a server-side audit trail of login and logout events.
 *
 * Specifically, it watches for changes to the "user" attribute that our
 * LoginServlet and LogoutServlet manage:
 *   - "user" ADDED   → someone just logged in
 *   - "user" REMOVED → someone just logged out or their session expired
 */
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
