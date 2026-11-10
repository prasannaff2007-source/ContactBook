<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="status" value="${requestScope['jakarta.servlet.error.status_code']}" />
<c:set var="pageTitle" value="Something went wrong" />
<%@ include file="/WEB-INF/views/_header.jspf" %>

<main class="container narrow">
    <div class="card empty">
        <div class="empty-icon" aria-hidden="true">!</div>
        <c:choose>
            <c:when test="${not empty title}">
                <h1><c:out value="${title}" /></h1>
                <p class="muted"><c:out value="${message}" /></p>
            </c:when>
            <c:when test="${status == 404}">
                <h1>Page not found</h1>
                <p class="muted">The page you are looking for does not exist.</p>
            </c:when>
            <c:when test="${status == 405}">
                <h1>Action not allowed</h1>
                <p class="muted">That action cannot be done this way.</p>
            </c:when>
            <c:otherwise>
                <h1>Something went wrong</h1>
                <p class="muted">An unexpected error happened. Please try again.</p>
            </c:otherwise>
        </c:choose>
        <a class="btn btn-primary" href="${ctx}/contacts">Back to contacts</a>
    </div>
</main>

<%@ include file="/WEB-INF/views/_footer.jspf" %>
