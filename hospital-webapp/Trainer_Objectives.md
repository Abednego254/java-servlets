# Trainer Objectives: ServletConfig & RequestDispatcher

Today's lesson focused on moving from "Hardcoded Java" to "Configurable & Modular JEE Architecture." Here is exactly what the trainer wanted you to master:

## 1. ServletConfig (The "Private" Settings)
**Goal:** Learn how to customize a servlet's behavior from `web.xml` without editing the `.java` source code.

*   **Key Concept:** Use `<init-param>` in `web.xml` to store metadata (like `pageHeader`, `pageTitle`, or a `secretKey`).
*   **Java Access:** Use `getServletConfig().getInitParameter("name")`.
*   **Why?** If you move the project to a different hospital, you can change the page header in `web.xml` (the configuration) instead of re-compiling the Java code.

## 2. RequestDispatcher: include() (The "Modular" UI)
**Goal:** Break the UI into reusable chunks to avoid copy-pasting HTML in every servlet.

*   **Key Concept:** Create a dedicated servlet (e.g., `FooterServlet`) for a shared UI component.
*   **Logic:** Use `request.getRequestDispatcher("/footer").include(request, response)` to "stitch" that component into any page.
*   **Why?** If you need to update the copyright year or a link in the footer, you only edit **one file** (`FooterServlet`), and it updates across the entire application.

## 3. RequestDispatcher: forward() (The "Handoff")
**Goal:** Separate processing logic from the final display.

*   **Key Concept:** One servlet processes data (e.g., `BookAppointmentServlet`), then "hands over" the request to another (e.g., `ConfirmationServlet`) using `forward()`.
*   **User Experience:** The browser URL remains on the original route (e.g., `/appointment`), making the transition feel seamless and secure.

---
**Summary:** You have successfully moved from monolithic code to an **Interface-driven, Configurable Architecture**.
