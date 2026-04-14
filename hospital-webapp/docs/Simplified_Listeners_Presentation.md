# Simplified Presentation: Java Servlet Listeners
### SanityCare Hospital Web App

---

## 1. Introduction: What is a Listener?

Servlets only run when a user makes a request (like clicking a button). But some important things happen in the background:
- The server starts up or shuts down.
- A user logs in or their session times out.

**Listeners** are background components that listen for these "events" and run automatically. They **do not need a URL** and they **don't wait for a browser request**.

---

## 2. The Three Levels of Listeners

You can listen for events at three different levels (scopes):

1. **Application Level (`ServletContextListener`)**
   - **When:** The whole application starts or stops.
   - **Use case:** Setting up global variables or database connections.
2. **Session Level (`HttpSessionListener`)**
   - **When:** A specific user's session begins or ends (time out / logout).
   - **Use case:** Counting how many users are currently online.
3. **Request Level (`ServletRequestListener`)**
   - **When:** A single HTTP request arrives or finishes.
   - **Use case:** Logging request times or counting total page views.

> *Note: There are also "Attribute Listeners" that fire when data (attributes) are added, removed, or changed at any of these levels.*

---

## 3. How to Create a Listener (3 Simple Steps)

Creating a listener is very straightforward:

1. **Pick the Interface:** Choose the right listener interface (e.g., `HttpSessionListener`).
2. **Write the Code:** Create a Java class that implements the interface.
3. **Register It:** Add purely the class name to `web.xml` (no URL needed!).

```xml
<!-- Example of web.xml registration -->
<listener>
    <listener-class>com.example.MyListener</listener-class>
</listener>
```

---

## 4. Listeners Used in Our Project (SanityCare)

Here are the three listeners we implemented to make the hospital app smarter:

### 1️⃣ AppLifecycleListener (Application Level)
- **What it does:** Runs **once** when the server starts. It sets our `totalVisitors` and `activeSessions` counters to 0 so we don't get errors later, and prints a startup banner in the console.

### 2️⃣ ActiveSessionListener (Session Level)
- **What it does:** Increases the `activeSessions` count when someone opens the app, and decreases it when they log out or their session times out. This powers the **"🟢 Users Online"** tracker in the footer.

### 3️⃣ AuditLogListener (Session Attribute Level)
- **What it does:** Automatically detects when the `user` attribute is added or removed from a session. This creates a secure, timestamps **audit log** in the server console every time a doctor logs in or out.

---

## 5. Listeners vs. Filters vs. Servlets (Quick Summary)

- **Servlets:** The *workers*. They handle direct user requests (like clicking "Submit") and need a URL.
- **Filters:** The *security guards*. They check requests before they reach the Servlet (like checking if you are logged in).
- **Listeners:** The *observers*. They passively watch for background events (like server startup or timeouts) and run silently.

---

## 6. Key Takeaways Fast Review
- Passive / Event-Driven (the server calls them automatically).
- No URLs or mapping paths required.
- Perfect for setup, analytics, audits, and managing active sessions.
