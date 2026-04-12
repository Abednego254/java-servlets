# Servlet Filters — SanityCare Hospital Web App

**Trainer Reference:** `https://github.com/mikebavon/cohort12` → `LoginFilter.java`
**Project:** `hospital-webapp`

---

## What is a Servlet Filter?

A **Filter** is a reusable component that intercepts HTTP requests **before** they reach a servlet and HTTP responses **after** the servlet has processed them. Think of it as a security checkpoint or middleware layer that sits between the browser and your servlets.

```
Browser Request
      │
      ▼
 ┌──────────────────────┐
 │   LoggingFilter      │  ← Filter 1: Logs the request
 └──────────┬───────────┘
            │ filterChain.doFilter()
            ▼
 ┌──────────────────────┐
 │ AuthenticationFilter │  ← Filter 2: Checks session / redirects
 └──────────┬───────────┘
            │ filterChain.doFilter()
            ▼
 ┌──────────────────────┐
 │   Target Servlet     │  ← e.g. StaffListServlet
 └──────────┬───────────┘
            │ Response
            ▼
 ┌──────────────────────┐
 │ AuthenticationFilter │  ← Post-processing (response flows back)
 └──────────┬───────────┘
            │
            ▼
 ┌──────────────────────┐
 │   LoggingFilter      │  ← Logs the response + duration
 └──────────────────────┘
            │
            ▼
       Browser Response
```

---

## The Filter Interface

A filter must implement the `jakarta.servlet.Filter` interface. There is only **one method** you are required to override:

```java
public interface Filter {
    // Called once when the filter is loaded
    default void init(FilterConfig filterConfig) throws ServletException {}

    // Called on EVERY request matching the filter's url-pattern
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    // Called once when the application is shutting down
    default void destroy() {}
}
```

> `init()` and `destroy()` have **default implementations** in Jakarta EE 5+, so you only need to implement `doFilter()`.

---

## Filters Implemented

### 1. `LoggingFilter.java` — Request/Response Logging

**Purpose:** Logs every request (method, URI, client IP, timestamp and duration) to the server console. Demonstrates **pre-processing** and **post-processing** around `filterChain.doFilter()`.

```java
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String method    = httpRequest.getMethod();
        String uri       = httpRequest.getRequestURI();

        // PRE-PROCESSING: runs BEFORE the request reaches the servlet
        System.out.println("[" + timestamp + "] [REQUEST ] " + method + " " + uri);

        long startTime = System.currentTimeMillis();

        // Hand off to the NEXT filter in the chain (AuthenticationFilter)
        filterChain.doFilter(servletRequest, servletResponse);

        // POST-PROCESSING: runs AFTER the servlet has responded
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[" + timestamp + "] [RESPONSE] " + method + " " + uri
                + " completed in " + duration + "ms");
    }
}
```

**Sample Server Output:**
```
[2026-04-12 07:30:15] [REQUEST ] GET /hospital-webapp/staff from 127.0.0.1
[2026-04-12 07:30:15] [RESPONSE] GET /hospital-webapp/staff completed in 12ms
```

---

### 2. `AuthenticationFilter.java` — Centralized Session Guard

**Purpose:** Replaces the manual `getSession(false)` checks that were scattered across `RegisterServlet` and `StaffListServlet`. Now a **single filter** enforces the login rule for every protected route.

This directly mirrors the trainer's `LoginFilter` pattern from the cohort12 repository.

