# Migration to Annotations in Java Servlets

During the recent refactoring of the `hospital-webapp` project, we successfully migrated our configuration architecture from a centralized XML approach (`web.xml`) to a more modern, decentralized annotations-based approach (Jakarta EE / Java EE 6+).

This document explains the steps taken, the annotations used, and the underlying reasons behind certain design choices.

---

## 1. What Are Annotations?

Before Java EE 6 (Servlet 3.0), every single Servlet, Filter, and Listener had to be manually registered and mapped to specific URL patterns inside the `WEB-INF/web.xml` file. As applications grew, this file became massive, difficult to navigate, and prone to merge conflicts.

**Annotations** allow developers to define the routing and metadata directly above the Java class definition itself. This keeps the configuration and the code in exactly the same place (colocated), making the application significantly easier to read and maintain.

---

## 2. The Core Annotations We Used

Here is how the old `web.xml` components were replaced:

### A. `@WebServlet` (Replaces `<servlet>` and `<servlet-mapping>`)
Instead of an XML block, we now annotate the class directly.
**Example:**
```java
@WebServlet("/home")
public class HomeServlet implements Servlet { ... }
```
For servlets with initialization parameters (like our `RegisterServlet`), the annotation handles it neatly:
```java
@WebServlet(urlPatterns = {"/register"}, initParams = {
    @WebInitParam(name = "registrationSecret", value = "Sanity2026")
})
public class RegisterServlet extends HttpServlet { ... }
```

### B. `@WebFilter` (Replaces `<filter>` and `<filter-mapping>`)
Filters are now seamlessly attached to their URL patterns without touching XML.
**Example:**
```java
@WebFilter("/*")
public class AuthenticationFilter implements Filter { ... }
```

### C. `@WebListener` (Replaces `<listener>`)
Listeners for session caching, app lifecycles, and audit logging simply require an empty annotation. The servlet container knows which lifecycle events to trigger based on the interfaces the class implements (e.g., `HttpSessionListener`, `ServletContextListener`).
**Example:**
```java
@WebListener
public class ActiveSessionListener implements HttpSessionListener { ... }
```

---

## 3. Why is `web.xml` Not Completely Empty?

When you open `src/main/webapp/WEB-INF/web.xml`, you will notice it isn't entirely blank. It still contains the XML headers and our global **Context Parameters**:

```xml
  <context-param>
    <param-name>globalAdminKey</param-name>
    <param-value>SanityAdmin2026</param-value>
  </context-param>
  <context-param>
    <param-name>hospitalCity</param-name>
    <param-value>Nairobi, Kenya</param-value>
  </context-param>
```

### The Reason
While `@WebInitParam` exists for **local** configuration (configuration tied to a *specific* Servlet or Filter), **there is no class-level annotation equivalent for `<context-param>`**. 

A `<context-param>` is meant strictly for **global** application variables that apply to the entire `ServletContext`. Since an annotation by definition has to be attached to a specific Java class, attaching a universal configuration to just one random class doesn't make structural sense. 

While it *is* technically possible to define these variables programmatically inside a `ServletContextListener` using `servletContext.setInitParameter("...", "...")`, it violates the principle of keeping configuration (like API Keys and global strings) abstracted from compiled Java code. 

Therefore, keeping `web.xml` solely as an environment file for overarching Application Constants (Context Params) represents best practices in modern Servlet environments.

---

## 4. Advantages of This Migration

1. **Faster Development**: Dropping a new Servlet into the project no longer requires modifying a separate configuration file.
2. **Improved Readability**: Developers can instantly tell what URL a Servlet responds to simply by glancing at the top of the Java file.
3. **Fewer Merge Conflicts**: Team members creating different Servlets won't constantly run into Git conflicts on `web.xml`.
4. **Separation of Concerns**: Global environmental constants are perfectly isolated in `web.xml`, while functional routing rests directly in the Java sources.
