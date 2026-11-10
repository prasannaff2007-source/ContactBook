package com.contactbook.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Shows a friendly error page. Details go to the server log only, never to the visitor. */
public final class ErrorHelper {

    private ErrorHelper() {
    }

    public static void databaseError(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        request.getServletContext().log("Database error in Contact Book", e);

        request.setAttribute("title", "Database problem");
        request.setAttribute("message",
                "We could not complete that request because of a database problem. "
                + "Please try again. If it keeps happening, check that MySQL is running "
                + "(the technical details are in the Tomcat log).");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}
