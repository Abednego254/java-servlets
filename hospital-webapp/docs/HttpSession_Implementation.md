# HttpSession Implementation — SanityCare Hospital Web App

## Overview

This document explains the **HttpSession-based authentication** layer added to the SanityCare Hospital Web App. The goal is to protect sensitive staff operations (registration, staff directory) behind a secure login wall, while keeping public-facing pages (Home, Services, Appointment booking) freely accessible.

---

## What Is HttpSession?

`HttpSession` is part of the Jakarta Servlet API. It allows the server to maintain **stateful user data** across multiple HTTP requests from the same browser client. Unlike `ServletContext` (shared with everyone) or request attributes (one-click lifespan), a session:

- Is **unique per browser client**
- Persists until the session is **explicitly invalidated** (logout) or **times out** (idle timeout)
- Is identified internally by a `JSESSIONID` cookie automatically sent by the browser

---

## New Files Created

### 1. `StaffMember.java` — The Data Model
A simple POJO that holds a staff member's details.

```java
public class StaffMember {
    private String fullName;
    private String email;
    // Constructor, getters, setters
}
```

### 2. `StaffService.java` — The Interface (DIP Principle)
Defines the contract for managing staff data. By depending on this interface, our Servlets are decoupled from the implementation.

```java
public interface StaffService {
    void addStaff(StaffMember member);
    List<StaffMember> getAllStaff();
}
```

### 3. `StaffServiceImpl.java` — The Implementation
Uses a **static `CopyOnWriteArrayList`** to store staff members in memory. The `static` keyword ensures the list is shared across all instances of the class (acting like a database), while `CopyOnWriteArrayList` ensures **thread-safety** for concurrent requests.

```java
private static final List<StaffMember> staffDirectory = new CopyOnWriteArrayList<>();
```

### 4. `LoginServlet.java` — The Authentication Gateway
- **`doGet()`**: Renders the login form with proper error messages.
- **`doPost()`**: Validates credentials.
  - **Username**: `admin`
  - **Password**: Fetched from `ServletContext` global parameter `globalAdminKey`
  - **On Success**: Creates a session and stores the username → `request.getSession().setAttribute("user", "Administrator")`
  - **On Failure**: Redirects back with an error flag.

### 5. `LogoutServlet.java` — The Session Terminator
```java
HttpSession session = request.getSession(false); // false = do NOT create new session
if (session != null) {
    session.invalidate(); // Destroy all session data
}
response.sendRedirect("/hospital-webapp/home");
```
> **Key Pattern**: `getSession(false)` is used when you don't want to accidentally create a session during a check.

### 6. `StaffListServlet.java` — The Protected Staff Directory
Renders a dynamic HTML table of all registered staff from `StaffService`. Protected by a session guard at the top of `doGet()`.

---

## Modified Files

### `RegisterServlet.java`
- Added **session guard** at the top of `doGet()` and `doPost()`.
- Upon successful registration, saves the new `StaffMember` to the `StaffService` and **redirects to `/staff`** instead of forwarding to a success page.

### `HtmlTemplate.java`
- `renderHeader()` now accepts `HttpServletRequest request` as its first argument.
- It checks for an active session to **dynamically change the navigation bar**:
  - **Logged In**: Shows `Staff Directory`, `New Enrollment`, and a red `Logout` link.
  - **Logged Out**: Shows a `Staff Login` link.

### `web.xml`
- Registered three new URL mappings:
  - `/login` → `LoginServlet`
  - `/logout` → `LogoutServlet`
  - `/staff` → `StaffListServlet`

---

## The Authentication Flow (Step by Step)

```
┌──────────────────────────────────────────────────────────────┐
│  User visits /staff or /register (protected route)           │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  Session Guard Check:                                │   │
│  │  HttpSession session = request.getSession(false);    │   │
│  │  if (session == null || session.getAttribute("user") │   │
│  │      == null) → redirect to /login                   │   │
│  └──────────────────────────────────────────────────────┘   │
│               │ No valid session                             │
│               ▼                                              │
│         /login (GET) → Show Login Form                       │
│               │                                              │
│               ▼ User submits credentials                     │
│         /login (POST) → Validate admin / SanityAdmin2026     │
│               │                                              │
│      ┌────────┴────────┐                                     │
│      │ SUCCESS         │ FAILURE                             │
│      ▼                 ▼                                     │
│  Create Session    Redirect to /login?error=invalid         │
│  Redirect to /staff                                          │
│      │                                                       │
│      ▼                                                       │
│  /staff (GET) → Session valid → Show Staff Directory table   │
│      │                                                       │
│      ▼ Click "Register New Staff"                            │
│  /register → Session valid → Show form                       │
│      │                                                       │
│      ▼ Submit form with master password                      │
│  /register (POST) → Save to StaffService → Redirect /staff   │
│      │                                                       │
│      ▼ Click "Logout"                                        │
│  /logout → session.invalidate() → Redirect to /home         │
└──────────────────────────────────────────────────────────────┘
```

---

## Key Concepts Summary

| Concept | Method | Scope | Use Case |
|---|---|---|---|
| `getSession()` | Creates or retrieves | Per-user | Store logged-in user |
| `getSession(false)` | Retrieves only (no create) | Per-user | Check if logged in |
| `session.setAttribute("user", ...)` | Store data in session | Per-user | Track who is logged in |
| `session.invalidate()` | Destroy session | Per-user | Logout |
| `request.getSession(false) == null` | Check for session | Per-request | Route guard |

---

## Credentials for Testing

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `SanityAdmin2026` (defined in `web.xml` as `globalAdminKey`) |
