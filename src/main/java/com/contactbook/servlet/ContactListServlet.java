package com.contactbook.servlet;

import com.contactbook.Contact;
import com.contactbook.dao.ContactDAO;
import com.contactbook.util.ErrorHelper;
import com.contactbook.util.Flash;
import com.contactbook.util.Validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/** GET /contacts          -> list all contacts
 *  GET /contacts?q=arun   -> search contacts by name */
@WebServlet("/contacts")
public class ContactListServlet extends HttpServlet {

    private final ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = Validator.clean(request.getParameter("q"));
        if (query.length() > 100) {
            query = query.substring(0, 100);
        }

        try {
            List<Contact> contacts = query.isEmpty()
                    ? dao.getAllContacts()
                    : dao.searchContacts(query);

            Flash.moveToRequest(request);
            request.setAttribute("contacts", contacts);
            request.setAttribute("q", query);
            request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);

        } catch (SQLException e) {
            ErrorHelper.databaseError(request, response, e);
        }
    }
}
