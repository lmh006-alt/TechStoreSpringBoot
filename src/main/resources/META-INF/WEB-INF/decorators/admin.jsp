<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title"/> | Quản trị TechStore</title>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/static/favicon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <sitemesh:write property="head"/>
</head>
<body class="site-page">
<header class="site-header">
    <div class="header-inner">
        <a class="logo" href="${pageContext.request.contextPath}/">Tech<span>Store</span> Admin</a>
        <div class="header-account">
            <span>Xin chào, <strong><c:out value="${sessionScope.account}"/></strong></span>
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <button class="btn btn-outline" type="submit">Đăng xuất</button>
            </form>
        </div>
    </div>
</header>
<nav class="site-nav">
    <div class="nav nav-inner">
        <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories">Danh mục</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Người dùng</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/">Trang chủ</a>
    </div>
</nav>
<main><sitemesh:write property="body"/></main>
<footer class="site-footer"><strong>TechStore Administration &copy; 2026</strong></footer>
<script src="${pageContext.request.contextPath}/static/js/form-validation.js"></script>
</body>
</html>
