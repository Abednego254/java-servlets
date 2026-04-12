# HttpSession & ServletContext in the SanityCare Hospital Web App

**Project:** `hospital-webapp`
**Tech Stack:** Jakarta EE 6.0 · Java Servlets · Maven

---

## Part 1: ServletContext — Application-Wide Configuration & State

### What is ServletContext?

`ServletContext` is an object shared by **every servlet in the entire web application**. Think of it as the application's "global blackboard." It is created when the server starts and destroyed when the server shuts down.

```
Server Starts
    │
    └─► ServletContext is created (ONE per application)
            │
            ├─► HomeServlet has access to it
            ├─► RegisterServlet has access to it
            ├─► FooterServlet has access to it
            └─► All other servlets share the same context
```

### How to Access It

From inside any servlet:
```java
// Via getServletContext() (preferred in HttpServlet)
ServletContext ctx = getServletContext();

// Via ServletConfig (used in the raw Servlet interface)
ServletContext ctx = getServletConfig().getServletContext();
```

---

### 1. Global Parameters (`<context-param>` in web.xml)

These are **read-only** values configured once in `web.xml` and accessible by every servlet, without recompiling Java code.

**`web.xml` configuration:**
```xml
<!-- Global Application Parameters (ServletContext) -->
<context-param>
    <param-name>globalAdminKey</param-name>
    <param-value>SanityAdmin2026</param-value>
</context-param>
<context-param>
    <param-name>hospitalCity</param-name>
    <param-value>Nairobi, Kenya</param-value>
</context-param>
<context-param>
    <param-name>hospitalEmail</param-name>
    <param-value>support@sanitycare.info</param-value>
</context-param>
```

**Java access (RegisterServlet.java):**
```java
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);
    // Read from the GLOBAL context, not the local servlet config
    this.secretKey = config.getServletContext().getInitParameter("globalAdminKey");
}
```

**Java access (BookAppointmentServlet.java):**
```java
// Validate the Admin Passcode against the global key
String globalKey = getServletContext().getInitParameter("globalAdminKey");
if (globalKey != null && !globalKey.equals(enteredPasscode)) {
    response.sendRedirect("/hospital-webapp/appointment?error=wrong_passcode");
    return;
}
```

> **Key Distinction:** `getServletConfig().getInitParameter()` reads `<init-param>` for **that specific servlet only**.
> `getServletContext().getInitParameter()` reads `<context-param>` shared by **the entire application**.

---

### 2. Global State — The Visitor Counter

`ServletContext` can also store live, mutable **attributes** (not just parameters). These persist across all users and all requests until the server restarts.

**HomeServlet.java:**
```java
// Thread-safe increment of the global visitor counter
synchronized(this.getServletConfig().getServletContext()) {
    Integer totalVisitors = (Integer) this.getServletConfig()
        .getServletContext().getAttribute("totalVisitors");

    if (totalVisitors == null) {
        totalVisitors = 1;
    } else {
        totalVisitors++;
    }

    this.getServletConfig().getServletContext()
        .setAttribute("totalVisitors", totalVisitors);
}
```

**FooterServlet.java (reading the counter):**
```java
String city    = getServletContext().getInitParameter("hospitalCity");
String email   = getServletContext().getInitParameter("hospitalEmail");
Integer visitors = (Integer) getServletContext().getAttribute("totalVisitors");
int count = (visitors != null) ? visitors : 0;

out.println("Serving " + city + " | Contact: " + email
          + " | Total Visitors: " + count);
```

> **Why `synchronized`?** Multiple users can hit `HomeServlet` at the same time. Without synchronization, two threads could read the same counter value simultaneously and both write incremented values, losing a count. The `synchronized` block ensures only one thread updates the counter at a time.

---

### ServletContext Scope Comparison

| Scope | Object | Lifespan | Shared With |
|---|---|---|---|
| **Application** | `ServletContext` | Server lifetime | Every user, every servlet |
| **Session** | `HttpSession` | Until logout / timeout | One specific user |
| **Request** | `HttpServletRequest` | One request/response cycle | One request only |

---

## Part 2: HttpSession — Per-User Authentication

### What is HttpSession?

An `HttpSession` is a **server-side object** that stores data for a specific user across multiple HTTP requests. HTTP is stateless by nature — each request is independent. `HttpSession` solves this by giving each user a unique ID (stored in a `JSESSIONID` cookie) and linking it to their stored data on the server.

```
Browser (User A)                        Server
    │                                      │
    │── GET /home ─────────────────────►  │
    │◄─ Set-Cookie: JSESSIONID=abc123 ──  │  ← session created
    │                                      │
    │── GET /staff (JSESSIONID=abc123) ──►│
    │                                      │  ← server finds session abc123
    │                                      │  ← reads getAttribute("user") = "Administrator"
    │◄─ 200 OK — Staff Directory ─────── │
```

---

### New Files Implementing HttpSession

#### `StaffMember.java` — Data Model
A simple model object to hold staff data.
```java
public class StaffMember implements Serializable {
    private String fullName;
    private String email;
    // Constructor, getters, setters
}
```

