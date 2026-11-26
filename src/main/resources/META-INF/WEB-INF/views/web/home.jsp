<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><title>Trang chủ | TechStore</title></head>
<body>
<div class="page-container">
    <section class="hero">
        <div class="hero-content">
            <p class="eyebrow">TECHSTORE · CỬA HÀNG CÔNG NGHỆ</p>
            <h1>Khám phá công nghệ.<br>Chọn đúng sản phẩm.</h1>
            <p>Quản lý danh mục sản phẩm và tài khoản trong một không gian gọn gàng, dễ sử dụng.</p>
            <c:choose>
                <c:when test="${sessionScope.roleId == 1}">
                    <a class="btn" href="${pageContext.request.contextPath}/admin/categories">Quản lý danh mục</a>
                </c:when>
                <c:otherwise>
                    <a class="btn" href="${pageContext.request.contextPath}/login">Đăng nhập để tiếp tục</a>
                </c:otherwise>
            </c:choose>
        </div>
        <svg class="hero-art" viewBox="0 0 24 24" fill="none" stroke="currentColor"
             stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <rect x="3" y="4" width="18" height="13" rx="1"/>
            <path d="M2 20h20l-2-3H4l-2 3Zm10-3v3"/>
        </svg>
    </section>

    <section aria-labelledby="categories-heading">
        <div class="section-heading">
            <div>
                <p class="eyebrow">KHÁM PHÁ</p>
                <h2 id="categories-heading">Danh mục sản phẩm</h2>
            </div>
            <c:if test="${sessionScope.roleId == 1}">
                <a class="text-link" href="${pageContext.request.contextPath}/admin/categories">Xem tất cả danh mục &rarr;</a>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${empty categories}">
                <div class="empty-state">
                    <h3>Chưa có danh mục sản phẩm</h3>
                    <p>Danh mục sẽ xuất hiện tại đây sau khi được thêm vào hệ thống.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="category-grid">
                    <c:forEach var="category" items="${categories}">
                        <article class="category-card">
                            <c:choose>
                                <c:when test="${availableCategoryImageIds.contains(category.categoryId)}">
                                    <c:url var="imageUrl" value="/category-image">
                                        <c:param name="file" value="${category.images}"/>
                                    </c:url>
                                    <img class="category-image" src="${imageUrl}" alt="<c:out value='${category.categoryName}'/>"
                                         loading="lazy" onerror="this.hidden=true;this.nextElementSibling.hidden=false">
                                    <span class="category-image-placeholder" hidden>Chưa có ảnh</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="category-image-placeholder" aria-hidden="true">TS</span>
                                </c:otherwise>
                            </c:choose>
                            <h3><c:out value="${category.categoryName}"/></h3>
                        </article>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</div>
</body>
</html>
