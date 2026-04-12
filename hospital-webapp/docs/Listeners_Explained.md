# Servlet Listeners — SanityCare Hospital Web App

**Project:** `hospital-webapp`

---

## What is a Servlet Listener?

A **Listener** is a passive observer that reacts to lifecycle events. You do not call it — the **servlet container calls it automatically** when something significant happens. There are no URL patterns; you simply register the class and the container does the rest.

There are three scopes with their own listener interfaces:

| Scope | Lifecycle Interface | Attribute Interface |
|---|---|---|
| **Application** | `ServletContextListener` | `ServletContextAttributeListener` |
| **Session** | `HttpSessionListener` | `HttpSessionAttributeListener` |
| **Request** | `ServletRequestListener` | `ServletRequestAttributeListener` |

---

## Listeners Implemented

### 1. `AppLifecycleListener` — `ServletContextListener`

**Fires on:** Application startup (`contextInitialized`) and shutdown (`contextDestroyed`).

```java
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        // Pre-seed shared counters — runs BEFORE any servlet or filter initialises
        context.setAttribute("totalVisitors", 0);
        context.setAttribute("activeSessions", 0);
        context.setAttribute("appStartTime", LocalDateTime.now().format(FORMATTER));

        // Log startup banner to console
        System.out.println("🏥 SanityCare Hospital Web App — STARTING UP");
        System.out.println("Hospital: " + context.getInitParameter("hospitalCity"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Log stats on shutdown
        Integer totalVisitors = (Integer) event.getServletContext().getAttribute("totalVisitors");
        System.out.println("🏥 SanityCare Hospital Web App — SHUTTING DOWN");
        System.out.println("Total Visitors this session: " + totalVisitors);
    }
}
```

**Key benefit:** `contextInitialized()` runs **before** any servlet's `init()`. This means shared attributes like `totalVisitors` are guaranteed to exist before `HomeServlet` tries to read them — eliminating the need for null-checks.

**When it fires:**
```
Server starts → contextInitialized() → Filters init → Servlets init → Requests handled
Server stops  → Servlets destroyed  → Filters destroyed → contextDestroyed()
```

---

### 2. `ActiveSessionListener` — `HttpSessionListener`

**Fires on:** Every `HttpSession` creation and destruction anywhere in the application.

```java
public class ActiveSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();

        synchronized (context) {
            Integer active = (Integer) context.getAttribute("activeSessions");
            context.setAttribute("activeSessions", active + 1); // ↑ increment
        }

        System.out.println("==> Session CREATED | Active: "
                + context.getAttribute("activeSessions"));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();

        synchronized (context) {
            Integer active = (Integer) context.getAttribute("activeSessions");
            context.setAttribute("activeSessions", Math.max(0, active - 1)); // ↓ decrement
        }

        System.out.println("==> Session DESTROYED | Active: "
                + context.getAttribute("activeSessions"));
    }
}
```

**When `sessionCreated` fires:**
- First time any request calls `request.getSession()` or `request.getSession(true)`

**When `sessionDestroyed` fires:**
- `session.invalidate()` is called (logout in `LogoutServlet`)
- Session idle timeout (configured in `web.xml` via `<session-config>`)

**Live effect:** The footer now displays a 🟢 **Users Online** badge that reflects the real-time count.

> **Why `synchronized`?** Multiple users could create/destroy sessions at the exact same millisecond. Without synchronization, two threads could read `active = 3` simultaneously and both write `4` — losing a count.

---

### 3. `AuditLogListener` — `HttpSessionAttributeListener`

**Fires on:** Every time an attribute is added, removed, or replaced on **any** active session.

```java
public class AuditLogListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        // Watch specifically for the "user" attribute — set in LoginServlet
        if ("user".equals(event.getName())) {
            System.out.println("[AUDIT] LOGIN  — User: " + event.getValue()
                    + " | Session: " + event.getSession().getId());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        // Watch for "user" removal — caused by session.invalidate() in LogoutServlet
        if ("user".equals(event.getName())) {
            System.out.println("[AUDIT] LOGOUT — User: " + event.getValue()
                    + " | Session: " + event.getSession().getId());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            System.out.println("[AUDIT] REPLACED — 'user' attribute updated in session");
        }
    }
}
```

**What triggers each method:**
| Method | What causes it |
|---|---|
| `attributeAdded` | `session.setAttribute("user", "Administrator")` in `LoginServlet` |
| `attributeRemoved` | `session.invalidate()` in `LogoutServlet` (removes all attributes) |
| `attributeReplaced` | `session.setAttribute("user", newValue)` when `"user"` already exists |

---

## Registering Listeners in `web.xml`

Listeners require **no URL mapping**. Just declare the class — the container handles the rest.

```xml
<!-- Listener 1: Application lifecycle -->
<listener>
    <listener-class>com.example.AppLifecycleListener</listener-class>
</listener>

<!-- Listener 2: Session creation/destruction -->
<listener>
    <listener-class>com.example.ActiveSessionListener</listener-class>
</listener>

<!-- Listener 3: Session attribute changes -->
<listener>
    <listener-class>com.example.AuditLogListener</listener-class>
</listener>
```

> **Order matters:** Multiple `ServletContextListener`s fire in their declaration order. Multiple `HttpSessionListener`s also fire in order.

---

## Full Architecture — Listeners, Filters & Servlets Together

```
APP STARTUP
    │
    └─► AppLifecycleListener.contextInitialized()
            Sets: totalVisitors=0, activeSessions=0, appStartTime

BROWSER REQUEST (e.g. POST /login)
    │
    ├─► LoggingFilter.doFilter() [pre]       ← Filter
    ├─► AuthenticationFilter.doFilter()      ← Filter
    ├─► LoginServlet.doPost()
    │       session.setAttribute("user", "Administrator")
    │               │
    │               └─► AuditLogListener.attributeAdded()  ← Listener fires here
    │
    ├─► AuthenticationFilter.doFilter() [post]
    └─► LoggingFilter.doFilter() [post]

SESSION CREATED (first request.getSession() call)
    │
    └─► ActiveSessionListener.sessionCreated()  ← Listener fires, activeSessions++

SESSION DESTROYED (session.invalidate() at /logout)
    │
    ├─► AuditLogListener.attributeRemoved()   ← "user" removed → audit log
    └─► ActiveSessionListener.sessionDestroyed() ← activeSessions--

APP SHUTDOWN
    │
    └─► AppLifecycleListener.contextDestroyed()
            Logs: total visitors, shutdown time
```

---

## Listener vs Filter vs Servlet

| | Servlet | Filter | Listener |
|---|---|---|---|
| **Triggered by** | HTTP request to its URL | Every request (url-pattern) | Container lifecycle events |
| **URL Pattern** | Required | Required | **None** |
| **Purpose** | Handle request, build response | Inspect/modify requests & responses | React to lifecycle events |
| **Call style** | Browser calls it | Container chains it | **Container calls it automatically** |
| **Our examples** | `HomeServlet`, `LoginServlet` | `AuthenticationFilter`, `LoggingFilter` | `AppLifecycleListener`, `ActiveSessionListener`, `AuditLogListener` |
