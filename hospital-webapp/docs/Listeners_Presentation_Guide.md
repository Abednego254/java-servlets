# Presentation Guide: Servlet Listeners
### SanityCare Hospital Web App — Abednego Kaume

---

## Slide 1 — Opening: What Problem Do Listeners Solve?

> **Talking points:**

HTTP is a stateless, request/response protocol. Servlets only run when a browser makes a request. But some important things happen **outside** of requests:

- The application starts and needs to load configuration
- A user's session expires in the background — no one sent a request
- An attribute changes on a session — how do you know about it?

**Listeners** solve this. They are event-driven components that the **servlet container calls automatically** when these lifecycle moments happen — no browser request required.

---

## Slide 2 — The Three Listener Scopes

There are three "levels" at which you can listen for events:

```
┌─────────────────────────────────────────────────────────────┐
│  APPLICATION (ServletContext)                                │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  SESSION (HttpSession)                               │   │
│  │  ┌────────────────────────────────────────────────┐  │   │
│  │  │  REQUEST (HttpServletRequest)                  │  │   │
│  │  └────────────────────────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

| Scope | Lifecycle Events | Attribute Events |
|---|---|---|
| **Application** | `ServletContextListener` | `ServletContextAttributeListener` |
| **Session** | `HttpSessionListener` | `HttpSessionAttributeListener` |
| **Request** | `ServletRequestListener` | `ServletRequestAttributeListener` |

> **Key point:** A Listener has **no URL pattern**. You don't request it. The container calls it.

---

## Slide 3 — How to Add a Listener to Your Project (Step-by-Step)

> **Use a new example: `RequestCounterListener`**
> *This listener counts how many HTTP requests the application has received in total — across all users.*

---

### Step 1: Identify the right interface

You need to react when a **request arrives and leaves**. That is the `ServletRequestListener` interface.

```java
// The two methods you must implement:
public interface ServletRequestListener {
    void requestInitialized(ServletRequestEvent event); // request arrives
    void requestDestroyed(ServletRequestEvent event);   // request leaves
}
```

---

### Step 2: Create the class and implement the interface

```java
package com.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

/**
 * RequestCounterListener — implements ServletRequestListener
 *
 * Fires on EVERY incoming HTTP request and increments a global
 * request counter stored in the ServletContext.
 *
 * This is a great example of a Listener because it captures events
 * that span ALL servlets without modifying any of them.
 */
public class RequestCounterListener implements ServletRequestListener {

    /**
     * Fires the moment a request is RECEIVED by the server.
     */
    @Override
    public void requestInitialized(ServletRequestEvent event) {
        ServletContext context = event.getServletContext();

        // Thread-safe increment
        synchronized (context) {
            Integer totalRequests = (Integer) context.getAttribute("totalRequests");
            if (totalRequests == null) totalRequests = 0;
            context.setAttribute("totalRequests", totalRequests + 1);
        }
    }

    /**
     * Fires when the server has finished processing the request.
     * You can use this for cleanup, timing, logging, etc.
     */
    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        // Optional: log completion, measure duration, free resources
    }
}
```

> **Point to make:** Notice we did not touch `HomeServlet`, `BookAppointmentServlet`, or any other servlet. The counter increments for **every request in the entire app**, automatically.

---

### Step 3: Register it in `web.xml`

```xml
<!-- In WEB-INF/web.xml, no url-pattern needed — just declare the class -->
<listener>
    <listener-class>com.example.RequestCounterListener</listener-class>
