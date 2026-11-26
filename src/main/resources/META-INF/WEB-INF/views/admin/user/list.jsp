<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>Quản lý người dùng</title></head>
<body>
<div class="page-container">
    <div class="page-heading">
        <div><p class="eyebrow">QUẢN TRỊ</p><h1>Quản lý người dùng</h1></div>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/users/add">Thêm người dùng</a>
    </div>
    <c:if test="${not empty success}"><div class="alert alert-success"><c:out value="${success}"/></div></c:if>
    <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
    <form class="admin-search" action="${pageContext.request.contextPath}/admin/users/searchpaginated" method="get">
        <input class="form-control" type="search" name="keyword" value="<c:out value='${keyword}'/>" placeholder="Username, họ tên hoặc email" aria-label="Tìm kiếm người dùng">
        <input type="hidden" name="size" value="${pageSize}">
        <button class="btn btn-outline" type="submit">Tìm kiếm</button>
    </form>
    <c:choose>
        <c:when test="${empty userPage.content}">
            <div class="alert alert-info">Không có dữ liệu người dùng.</div>
        </c:when>
        <c:otherwise>
            <div class="panel table-responsive">
                <table class="table table-hover align-middle category-table">
                    <thead><tr><th>STT</th><th>Username</th><th>Họ tên</th><th>Email</th><th>Role</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
                    <tbody>
                    <c:forEach var="user" items="${userPage.content}" varStatus="status">
                        <tr>
                            <td>${userPage.number * userPage.size + status.index + 1}</td>
                            <td><c:out value="${user.username}"/></td>
                            <td><c:out value="${user.fullname}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td>${user.roleId == 1 ? 'ADMIN' : 'USER'}</td>
                            <td>${user.active ? 'Hoạt động' : 'Khóa'}</td>
                            <td>
                                <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/users/edit/${user.id}">Sửa</a>
                                <form class="d-inline" action="${pageContext.request.contextPath}/admin/users/delete/${user.id}" method="post"
                                      onsubmit="return confirm('Bạn có chắc muốn xóa người dùng này?');">
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
    <div class="pagination-toolbar">
        <form action="${pageContext.request.contextPath}/admin/users/searchpaginated" method="get" class="d-flex align-items-center gap-2">
            <input type="hidden" name="keyword" value="<c:out value='${keyword}'/>">
            <label for="size">Số dòng:</label>
            <select id="size" name="size" class="form-select" onchange="this.form.submit()">
                <c:forEach var="value" items="${[3,5,10,15,20]}">
                    <option value="${value}" ${pageSize == value ? 'selected' : ''}>${value}</option>
                </c:forEach>
            </select>
        </form>
        <nav aria-label="Phân trang người dùng"><ul class="pagination mb-0">
            <c:forEach var="number" items="${pageNumbers}">
                <li class="page-item ${number == currentPage ? 'active' : ''}">
                    <a class="page-link" aria-current="${number == currentPage ? 'page' : ''}" href="${pageContext.request.contextPath}/admin/users/searchpaginated?keyword=<c:out value='${keyword}'/>&amp;page=${number}&amp;size=${pageSize}">${number}</a>
                </li>
            </c:forEach>
        </ul></nav>
    </div>
</div>
</body>
</html>
