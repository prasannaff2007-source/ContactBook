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

/** GET  /contacts/edit?id=5 -> show form filled with contact 5
 *  POST /contacts/edit      -> validate, update, then redirect to /contacts */
@WebServlet("/contacts/edit")
public class EditContactServlet extends HttpServlet {

    private final ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id = Validator.parseId(request.getParameter("id"));
        if (id == null) {
            backToList(request, response, "That contact ID is not valid.");
            return;
        }

        try {
            Contact contact = dao.getContactById(id);
            if (contact == null) {
                backToList(request, response, "That contact no longer exists.");
                return;
            }
            showForm(request, response, contact, null);
        } catch (SQLException e) {
            ErrorHelper.databaseError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id = Validator.parseId(request.getParameter("id"));
        if (id == null) {
            backToList(request, response, "That contact ID is not valid.");
            return;
        }

        String name = Validator.clean(request.getParameter("name"));
        String phone = Validator.clean(request.getParameter("phone"));
        String email = Validator.clean(request.getParameter("email"));

        Contact contact = new Contact(id, name, phone, email.isEmpty() ? null : email);
        Map<String, String> errors = Validator.validate(name, phone, email);

        if (!errors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            showForm(request, response, contact, errors);
            return;
        }

        try {
            if (dao.updateContact(contact)) {
                Flash.set(request, "success", "Contact updated successfully.");
            } else {
                Flash.set(request, "error", "Nothing was updated. The contact may have been deleted.");
            }
            response.sendRedirect(request.getContextPath() + "/contacts");
        } catch (SQLException e) {
            ErrorHelper.databaseError(request, response, e);
        }
    }

    private void backToList(HttpServletRequest request, HttpServletResponse response, String message)
            throws IOException {
        Flash.set(request, "error", message);
        response.sendRedirect(request.getContextPath() + "/contacts");
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response,
                          Contact contact, Map<String, String> errors)
            throws ServletException, IOException {
        request.setAttribute("mode", "edit");
        request.setAttribute("contact", contact);
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(request, response);
    }
}
