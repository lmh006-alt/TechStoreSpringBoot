<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>${empty user.id ? 'Thêm' : 'Sửa'} người dùng</title></head>
<body>
<div class="page-container page-narrow">
    <h1 class="h3">${empty user.id ? 'Thêm người dùng' : 'Sửa người dùng'}</h1>
    <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
    <div class="panel">
    <form data-validate action="${pageContext.request.contextPath}/admin/users/${empty user.id ? 'add' : 'edit/'.concat(user.id)}" method="post">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <div class="mb-3"><label class="form-label" for="username">Username</label><input class="form-control" id="username" name="username" maxlength="255" value="<c:out value='${user.username}'/>" data-not-blank required></div>
        <div class="mb-3"><label class="form-label" for="password">${empty user.id ? 'Mật khẩu' : 'Mật khẩu mới (để trống nếu không đổi)'}</label><input class="form-control" id="password" name="password" type="password" minlength="8"></div>
        <div class="mb-3"><label class="form-label" for="email">Email</label><input class="form-control" id="email" name="email" type="email" maxlength="255" value="<c:out value='${user.email}'/>" data-not-blank required></div>
        <div class="mb-3"><label class="form-label" for="fullname">Họ tên</label><input class="form-control" id="fullname" name="fullname" maxlength="255" value="<c:out value='${user.fullname}'/>"></div>
        <div class="mb-3"><label class="form-label" for="phone">Số điện thoại</label><input class="form-control" id="phone" name="phone" maxlength="20" value="<c:out value='${user.phone}'/>" data-phone></div>
        <div class="mb-3"><label class="form-label" for="roleId">Role</label>
            <select class="form-select" id="roleId" name="roleId">
                <option value="1" ${user.roleId == 1 ? 'selected' : ''}>ADMIN</option>
                <option value="2" ${user.roleId == null or user.roleId == 2 ? 'selected' : ''}>USER</option>
            </select>
        </div>
        <div class="form-check mb-3">
            <input class="form-check-input" id="active" name="active" type="checkbox" value="true" ${user.active ? 'checked' : ''}>
            <label class="form-check-label" for="active">Tài khoản hoạt động</label>
        </div>
        <button class="btn btn-primary" type="submit">Lưu</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/users">Hủy</a>
    </form>
    </div>
</div>
</body>
</html>
