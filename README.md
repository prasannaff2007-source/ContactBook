# Contact Book - Java Web Application

A small web application to **add, view, search, edit and delete contacts**.

| Layer     | Technology                                   |
|-----------|----------------------------------------------|
| Front end | HTML, CSS (responsive), JSP + JSTL           |
| Back end  | Java Servlets (Jakarta Servlet 6.0)          |
| Database  | MySQL 8 using JDBC (`PreparedStatement`)     |
| Build     | Maven (WAR file)                             |
| Server    | Apache Tomcat 10.1                           |

## How to run (3 small steps)

1. **Database** - In MySQL Workbench open the `ContactBook` connection and run `database/schema.sql`
   (it is safe to run again; it also adds 4 demo contacts - delete those lines if you do not want them).
2. **Password** - Open `src/main/resources/db.properties` and replace `PUT_YOUR_MYSQL_PASSWORD_HERE`
   with your own MySQL root password. Save the file (Ctrl+S). Never share this file.
3. **Run** - Double-click `deploy.bat`. It builds the project, copies the WAR to Tomcat and starts Tomcat.
   Wait about 10 seconds, then open: **http://localhost:8080/ContactBook/**

Do not run WAMP's MySQL at the same time (both use port 3306).

## Features

- Add a contact (name, phone, optional email) with server-side validation
- List all contacts, newest first
- Search by name (partial match, not case sensitive)
- Edit a contact
- Delete a contact (asks for confirmation; POST only)
- Friendly messages after every action; friendly error page (no stack traces shown)

## Folder structure

```
ContactBook
├── pom.xml                      Maven settings and libraries
├── deploy.bat                   one-click build + deploy + start
├── database/schema.sql          table + demo data
└── src/main
    ├── java/com/contactbook
    │   ├── Contact.java         model (id, name, phone, email)
    │   ├── dao/ContactDAO.java  all SQL (PreparedStatement)
    │   ├── servlet/             ContactListServlet, AddContactServlet,
    │   │                        EditContactServlet, DeleteContactServlet
    │   └── util/                DBConnection, Validator, Flash, ErrorHelper, EncodingFilter
    ├── resources/db.properties  database settings (your password goes here)
    └── webapp
        ├── index.jsp            redirects to /contacts
        ├── css/style.css        the design
        └── WEB-INF
            ├── web.xml          error pages, welcome file
            └── views/           list.jsp, form.jsp, error.jsp (+ header/footer parts)
```

## URLs

| URL                      | Method | What it does                    |
|--------------------------|--------|---------------------------------|
| `/contacts`              | GET    | list contacts (`?q=name` search)|
| `/contacts/add`          | GET    | show empty form                 |
| `/contacts/add`          | POST   | validate + save + redirect      |
| `/contacts/edit?id=5`    | GET    | show form filled with contact 5 |
| `/contacts/edit`         | POST   | validate + update + redirect    |
| `/contacts/delete`       | POST   | delete + redirect               |

## Common problems

| Problem | Fix |
|---------|-----|
| "Database problem" page | MySQL not running, wrong password in `db.properties`, or database `contactbook` missing. See the Tomcat window for the exact reason. |
| `Public Key Retrieval is not allowed` in Tomcat log | In `db.properties` add `&allowPublicKeyRetrieval=true` at the end of `db.url`, then run `deploy.bat` again. |
| `Access denied for user 'root'` | Password in `db.properties` is wrong. |
| Port 8080 already in use | Another Tomcat is running. Close it, or run `C:\tomcat\apache-tomcat-10.1.61\bin\shutdown.bat`. |
| Build fails: `mvn` not recognized | Close and reopen the window, or check Maven is in PATH (`mvn -version`). |
| 404 page | Use the full address with the name: `http://localhost:8080/ContactBook/` |
