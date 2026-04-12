# Assignment Completion: ServletContext Implementation

This document outlines the changes made to move the **SanityCare Hospital Web App** from a local, servlet-specific configuration to a global, application-wide configuration using `ServletContext`.

## 1. Global Parameters (`web.xml`)
Instead of hardcoding values or storing them in specific servlets, we now use `<context-param>` in the deployment descriptor.

- **`globalAdminKey`**: A single master password (`SanityAdmin2026`) that controls all sensitive actions across the entire application.
- **`hospitalCity` & `hospitalEmail`**: Global metadata accessible by any servlet, allowing for instant site-wide updates.

## 2. Protected Actions (Global Security)
The requirement was that "no one can add anything without the right password." We have enforced this globally:

- **Staff Registration**: Now validates against the `globalAdminKey` from the `ServletContext`.
- **Appointment Booking**: A new "Admin Passcode" field was added to the booking form. Appointments are rejected unless this global master password is provided.

## 3. Application-wide State (Visitor Counter)
We implemented a global visitor counter to demonstrate how `ServletContext` can share data across all users and pages.

- **Logic**: Every time the `HomeServlet` is accessed, it increments a `totalVisitors` attribute in the `ServletContext`.
- **Visibility**: This counter is displayed in the **Footer** of every page, alongside the global city and email metadata.
- **Persistence**: Unlike request attributes (which last for one click) or session attributes (which last for one user), this counter lasts as long as the application is running on the server.

---

## Verification Steps (How to Test)

### Step 1: Metadata Update
1. Open `web.xml`.
2. Change the `hospitalCity` to something else (e.g., "Mombasa, Kenya").
3. Refresh any page.
4. **Verified**: The footer on all pages instantly reflects the change.

### Step 2: Global Security
1. Go to **Staff Registration**.
2. Try the old key (`Sanity2026`). It will be rejected.
3. Try the new global key (`SanityAdmin2026`). It will be accepted.
4. Apply the same test to the **Book Appointment** form.

### Step 3: Global Counter
1. Open the Home page in a private/incognito window.
2. Observe the "Global Site Visitors" count.
3. Refresh or navigate to another page.
4. **Verified**: The count increments and remains consistent across all sessions.
