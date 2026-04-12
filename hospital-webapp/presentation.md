# 10-Minute Presentation Script: The Evolution of Java Servlets & SOLID Architecture

**Estimated Time:** 10 Minutes
**Project:** SanityCare Hospital Web App

---

## Introduction & Setup (1.5 Minutes)
"Good morning, everyone. Thank you for joining me. Today, I am excited to walk you through the architectural journey of our SanityCare Hospital Web App. I'm going to take you behind the scenes, focusing on the three different foundational ways we can build Java Servlets. But more importantly, I’ll show you how we took a working application and elevated it to enterprise standards by applying core SOLID principles.

When we began, we started completely from scratch. We fired up a blank canvas using the standard `maven-archetype-webapp`. Now, out of the box, that archetype is a bit dated. To bring it into the modern era, we made two critical updates before writing any code.

First, we jumped into our `pom.xml`. We added the `jakarta.servlet-api` version 6.0 as a provided dependency, ensuring we were using the latest enterprise specifications. We also updated the Maven compiler plugin to leverage Java 11. 

Second, we went into our `web.xml` deployment descriptor. We bumped the root schema right up to Jakarta EE 6.0. This gave us the ability to properly and seamlessly register our Servlets. With our environment modernized, we started building."

## Way 1: The Raw Interface (1.5 Minutes)
"Our very first task was to build a landing page, the `HomeServlet`. And to do this, we took the most foundational approach possible: directly implementing the `Servlet` interface.

Now, working directly with an interface in Java means you are signing a strict contract. We were forced to write concrete implementations for all five lifecycle methods, regardless of whether we actually needed them. 

We had to implement:
- `init()`, which is triggered precisely once when the Servlet is loaded into memory.
- `destroy()`, called during server shutdown.
- `getServletConfig()` and `getServletInfo()`, which provide metadata.
- And finally, the `service()` method. This is where the magic happens, and where our actual HTML generation code lived.

**The major takeaway here:** While this approach gave us a perfectly functional landing page, it was highly inefficient from a developer standpoint. It forced us to write a large amount of boilerplate code just to get that single `service()` method executing."

## Way 2: The Abstract Class (1.5 Minutes)
"Realizing the boilerplate issue, we pivoted for our next component. We needed a page to display our various medical departments. So, we built the `ServicesServlet`. 

This time, instead of the raw interface, we extended `GenericServlet`. This was an immediate and massive quality-of-life improvement. 

Why was it better? Because `GenericServlet` is an abstract class that has already implemented the `Servlet` interface behind the scenes. It generously provides default, 'dummy' implementations for `init()`, `destroy()`, and the configuration methods. 

**The major takeaway:** By extending `GenericServlet`, we drastically reduced our boilerplate. We only had to override exactly one method—the `service()` method. Our `ServicesServlet` became incredibly clean, focused, and much faster to develop."

## Way 3: The Industry Standard (2 Minutes)
"Finally, we needed to tackle the most complex part of our application: patient scheduling. For this, we built the `BookAppointmentServlet`. And we pulled out the ultimate tool for the job—we extended `HttpServlet`.

Why is `HttpServlet` the gold standard in the industry? Because the previous two methods utilize a generic `service()` method that treats absolutely every request the exact same way. `HttpServlet` changes the game entirely because it inherently understands the HTTP protocol.

Instead of one generic entry point, `HttpServlet` acts as an intelligent traffic router. It inspects the incoming request and dispatches it to highly specific methods.

Here’s how we used it:
First, a user navigates to our booking page. The browser fires a standard HTTP GET request. Our Servlet seamlessly routes this to the `doGet()` method, where we safely render and serve the HTML booking form.

Then, the user fills out their sensitive medical data and clicks submit. The browser sends a POST request. `HttpServlet` catches this POST request and routes it directly to our `doPost()` method. Secure data handling is now fundamentally separated from page loading. 

We were also granted access to upgraded objects—`HttpServletRequest` and `HttpServletResponse`—yielding powerful capabilities like reading cookies, managing user sessions, and enforcing HTTP status codes."

## Elevating to Enterprise Standards with SOLID Principles (2.5 Minutes)
"At this point, we had a fully functional application. It worked perfectly. But as engineers, 'working' isn't enough. We recognized a few glaring architectural flaws.

First, we had massive duplication of UI code. Our CSS styles and HTML wrappers were copy-pasted across all three Servlets. Second, our `BookAppointmentServlet` was doing way too much—it was rendering views, handling HTTP routing, and processing complex business logic to validate patient data and generate reference numbers. 

This violated fundamental software engineering rules. So, we stopped, analyzed our code, and heavily refactored the application using two core SOLID principles.

**Principle Number 1: The Single Responsibility Principle (SRP)**
SRP states that a class should have one, and only one, reason to change. Our Servlets were acting as routers, UI renderers, and business logic processors all at once. 

To fix this, we created a dedicated `HtmlTemplate` class. We extracted all the HTML structure, our premium CSS animations, and layout rendering into this single class. Now, if we want to change our theme, we simply edit one file. The Servlets were reduced to their true purpose: acting purely as HTTP Controllers.

We also extracted all the appointment generation logic completely out of the Servlet and entirely into a dedicated service.

**Principle Number 2: The Dependency Inversion Principle (DIP)**
DIP states that high-level modules should not depend on low-level concrete implementations, but rather on abstractions.

So, instead of having our `BookAppointmentServlet` rely on a hardcoded implementation for booking, we created an abstract interface called `AppointmentService`. We then built an `AppointmentServiceImpl` to handle the actual data processing, and injected that service into our Servlet.

Why does this matter? Tomorrow, the hospital might ask us to change how appointments are processed—maybe they want to save them to a cloud SQL database or trigger an SMS notification API. Because our Servlet relies on an abstraction, we can seamlessly swap out the implementation for a new one without altering a single line of our Servlet’s routing code. The system is now extensible, modular, and testable."

## Conclusion (1 Minute)
"To wrap up, you can see clearly how we matured this project on two separate fronts.

First, the **evolution of the Java architecture itself**: We journeyed from the rigid, boilerplate-heavy `Servlet` interface, to the streamlined `GenericServlet`, and finally arrived at the HTTP-aware, industry-standard `HttpServlet`. 

Second, the **evolution of software design**: We started with tangled, monolithic code files that got the job done quickly. But we took the time to step back and refactor to a maintainable, enterprise-grade architecture. By aggressively applying the Single Responsibility Principle and the Dependency Inversion Principle, we utilized interface-driven services and UI components to ensure our application can scale safely into the future.

Thank you very much. I'll now open the floor to any questions."
