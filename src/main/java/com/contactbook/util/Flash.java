package com.contactbook.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * "Flash" messages: a message saved in the session before a redirect,
 * shown once on the next page, then removed (Post/Redirect/Get pattern).
 */
public final class Flash {

    private static final String TYPE = "flashType";
    private static final String MESSAGE = "flashMessage";

    private Flash() {
    }

    /** type is "success" or "error". */
    public static void set(HttpServletRequest request, String type, String message) {
        HttpSession session = request.getSession();
        session.setAttribute(TYPE, type);
        session.setAttribute(MESSAGE, message);
    }

    /** Moves the message from the session to the request so the JSP can show it once. */
    public static void moveToRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        Object message = session.getAttribute(MESSAGE);
        if (message != null) {
            request.setAttribute(MESSAGE, message);
            request.setAttribute(TYPE, session.getAttribute(TYPE));
            session.removeAttribute(MESSAGE);
            session.removeAttribute(TYPE);
        }
    }
}
