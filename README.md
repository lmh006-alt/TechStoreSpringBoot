# TechStoreSpringBoot

Dự án chuyển đổi TechStore sang Spring Boot 4/JAR, JSP/JSTL, Spring Data JPA và MySQL.

## Công nghệ

- Java 21
- Spring Boot 4.0.0
- Spring MVC, Spring Data JPA, Jakarta Validation
- JSP/JSTL và Embedded Tomcat
- SiteMesh 3.3.0-RC1
- Bootstrap 5
- MySQL 8

## Cấu hình

Dự án sử dụng database `banhang` của TechStore cũ và không chạy `ddl-auto=create/create-drop`.

Thiết lập thông tin database bằng biến môi trường:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="<mat-khau>"
```

Sau đó chạy:

```powershell
.\mvnw.cmd clean package
java -jar target\TechStoreSpringBoot-1.0.0-SNAPSHOT.jar
```

Ứng dụng chạy tại `http://localhost:8090`.

## Chức năng

- Đăng ký, đăng nhập, đăng xuất bằng HttpSession
- Phân quyền USER/ADMIN bằng Interceptor
- CSRF cho POST
- CRUD Category với tìm kiếm và phân trang
- CRUD User với tìm kiếm, phân trang và quản lý role/trạng thái
- JSP nằm trong `src/main/resources/META-INF/WEB-INF`

## URL chính

- `/`
- `/login`
- `/register`
- `/logout`
- `/admin/categories/searchpaginated`
- `/admin/users/searchpaginated`

## Tài khoản ADMIN

Dùng tài khoản ADMIN có sẵn trong bảng `User` với `roleid = 1`, hoặc thêm bằng MySQL trong môi trường được cấp quyền. Không commit mật khẩu database.
