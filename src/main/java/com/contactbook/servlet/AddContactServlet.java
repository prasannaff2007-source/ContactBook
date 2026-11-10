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
import java.util.Map;

/** GET  /contacts/add -> show empty form
 *  POST /contacts/add -> validate, save, then redirect to /contacts (Post/Redirect/Get) */
@WebServlet("/contacts/add")
public class AddContactServlet extends HttpServlet {

    private final ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showForm(request, response, new Contact(), null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = Validator.clean(request.getParameter("name"));
        String phone = Validator.clean(request.getParameter("phone"));
        String email = Validator.clean(request.getParameter("email"));

        Contact contact = new Contact(name, phone, email.isEmpty() ? null : email);
        Map<String, String> errors = Validator.validate(name, phone, email);

        if (!errors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            showForm(request, response, contact, errors);
            return;
        }

        try {
            dao.addContact(contact);
            Flash.set(request, "success", "Contact added successfully.");
            response.sendRedirect(request.getContextPath() + "/contacts");
        } catch (SQLException e) {
            ErrorHelper.databaseError(request, response, e);
        }
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response,
                          Contact contact, Map<String, String> errors)
            throws ServletException, IOException {
        request.setAttribute("mode", "add");
        request.setAttribute("contact", contact);
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(request, response);
    }
}
