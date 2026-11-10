<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="My contacts" />
<%@ include file="/WEB-INF/views/_header.jspf" %>

<main class="container">

    <section class="page-head">
        <div>
            <h1>My contacts</h1>
            <p class="muted">
                <c:choose>
                    <c:when test="${not empty q}">
                        ${fn:length(contacts)} result(s) for “<c:out value="${q}" />”
                    </c:when>
                    <c:otherwise>${fn:length(contacts)} contact(s) saved</c:otherwise>
                </c:choose>
            </p>
        </div>
        <a class="btn btn-primary" href="${ctx}/contacts/add">+ Add contact</a>
    </section>

    <c:if test="${not empty flashMessage}">
        <div class="alert alert-${flashType}" role="status"><c:out value="${flashMessage}" /></div>
    </c:if>

    <form class="search" action="${ctx}/contacts" method="get" role="search">
        <input type="search" name="q" value="<c:out value='${q}' />" maxlength="100"
               placeholder="Search by name…" aria-label="Search contacts by name">
        <button class="btn btn-primary" type="submit">Search</button>
        <c:if test="${not empty q}">
            <a class="btn btn-ghost" href="${ctx}/contacts">Clear</a>
        </c:if>
    </form>

    <c:choose>
        <c:when test="${empty contacts}">
            <div class="card empty">
                <div class="empty-icon" aria-hidden="true">☎</div>
                <c:choose>
                    <c:when test="${not empty q}">
                        <h2>No contacts found</h2>
                        <p class="muted">Nobody matches “<c:out value="${q}" />”. Try a shorter name.</p>
                    </c:when>
                    <c:otherwise>
                        <h2>No contacts yet</h2>
                        <p class="muted">Add your first contact to get started.</p>
                        <a class="btn btn-primary" href="${ctx}/contacts/add">+ Add contact</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:when>

        <c:otherwise>
            <div class="card table-card">
                <table class="contacts">
                    <thead>
                    <tr>
                        <th>Contact</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th class="col-actions">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ct" items="${contacts}">
                        <tr>
                            <td data-label="Contact">
                                <div class="who">
                                    <span class="avatar av-${ct.id % 6}" aria-hidden="true"><c:out value="${fn:toUpperCase(fn:substring(ct.name, 0, 1))}" /></span>
                                    <span>
                                        <span class="name"><c:out value="${ct.name}" /></span>
                                        <small class="muted">ID <c:out value="${ct.id}" /></small>
                                    </span>
                                </div>
                            </td>
                            <td data-label="Phone">
                                <a class="link" href="tel:<c:out value='${ct.phone}' />"><c:out value="${ct.phone}" /></a>
                            </td>
                            <td data-label="Email">
                                <c:choose>
                                    <c:when test="${not empty ct.email}">
                                        <a class="link" href="mailto:<c:out value='${ct.email}' />"><c:out value="${ct.email}" /></a>
                                    </c:when>
                                    <c:otherwise><span class="muted">—</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td class="col-actions" data-label="Actions">
                                <div class="row-actions">
                                    <a class="btn btn-small" href="${ctx}/contacts/edit?id=${ct.id}">Edit</a>
                                    <form class="inline" action="${ctx}/contacts/delete" method="post"
                                          onsubmit="return confirm('Delete this contact? This cannot be undone.');">
                                        <input type="hidden" name="id" value="${ct.id}">
                                        <button class="btn btn-small btn-danger" type="submit">Delete</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<%@ include file="/WEB-INF/views/_footer.jspf" %>
