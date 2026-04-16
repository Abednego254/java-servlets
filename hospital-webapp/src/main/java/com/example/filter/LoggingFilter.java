package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LoggingFilter — Demonstrates Filter Chaining.
 *
 * This filter runs BEFORE the AuthenticationFilter (based on ordering in web.xml).
 * It logs every incoming request — method, URI, and timestamp — to the server console.
 *
 * Concept: Multiple filters form a "FilterChain". Each filter calls
 * filterChain.doFilter() to pass control to the next filter in the chain.
 * If a filter does NOT call filterChain.doFilter(), the request is STOPPED there.
 *
 * Order of execution for a request:
 *   LoggingFilter → AuthenticationFilter → Target Servlet
 *                                       ← (response flows back through filters)
 *   LoggingFilter ← AuthenticationFilter ← Target Servlet
 */
@WebFilter("/*")
public class LoggingFilter implements Filter {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String method    = httpRequest.getMethod();
        String uri       = httpRequest.getRequestURI();
        String client    = servletRequest.getRemoteAddr();

        // ── PRE-PROCESSING: runs BEFORE the request reaches the servlet ─────────
        System.out.println("[" + timestamp + "] [REQUEST ] " + method + " " + uri
                + " from " + client);

        long startTime = System.currentTimeMillis();

        // ── PASS CONTROL to the next filter (AuthenticationFilter) or servlet ───
        filterChain.doFilter(servletRequest, servletResponse);

        // ── POST-PROCESSING: runs AFTER the servlet has completed ───────────────
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[" + timestamp + "] [RESPONSE] " + method + " " + uri
                + " completed in " + duration + "ms");
    }
}