```java
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String contextPath = httpRequest.getContextPath(); // "/hospital-webapp"
        String requestUri  = httpRequest.getRequestURI();  // e.g. "/hospital-webapp/staff"

        // Define which paths are publicly accessible (no login required)
        boolean isPublicPath =
                requestUri.equals(contextPath + "/home")              ||
                requestUri.equals(contextPath + "/")                  ||
                requestUri.startsWith(contextPath + "/services")      ||
                requestUri.startsWith(contextPath + "/appointment")   ||
                requestUri.startsWith(contextPath + "/confirmation")  ||
                requestUri.startsWith(contextPath + "/notice")        ||
                requestUri.startsWith(contextPath + "/footer")        ||
                requestUri.startsWith(contextPath + "/login")         ||
                requestUri.startsWith(contextPath + "/logout");

        // Check: is the user logged in?
        HttpSession session = httpRequest.getSession(false); // false = do NOT create
        boolean isLoggedIn  = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn || isPublicPath) {
            // ✅ Allow: pass request through to the next filter or servlet
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // ❌ Block: invalidate stale session, redirect to login
            if (session != null) session.invalidate();
            httpResponse.sendRedirect(contextPath + "/login?error=auth_required");
        }
    }
}
```

#### Key Design Decisions
| Decision | Reason |
|---|---|
| `getSession(false)` | Prevents accidentally creating a new session during a guard check |
| Check `isPublicPath` first | Avoids null-pointer issues; allows unauthenticated access to public pages |
| `filterChain.doFilter()` | **Must** be called to pass the request forward; omitting it stops the request |
| `session.invalidate()` before redirect | Cleans up any corrupted/expired session before bouncing the user |

---

## Registering Filters in `web.xml`

Filters must be declared **before** servlet definitions in `web.xml`. The order of `<filter-mapping>` elements determines the chain execution order.

```xml
<!-- FILTER 1: Runs first — logs all requests -->
<filter>
    <filter-name>LoggingFilter</filter-name>
    <filter-class>com.example.LoggingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>LoggingFilter</filter-name>
    <url-pattern>/*</url-pattern>  <!-- Intercepts ALL requests -->
</filter-mapping>

<!-- FILTER 2: Runs second — enforces authentication -->
<filter>
    <filter-name>AuthenticationFilter</filter-name>
    <filter-class>com.example.AuthenticationFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>AuthenticationFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

> **`url-pattern=/*`** means the filter intercepts every single request to the application, including requests for servlets, static resources, etc.

---

## Before vs After: The Refactoring Benefit

### Before (manual guards in every servlet):
```java
// RegisterServlet.java
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false); // Duplicated in 2+ servlets
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("/hospital-webapp/login?error=auth_required");
        return;
    }
    // ... render page
}

// StaffListServlet.java
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false); // Same check, again
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("/hospital-webapp/login?error=auth_required");
        return;
    }
    // ... render page
}
```

### After (one filter handles everything):
```java
// RegisterServlet.java
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    // NOTE: Session protection is now handled centrally by AuthenticationFilter.
    // No manual session check needed here.
    // ... render page
}

// StaffListServlet.java
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    // NOTE: Session protection is now handled centrally by AuthenticationFilter.
    // ... render page
}
```

This follows the **Don't Repeat Yourself (DRY)** principle — security logic lives in exactly **one place**.

---

## Filter vs Servlet — Key Differences

| Feature | Servlet | Filter |
|---|---|---|
| **Purpose** | Handle a specific request, generate a response | Intercept and inspect/modify requests & responses |
| **Interface** | `HttpServlet` | `Filter` |
| **Key method** | `doGet()`, `doPost()` | `doFilter()` |
| **URL mapping** | Specific path (`/staff`) | Broad patterns (`/*`, `/admin/*`) |
| **Chain** | No chaining | Multiple filters chain via `FilterChain` |
| **Typical use** | Business logic, HTML rendering | Auth, logging, caching, compression |

---

## Summary of Changes Made

| File | Action |
|---|---|
| `AuthenticationFilter.java` | **NEW** — Central session-based route guard |
| `LoggingFilter.java` | **NEW** — Request/response logging with timing |
| `web.xml` | **MODIFIED** — Added `<filter>` + `<filter-mapping>` for both filters |
| `RegisterServlet.java` | **MODIFIED** — Removed manual session guard (now handled by filter) |
| `StaffListServlet.java` | **MODIFIED** — Removed manual session guard (now handled by filter) |
