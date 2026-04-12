package com.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * AppLifecycleListener — implements ServletContextListener.
 *
 * This listener fires ONCE at two key moments in the entire application's life:
 *   1. contextInitialized() → when WildFly/Tomcat starts the app
 *   2. contextDestroyed()   → when WildFly/Tomcat shuts the app down
 *
 * Use cases: pre-loading data, initialising shared state, logging startup time,
 * cleaning up resources on shutdown.
 */
public class AppLifecycleListener implements ServletContextListener {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Called ONCE when the web application is deployed and started.
     * This runs BEFORE any servlet or filter is initialised.
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String startTime = LocalDateTime.now().format(FORMATTER);

        // ── Pre-seed shared state in ServletContext ────────────────────────────
        // By initialising here, HomeServlet no longer needs a null-check.
        context.setAttribute("totalVisitors", 0);
        context.setAttribute("activeSessions", 0);
        context.setAttribute("appStartTime", startTime);

        // ── Log startup info ───────────────────────────────────────────────────
        String appName  = context.getServletContextName(); // from web.xml <display-name>
        String realPath = context.getRealPath("/");

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║  🏥 SanityCare Hospital Web App — STARTING UP        ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  App Name   : " + appName);
        System.out.println("║  Start Time : " + startTime);
        System.out.println("║  Deploy Path: " + realPath);
        System.out.println("║  Global Key : " + context.getInitParameter("globalAdminKey"));
        System.out.println("║  Hospital   : " + context.getInitParameter("hospitalCity"));
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    /**
     * Called ONCE when the web application is undeployed or the server shuts down.
     * This runs AFTER all servlets and filters have been destroyed.
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        Integer totalVisitors = (Integer) context.getAttribute("totalVisitors");
        String startTime      = (String) context.getAttribute("appStartTime");

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║  🏥 SanityCare Hospital Web App — SHUTTING DOWN      ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Started At        : " + startTime);
        System.out.println("║  Shut Down At      : " + LocalDateTime.now().format(FORMATTER));
        System.out.println("║  Total Visitors    : " + totalVisitors);
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}
