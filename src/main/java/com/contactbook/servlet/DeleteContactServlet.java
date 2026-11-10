package com.contactbook.servlet;

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

/** POST /contacts/delete (id) -> delete, then redirect to /contacts.
 *  Delete is POST only, so a link or a search engine can never delete data by accident. */
@WebServlet("/contacts/delete")
public class DeleteContactServlet extends HttpServlet {

    private final ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/contacts");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id = Validator.parseId(request.getParameter("id"));
        if (id == null) {
            Flash.set(request, "error", "That contact ID is not valid.");
            response.sendRedirect(request.getContextPath() + "/contacts");
            return;
        }

        try {
            if (dao.deleteContact(id)) {
                Flash.set(request, "success", "Contact deleted.");
            } else {
                Flash.set(request, "error", "That contact was already deleted.");
            }
            response.sendRedirect(request.getContextPath() + "/contacts");
        } catch (SQLException e) {
            ErrorHelper.databaseError(request, response, e);
        }
    }
}
