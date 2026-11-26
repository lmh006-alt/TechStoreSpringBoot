<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lỗi ${status} | TechStore</title>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/static/favicon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body class="site-page">
<header class="site-header">
    <div class="header-inner">
        <a class="logo" href="${pageContext.request.contextPath}/">Tech<span>Store</span></a>
        <a class="btn btn-outline" href="${pageContext.request.contextPath}/">Trang chủ</a>
    </div>
</header>
<main class="page-container page-narrow">
    <section class="error-page panel" role="alert">
        <p class="eyebrow">TECHSTORE · THÔNG BÁO</p>
        <h1><c:out value="${status}"/></h1>
        <c:choose>
            <c:when test="${status == 403}">
                <h2>Bạn không có quyền truy cập</h2>
                <p>Tài khoản hiện tại không được phép mở trang này.</p>
            </c:when>
            <c:when test="${status == 404}">
                <h2>Không tìm thấy trang</h2>
                <p>Địa chỉ có thể đã thay đổi hoặc nội dung không còn tồn tại.</p>
            </c:when>
            <c:otherwise>
                <h2>Đã xảy ra lỗi</h2>
                <p>Hệ thống chưa thể hoàn thành yêu cầu. Vui lòng thử lại sau.</p>
            </c:otherwise>
        </c:choose>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/">Về trang chủ</a>
    </section>
</main>
<footer class="site-footer"><strong>TechStore &copy; 2026</strong></footer>
</body>
</html>