</listener>
```

That's it. Three steps:
1. ✅ Pick the right interface
2. ✅ Implement the class
3. ✅ Register in `web.xml`

> **No `doGet()`. No URL. No manual call. The container handles everything.**

---

## Slide 4 — Three Listeners Implemented in This Project

---

### Listener 1: `AppLifecycleListener`
**Interface:** `ServletContextListener`

```java
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Fires ONCE when the application starts
        // Pre-seeds shared counters before any servlet runs
        event.getServletContext().setAttribute("totalVisitors", 0);
        event.getServletContext().setAttribute("activeSessions", 0);
        System.out.println("🏥 SanityCare Hospital — STARTING UP");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Fires ONCE when the server shuts down
        Integer total = (Integer) event.getServletContext().getAttribute("totalVisitors");
        System.out.println("🏥 SanityCare Hospital — SHUTTING DOWN | Visitors: " + total);
    }
}
```

**What it does in the project:**
- Guaranteed to run before any servlet's `init()` method
- Removes null-checks from `HomeServlet`'s visitor counter — `totalVisitors` is pre-seeded to `0`
- Prints a startup banner with the hospital name, city, and admin key to the console

---

### Listener 2: `ActiveSessionListener`
**Interface:** `HttpSessionListener`

```java
public class ActiveSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // Fires when any new HttpSession is created
        // (first call to request.getSession() for a browser)
        ServletContext ctx = event.getSession().getServletContext();
        synchronized (ctx) {
            int active = (Integer) ctx.getAttribute("activeSessions");
            ctx.setAttribute("activeSessions", active + 1);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // Fires when session.invalidate() is called (logout)
        // OR when the session times out
        ServletContext ctx = event.getSession().getServletContext();
        synchronized (ctx) {
            int active = (Integer) ctx.getAttribute("activeSessions");
            ctx.setAttribute("activeSessions", Math.max(0, active - 1));
        }
    }
}
```

**What it does in the project:**
- Maintains a live `activeSessions` count in the `ServletContext`
- The footer reads this count and displays **"🟢 Users Online: 2"**
- Decrements on logout AND on session timeout — even when no one explicitly clicked Logout

---

### Listener 3: `AuditLogListener`
**Interface:** `HttpSessionAttributeListener`

```java
public class AuditLogListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        // Fires when session.setAttribute() is called on any active session
        if ("user".equals(event.getName())) {
            System.out.println("[AUDIT] LOGIN  — User: " + event.getValue()
                    + " at " + LocalDateTime.now());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        // Fires when session.removeAttribute() OR session.invalidate() is called
        if ("user".equals(event.getName())) {
            System.out.println("[AUDIT] LOGOUT — User: " + event.getValue()
                    + " at " + LocalDateTime.now());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // Fires when session.setAttribute() overwrites an existing key
    }
}
```

**What it does in the project:**
- Creates a server-side audit trail — every login and logout is logged with a timestamp
- Works entirely from attribute events — `LoginServlet` and `LogoutServlet` don't need to write any audit code themselves
- `attributeRemoved` catches **both** explicit logout AND session timeout

---

## Slide 5 — How the Listeners Fit Into the Bigger Picture

```
SERVER STARTUP
    └─► AppLifecycleListener.contextInitialized()
            → Sets totalVisitors = 0
            → Sets activeSessions = 0
            → Logs startup banner

USER VISITS FIRST PAGE (browser has no session yet)
    └─► LoggingFilter  [pre-request]
    └─► AuthenticationFilter [passes — public path]
    └─► HomeServlet.service()
            → increments totalVisitors
    └─► [Session may be created for first-time visitors]
            └─► ActiveSessionListener.sessionCreated()
                → activeSessions = 1

USER LOGS IN (POST /login)
    └─► LoginServlet.doPost()
            → session.setAttribute("user", "Administrator")
                └─► AuditLogListener.attributeAdded()
                    → [AUDIT] LOGIN — User: Administrator

USER LOGS OUT (GET /logout)
    └─► LogoutServlet
            → session.invalidate()
                └─► AuditLogListener.attributeRemoved()
                    → [AUDIT] LOGOUT — User: Administrator
                └─► ActiveSessionListener.sessionDestroyed()
                    → activeSessions = 0

SERVER SHUTDOWN
    └─► AppLifecycleListener.contextDestroyed()
            → Logs total visitors and shutdown time
```

---

## Slide 6 — Listeners vs Filters vs Servlets: The Complete Comparison

| | Servlet | Filter | Listener |
|---|---|---|---|
| **What triggers it** | Browser request to its URL | Every request (url-pattern) | Container lifecycle events |
| **URL Pattern needed?** | ✅ Yes | ✅ Yes | ❌ No |
| **Key method** | `doGet()`, `doPost()` | `doFilter()` | `contextInitialized()`, `sessionCreated()`, etc. |
| **Runs when** | Request matches URL | Request passes through | App starts, session changes, etc. |
| **Can stop a request?** | Responds to it | Yes (`filterChain.doFilter()` not called) | ❌ No — purely observational |
| **Used for** | Business logic + HTML | Auth, logging, compression | Initialisation, auditing, counters |
| **In this project** | `HomeServlet`, `LoginServlet` | `AuthenticationFilter`, `LoggingFilter` | All three Listeners |

---

## Slide 7 — Key Takeaways

1. **Listeners are passive** — you never call them. The container calls them.
2. **Three scopes**: Application (`ServletContextListener`), Session (`HttpSessionListener`), Request (`ServletRequestListener`), and their attribute counterparts.
3. **Only 3 steps to add a listener**: pick interface → implement class → register in `web.xml`.
4. **No URL pattern** — they respond to events, not HTTP paths.
5. **Thread safety matters** — when modifying shared `ServletContext` attributes, always use `synchronized`.
6. **Listeners + Filters + Servlets** are complementary: Servlets handle logic, Filters gate requests, Listeners observe events.

---

## Demo Script (Live During Presentation)

1. **Start the server** → Watch console for the startup banner from `AppLifecycleListener`
2. **Open browser** → Visit `/home` a few times → Show visitor counter incrementing in footer
3. **Open incognito window** → Visit `/staff` → Show redirect to login (`AuthenticationFilter`)
4. **Login** → Watch console for `[AUDIT] LOGIN` from `AuditLogListener`; footer shows "🟢 Users Online: 1"
5. **Open second incognito window** and login → Footer shows "🟢 Users Online: 2"
6. **Logout** → Watch console for `[AUDIT] LOGOUT`; Users Online drops back to 1
7. **Deploy stop** → Watch console for shutdown banner from `AppLifecycleListener`
