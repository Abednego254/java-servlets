package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String contextPath   = httpRequest.getContextPath();
        String requestUri    = httpRequest.getRequestURI();

        // ── DEFINE PUBLIC (UNPROTECTED) PATHS ──
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

        HttpSession session  = httpRequest.getSession(false);
        boolean isLoggedIn   = (session != null && session.getAttribute("user") != null);

        System.out.println("==> AuthenticationFilter: uri=" + requestUri
                + " | loggedIn=" + isLoggedIn + " | public=" + isPublicPath);

        if (isLoggedIn || isPublicPath) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (session != null) {
                session.invalidate();
            }
            httpResponse.sendRedirect(contextPath + "/login?error=auth_required");
        }
    }
}
