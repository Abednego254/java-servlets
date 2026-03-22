# 5-Minute Presentation: The Evolution of Java Servlets

**Estimated Time:** 5 Minutes
**Project:** SanityCare Hospital Web App

---

## 1. Introduction & Setup (1 Minute)
**"Welcome everyone. Today I'm going to walk you through how we built the SanityCare Hospital Web App, specifically focusing on the three different ways to create Java Servlets."**

We started with a completely blank canvas using the standard `maven-archetype-webapp`. To get it ready for modern Java, we made **two critical changes**:
1. **`pom.xml`**: We added the `jakarta.servlet-api` (version 6.0) as a *provided* dependency and configured the maven compiler plugin for Java 11.
2. **`web.xml`**: We upgraded the root schema to Jakarta EE 6.0 so we could properly register our servlets.

---

## 2. Way 1: The Raw Interface (1 Minute)
**"Our first approach was building the `HomeServlet` by directly implementing the `Servlet` interface."**

Because `Servlet` is an interface, Java enforces a strict contract. We *had* to write concrete code for all **5 lifecycle methods**, even if we didn't need them all:
- `init()` (Called once when memory is allocated)
- **`service()`** (This is where our actual HTML generation lived)
- `destroy()` (Called once during shutdown)
- `getServletConfig()` & `getServletInfo()`

**The Takeaway:** It works perfectly for our landing page, but it forces us to write a lot of boilerplate code just to get a single `service()` method running.

---

## 3. Way 2: The Abstract Class (1 Minute)
**"To display our Medical Departments, we built the `ServicesServlet`. This time, we extended `GenericServlet`, which is a massive quality-of-life improvement."**

### Why is it better?
`GenericServlet` is an abstract class that already implements the `Servlet` interface for us. It provides "dummy" or default implementations for `init()`, `destroy()`, and the config methods. 

**The Takeaway:** We only had to override exactly **one** method: `service()`. This drastically reduced our boilerplate code, making `ServicesServlet` much cleaner and faster to write.

---

## 4. Way 3: The Industry Standard (1.5 Minutes)
**"Finally, we built the `BookAppointmentServlet`. For this, we used the ultimate approach: extending `HttpServlet`."**

### Why is this the gold standard?
The previous two ways use a generic `service()` method that treats every request the exact same way. `HttpServlet` changes the game because it actually understands the **HTTP Protocol**.

Instead of a single `service()` method, it acts as an intelligent traffic router. It looks at the incoming HTTP request and redirects it to specific methods:
1. **`doGet()`**: If a user is just loading a page (like clicking "Book Appointment"), it triggers `doGet()`. This is where we safely serve the HTML booking form.
2. **`doPost()`**: When the user fills out their sensitive medical data and hits submit, the browser sends a `POST` request. `HttpServlet` catches this and routes it perfectly to our `doPost()` method, where we process their data securely via the request body (hidden from the URL) and generate a success confirmation.

We also get access to upgraded objects—`HttpServletRequest` and `HttpServletResponse`—which give us superpowers like reading cookies, managing sessions, and setting HTTP status codes like `400 Bad Request`.

---

## 5. Conclusion (30 Seconds)
**"To wrap up, you can see the clear evolutionary chain of Java Servlets:"**
1. **`Servlet`:** The raw interface. Strict rules, lots of boilerplate.
2. **`GenericServlet`:** Removes the boilerplate, leaving just the logic.
3. **`HttpServlet`:** The industry standard. Adds full HTTP awareness, perfectly separating safe page-loads (GET) from secure data submissions (POST).

**"Thank you. Are there any questions?"**