#### `StaffService.java` / `StaffServiceImpl.java` — Data Layer
Stores staff records in a static, thread-safe list (simulating a database).
```java
// CopyOnWriteArrayList: thread-safe reads, safe for concurrent registration
private static final List<StaffMember> staffDirectory = new CopyOnWriteArrayList<>();
```

---

### The Authentication Flow

#### Step 1: Login — Creating the Session (`LoginServlet.java`)

```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    String user = request.getParameter("username");
    String pass = request.getParameter("password");

    // Validate against the GLOBAL key from ServletContext
    String globalKey = getServletContext().getInitParameter("globalAdminKey");

    if ("admin".equalsIgnoreCase(user) && globalKey.equals(pass)) {
        // ✅ SUCCESS: Create a new session and store the user's identity
        HttpSession session = request.getSession(); // Creates if not exists
        session.setAttribute("user", "Administrator");

        response.sendRedirect("/hospital-webapp/staff");
    } else {
        // ❌ FAILURE: Go back to login with an error
        response.sendRedirect("/hospital-webapp/login?error=invalid");
    }
}
```

> `request.getSession()` with **no argument** creates a new session if one does not exist (equivalent to `getSession(true)`).

---

#### Step 2: Guarding Protected Routes

Any servlet that should only be accessible to logged-in users begins with this check:

**`StaffListServlet.java` and `RegisterServlet.java`:**
```java
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    // ── SESSION GUARD ───────────────────────────────────────────────
    HttpSession session = request.getSession(false); // false = DO NOT create
    if (session == null || session.getAttribute("user") == null) {
        // Not logged in — redirect to login page
        response.sendRedirect("/hospital-webapp/login?error=auth_required");
        return;
    }
    // ── Route is now protected — proceed safely ─────────────────────
    // ... render the page
}
```

> `request.getSession(false)` is critical here. Passing `false` means: *"give me the existing session, but if there isn't one, don't create a new one — just return null."*

---

#### Step 3: Logout — Destroying the Session (`LogoutServlet.java`)

```java
private void processLogout(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false); // Only get if it exists
    if (session != null) {
        session.invalidate(); // 💥 Destroy all session data immediately
    }
    response.sendRedirect("/hospital-webapp/home");
}
```

> `session.invalidate()` removes the session from the server's memory. The user's `JSESSIONID` cookie becomes invalid, and any further requests will have no session.

---

#### Step 4: Dynamic Navigation (`HtmlTemplate.java`)

The navbar dynamically adapts based on whether a session exists:

```java
public static void renderHeader(HttpServletRequest request, PrintWriter out, ...) {
    // Check for active session
    HttpSession session = (request != null) ? request.getSession(false) : null;
    boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

    // ... render CSS, nav ...

    if (isLoggedIn) {
        // Show staff-specific links
        out.println("<li><a href='/hospital-webapp/staff'>Staff Directory</a></li>");
        out.println("<li><a href='/hospital-webapp/register'>New Enrollment</a></li>");
        out.println("<li><a href='/hospital-webapp/logout' style='color:#ef4444;'>Logout</a></li>");
    } else {
        // Show public login link
        out.println("<li><a href='/hospital-webapp/login'>Staff Login</a></li>");
    }
}
```

---

### Complete Flow Diagram

```
┌─── PUBLIC ROUTES (No session required) ──────────────────────────────────┐
│  /home , /services , /appointment , /footer , /notice , /confirmation    │
└───────────────────────────────────────────────────────────────────────────┘

┌─── AUTHENTICATION ────────────────────────────────────────────────────────┐
│  GET  /login  → Show login form                                           │
│  POST /login  → Validate: username=admin, password=SanityAdmin2026        │
│                 ✅ session.setAttribute("user", "Administrator")           │
│                 ✅ Redirect → /staff                                        │
│                 ❌ Redirect → /login?error=invalid                          │
│                                                                           │
│  GET  /logout → session.invalidate() → Redirect → /home                  │
└───────────────────────────────────────────────────────────────────────────┘

┌─── PROTECTED ROUTES (session.getAttribute("user") must not be null) ─────┐
│  GET  /staff    → Show live staff directory table                         │
│  GET  /register → Show staff enrollment form                              │
│  POST /register → Validate key → staffService.addStaff() → /staff        │
└───────────────────────────────────────────────────────────────────────────┘
```

---

### HttpSession vs ServletContext — Quick Reference

| Feature | `HttpSession` | `ServletContext` |
|---|---|---|
| **Scope** | One user's browser session | All users, entire application |
| **Created by** | `request.getSession()` | Server on startup |
| **Destroyed by** | `session.invalidate()` or timeout | Server shutdown |
| **Used for** | Login state, shopping cart, preferences | Global config, visitor counter |
| **One instance** | Per user | Per web application |
| **Our use in app** | `session.setAttribute("user", ...)` | `globalAdminKey`, `totalVisitors` |

---

### Test Credentials

| Field | Value |
|---|---|
| **URL** | `http://localhost:8080/hospital-webapp/login` |
| **Username** | `admin` |
| **Password** | `SanityAdmin2026` |
