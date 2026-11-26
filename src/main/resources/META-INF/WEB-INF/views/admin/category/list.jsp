<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>Quản lý danh mục</title></head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3">Quản lý danh mục</h1>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/categories/add">Thêm danh mục</a>
    </div>
    <c:if test="${not empty success}"><div class="alert alert-success"><c:out value="${success}"/></div></c:if>
    <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
    <form class="row g-2 mb-3" action="${pageContext.request.contextPath}/admin/categories/searchpaginated" method="get">
        <div class="col-auto"><input class="form-control" type="search" name="name" value="<c:out value='${name}'/>" placeholder="Tên danh mục"></div>
        <input type="hidden" name="size" value="${pageSize}">
        <div class="col-auto"><button class="btn btn-outline-primary" type="submit">Tìm kiếm</button></div>
    </form>
    <c:choose>
        <c:when test="${empty categoryPage.content}">
            <div class="alert alert-info">Không có dữ liệu danh mục.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-bordered table-striped align-middle">
                    <thead><tr><th>STT</th><th>Tên danh mục</th><th>Hình ảnh</th><th>Thao tác</th></tr></thead>
                    <tbody>
                    <c:forEach var="category" items="${categoryPage.content}" varStatus="status">
                        <tr>
                            <td>${categoryPage.number * categoryPage.size + status.index + 1}</td>
                            <td><c:out value="${category.categoryName}"/></td>
                            <td><c:out value="${category.images}"/></td>
                            <td>
                                <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/categories/edit/${category.categoryId}">Sửa</a>
                                <form class="d-inline" action="${pageContext.request.contextPath}/admin/categories/delete/${category.categoryId}" method="post"
                                      onsubmit="return confirm('Bạn có chắc muốn xóa danh mục này?');">
                                    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                    <button class="btn btn-sm btn-danger" type="submit">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="d-flex justify-content-between align-items-center">
        <form action="${pageContext.request.contextPath}/admin/categories/searchpaginated" method="get" class="d-flex align-items-center gap-2">
            <input type="hidden" name="name" value="<c:out value='${name}'/>">
            <label for="size">Số dòng:</label>
            <select id="size" name="size" class="form-select" onchange="this.form.submit()">
                <c:forEach var="value" items="${[3,5,10,15,20]}">
                    <option value="${value}" ${pageSize == value ? 'selected' : ''}>${value}</option>
                </c:forEach>
            </select>
        </form>
        <nav>
            <ul class="pagination mb-0">
                <c:forEach var="number" items="${pageNumbers}">
                    <li class="page-item ${number == currentPage ? 'active' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/categories/searchpaginated?name=<c:out value='${name}'/>&page=${number}&size=${pageSize}">${number}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
