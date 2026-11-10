# Contact Book - Presentation Notes (about 5 minutes)

**1. Objective** - A simple web application to store and manage contacts (add, view, search, edit, delete) instead of paper or phone-only lists.

**2. Technologies**
- Front end: HTML, CSS, JSP (JSTL)
- Back end: Java Servlets
- Database: MySQL, connected with JDBC
- Tools: Maven (build), Apache Tomcat 10.1 (server), VS Code (editor)

**3. Architecture (MVC pattern)**
- **Model:** `Contact` class + `ContactDAO` (database access)
- **View:** JSP pages (`list.jsp`, `form.jsp`)
- **Controller:** Servlets (`ContactListServlet`, `AddContactServlet`, `EditContactServlet`, `DeleteContactServlet`)
- Flow: Browser -> Servlet -> DAO -> MySQL -> back to Servlet -> JSP -> Browser

**4. Database** - one table `contacts(id, name, phone, email)`; `id` is AUTO_INCREMENT primary key.

**5. Workflow (example: Add)** - User fills form -> POST /contacts/add -> servlet validates -> DAO inserts with PreparedStatement -> redirect to list (Post/Redirect/Get) -> success message shown once.

**6. Features** - Add, view, search by name, edit, delete with confirmation, input validation, responsive design (works on phone), friendly error page.

**7. Security points** - PreparedStatement (stops SQL injection), `c:out` (stops XSS), password kept in a config file not in code, delete only by POST, no stack traces shown to users.

**8. Conclusion** - The project shows a complete Java web application with database, built with industry-standard tools. Future scope: login system, pagination, export to CSV, profile photos.

## Possible questions
- *Why PreparedStatement?* Prevents SQL injection and handles quotes safely.
- *What is a Servlet?* A Java class that handles HTTP requests and responses.
- *Why redirect after POST?* So refreshing the page does not submit the form twice.
- *What is JDBC?* The Java API for connecting to databases.
- *What does Maven do?* Downloads libraries and builds the WAR file.
