package com.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
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
