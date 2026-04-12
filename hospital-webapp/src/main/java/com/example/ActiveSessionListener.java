package com.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * ActiveSessionListener — implements HttpSessionListener.
 *
 * Fires whenever an HttpSession is CREATED or DESTROYED anywhere in the application.
 * Maintains a live count of currently active sessions in the ServletContext,
 * which is displayed in the FooterServlet as "Users Online".
 *
 * Note: A session is CREATED the first time request.getSession() or
 * request.getSession(true) is called. A session is DESTROYED when
 * session.invalidate() is called (logout) or the session times out.
 */
public class ActiveSessionListener implements HttpSessionListener {

    /**
     * Called whenever a NEW HttpSession is created.
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session    = event.getSession();
        ServletContext context = session.getServletContext();

        // Thread-safe increment
        synchronized (context) {
            Integer active = (Integer) context.getAttribute("activeSessions");
            if (active == null) active = 0;
            context.setAttribute("activeSessions", active + 1);
        }

        System.out.println("==> ActiveSessionListener: Session CREATED ["
                + session.getId().substring(0, 8) + "...] | Active sessions: "
                + context.getAttribute("activeSessions"));
    }

    /**
     * Called whenever an HttpSession is DESTROYED (logout or timeout).
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session    = event.getSession();
        ServletContext context = session.getServletContext();

        // Thread-safe decrement, never go below 0
        synchronized (context) {
            Integer active = (Integer) context.getAttribute("activeSessions");
            if (active == null || active <= 0) {
                context.setAttribute("activeSessions", 0);
            } else {
                context.setAttribute("activeSessions", active - 1);
            }
        }

        System.out.println("==> ActiveSessionListener: Session DESTROYED ["
                + session.getId().substring(0, 8) + "...] | Active sessions: "
                + context.getAttribute("activeSessions"));
    }
}
