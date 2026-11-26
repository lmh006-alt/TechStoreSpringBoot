<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>Đăng nhập | TechStore</title></head>
<body>
<main class="auth-box card">
    <a class="logo" href="${pageContext.request.contextPath}/">Tech<span>Store</span></a>
    <h1>Đăng nhập</h1>
    <c:if test="${not empty success}"><div class="alert alert-success"><c:out value="${success}"/></div></c:if>
    <c:if test="${not empty alert}"><div class="error" role="alert"><c:out value="${alert}"/></div></c:if>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <div class="field">
            <label class="form-label" for="username">Tên đăng nhập</label>
            <input class="form-control" id="username" name="username" maxlength="255" required>
        </div>
        <div class="field">
            <label class="form-label" for="password">Mật khẩu</label>
            <input class="form-control" id="password" name="password" type="password" required>
        </div>
        <button class="btn btn-primary w-100" type="submit">Đăng nhập</button>
    </form>
    <p class="auth-footer">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a></p>
</main>
</body>
</html>
