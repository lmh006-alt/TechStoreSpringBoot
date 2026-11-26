<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>Đăng ký | TechStore</title></head>
<body>
<main class="auth-box card">
    <a class="logo" href="${pageContext.request.contextPath}/">Tech<span>Store</span></a>
    <h1>Đăng ký tài khoản</h1>
    <c:if test="${not empty alert}"><div class="error" role="alert"><c:out value="${alert}"/></div></c:if>
    <form data-validate action="${pageContext.request.contextPath}/register" method="post">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <div class="field"><label class="form-label" for="username">Tên đăng nhập</label><input class="form-control" id="username" name="username" maxlength="255" value="<c:out value='${param.username}'/>" autocomplete="username" data-not-blank required></div>
        <div class="field"><label class="form-label" for="password">Mật khẩu</label><input class="form-control" id="password" name="password" type="password" minlength="8" autocomplete="new-password" data-not-blank required></div>
        <div class="field"><label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label><input class="form-control" id="confirmPassword" name="confirmPassword" type="password" minlength="8" autocomplete="new-password" data-not-blank data-match="password" required></div>
        <div class="field"><label class="form-label" for="email">Email</label><input class="form-control" id="email" name="email" type="email" maxlength="255" value="<c:out value='${param.email}'/>" autocomplete="email" data-not-blank required></div>
        <div class="field"><label class="form-label" for="fullname">Họ tên</label><input class="form-control" id="fullname" name="fullname" maxlength="255" value="<c:out value='${param.fullname}'/>" autocomplete="name"></div>
        <div class="field"><label class="form-label" for="phone">Số điện thoại</label><input class="form-control" id="phone" name="phone" maxlength="20" value="<c:out value='${param.phone}'/>" type="tel" autocomplete="tel" data-phone></div>
        <button class="btn btn-primary w-100" type="submit">Đăng ký</button>
    </form>
    <p class="auth-footer">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
</main>
</body>
</html>
