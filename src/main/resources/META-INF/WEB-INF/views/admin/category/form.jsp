<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>${empty category.categoryId ? 'Thêm' : 'Sửa'} danh mục</title></head>
<body>
<div class="container py-4">
    <h1 class="h3">${empty category.categoryId ? 'Thêm danh mục' : 'Sửa danh mục'}</h1>
    <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
    <form:form modelAttribute="category"
               action="${pageContext.request.contextPath}/admin/categories/${empty category.categoryId ? 'add' : 'edit/'.concat(category.categoryId)}"
               method="post">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <div class="mb-3">
            <form:label path="categoryName" cssClass="form-label">Tên danh mục</form:label>
            <form:input path="categoryName" cssClass="form-control" maxlength="255"/>
            <form:errors path="categoryName" cssClass="text-danger"/>
        </div>
        <div class="mb-3">
            <form:label path="images" cssClass="form-label">Tên/đường dẫn hình ảnh</form:label>
            <form:input path="images" cssClass="form-control" maxlength="255"/>
            <form:errors path="images" cssClass="text-danger"/>
        </div>
        <button class="btn btn-primary" type="submit">Lưu</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/categories">Hủy</a>
    </form:form>
</div>
</body>
</html>
