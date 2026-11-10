<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="isEdit" value="${mode == 'edit'}" />
<c:set var="pageTitle" value="${isEdit ? 'Edit contact' : 'Add contact'}" />
<%@ include file="/WEB-INF/views/_header.jspf" %>

<main class="container narrow">

    <a class="back" href="${ctx}/contacts">← Back to contacts</a>

    <div class="card form-card">
        <h1>${isEdit ? 'Edit contact' : 'Add a new contact'}</h1>
        <p class="muted">Fields marked * are required.</p>

        <c:if test="${not empty errors}">
            <div class="alert alert-error" role="alert">Please fix the highlighted fields and try again.</div>
        </c:if>

        <form action="${ctx}/contacts/${isEdit ? 'edit' : 'add'}" method="post">

            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="<c:out value='${contact.id}' />">
            </c:if>

            <div class="field ${not empty errors.name ? 'has-error' : ''}">
                <label for="name">Full name *</label>
                <input id="name" name="name" type="text" required maxlength="100" autocomplete="off"
                       placeholder="e.g. Arun Kumar" value="<c:out value='${contact.name}' />">
                <c:if test="${not empty errors.name}">
                    <p class="error-text"><c:out value="${errors.name}" /></p>
                </c:if>
            </div>

            <div class="field ${not empty errors.phone ? 'has-error' : ''}">
                <label for="phone">Phone number *</label>
                <input id="phone" name="phone" type="tel" required minlength="7" maxlength="15" autocomplete="off"
                       placeholder="e.g. 9876543210" value="<c:out value='${contact.phone}' />">
                <c:if test="${not empty errors.phone}">
                    <p class="error-text"><c:out value="${errors.phone}" /></p>
                </c:if>
            </div>

            <div class="field ${not empty errors.email ? 'has-error' : ''}">
                <label for="email">Email address <span class="muted">(optional)</span></label>
                <input id="email" name="email" type="email" maxlength="100" autocomplete="off"
                       placeholder="e.g. arun@example.com" value="<c:out value='${contact.email}' />">
                <c:if test="${not empty errors.email}">
                    <p class="error-text"><c:out value="${errors.email}" /></p>
                </c:if>
            </div>

            <div class="form-actions">
                <button class="btn btn-primary" type="submit">${isEdit ? 'Save changes' : 'Save contact'}</button>
                <a class="btn btn-ghost" href="${ctx}/contacts">Cancel</a>
            </div>
        </form>
    </div>
</main>

<%@ include file="/WEB-INF/views/_footer.jspf" %>
