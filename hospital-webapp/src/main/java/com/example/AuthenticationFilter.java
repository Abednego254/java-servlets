package com.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * AuthenticationFilter — A Jakarta Servlet Filter.
 *
 * This filter mirrors the trainer's LoginFilter pattern from the cohort12 repo.
 * It intercepts EVERY request to the application and decides whether to:
 *   (a) PASS IT THROUGH   → user is logged in, or the URL is public
 *   (b) REDIRECT TO LOGIN → user is not authenticated and tried to reach a protected route
 *
 * This replaces the manual session checks that were previously in
 * RegisterServlet.doGet(), RegisterServlet.doPost(), and StaffListServlet.doGet().
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        // Cast to HTTP-specific types to access URI, session, etc.
        HttpServletRequest  httpRequest  = (HttpServletRequest)  servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String contextPath   = httpRequest.getContextPath();   // e.g. "/hospital-webapp"
        String requestUri    = httpRequest.getRequestURI();    // e.g. "/hospital-webapp/staff"

        // ── DEFINE PUBLIC (UNPROTECTED) PATHS ─────────────────────────────────
        // Any request matching these patterns is allowed through WITHOUT a session.
        boolean isPublicPath =
                requestUri.equals(contextPath + "/home")         ||
                requestUri.equals(contextPath + "/")             ||
                requestUri.startsWith(contextPath + "/services") ||
                requestUri.startsWith(contextPath + "/appointment") ||
                requestUri.startsWith(contextPath + "/confirmation") ||
                requestUri.startsWith(contextPath + "/notice")   ||
                requestUri.startsWith(contextPath + "/footer")   ||
                requestUri.startsWith(contextPath + "/login")    ||
                requestUri.startsWith(contextPath + "/logout");

        // ── CHECK SESSION ──────────────────────────────────────────────────────
        // getSession(false) → returns null if no session exists (does NOT create one)
        HttpSession session  = httpRequest.getSession(false);
        boolean isLoggedIn   = (session != null && session.getAttribute("user") != null);

        System.out.println("==> AuthenticationFilter: uri=" + requestUri
                + " | loggedIn=" + isLoggedIn + " | public=" + isPublicPath);

        if (isLoggedIn || isPublicPath) {
            // ✅ PASS THROUGH: continue the filter chain → next filter or servlet
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // ❌ BLOCK: destroy any stale session and redirect to login
            if (session != null) {
                session.invalidate();
            }
            httpResponse.sendRedirect(contextPath + "/login?error=auth_required");
        }
    }

    // init() and destroy() have default implementations in the Filter interface
    // (Jakarta EE 5+), so we don't need to override them unless we need custom logic.
}
