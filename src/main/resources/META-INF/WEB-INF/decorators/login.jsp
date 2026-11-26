<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title"/></title>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/static/favicon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <sitemesh:write property="head"/>
</head>
<body class="auth-page">
<div class="auth-layout"><sitemesh:write property="body"/></div>
<script src="${pageContext.request.contextPath}/static/js/form-validation.js"></script>
</body>
</html>
