# TechStoreSpringBoot

Ứng dụng chuyển đổi TechStore sang Spring Boot 4, JSP/JSTL, SiteMesh 3, Spring Data JPA và MySQL. Ứng dụng chạy bằng JAR với Embedded Tomcat, mặc định tại `http://localhost:8090`.

## Công nghệ và cấu trúc

- Java 21, Spring Boot 4.0.0, Spring MVC, Spring Data JPA
- JSP/JSTL trong `src/main/resources/META-INF/WEB-INF/views`
- SiteMesh 3 trong `src/main/resources/META-INF/WEB-INF/decorators`
- Embedded Tomcat và MySQL; Hibernate dùng `ddl-auto=validate`
- CSS, JavaScript và favicon trong `src/main/resources/static`

Không chuyển JSP sang template engine khác. Cấu hình `JSPStaticResourceConfigurer` và `TomcatJSPConfiguration` đăng ký tài nguyên JSP trong `META-INF` cho cả chạy từ Maven và JAR.

## Database và cấu hình

Ứng dụng dùng database `banhang` và không tự tạo, xóa hoặc sửa schema. Trên database hiện có, không chạy lại các script dump có câu lệnh `DROP TABLE`; hãy giữ nguyên dữ liệu và cấu trúc đã được kiểm thử.

Chỉ với database mới, có thể tạo schema tối thiểu dưới đây trước khi chạy ứng dụng. Không chạy đoạn SQL này lên database hiện có; Hibernate được cấu hình `validate` và sẽ không tự sửa schema.

```sql
CREATE DATABASE IF NOT EXISTS banhang
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE banhang;

CREATE TABLE Category (
    cate_id INT NOT NULL AUTO_INCREMENT,
    cate_name VARCHAR(255) NOT NULL,
    icons VARCHAR(255),
    PRIMARY KEY (cate_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `User` (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    fullname VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    roleid INT DEFAULT 2,
    phone VARCHAR(20),
    is_active TINYINT(1) NOT NULL DEFAULT 0,
    otp_code VARCHAR(255),
    otp_expiry DATETIME,
    reset_otp_code VARCHAR(255),
    reset_otp_expiry DATETIME,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

Thiết lập thông tin kết nối bằng biến môi trường (không ghi mật khẩu vào Git):

```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "<mat-khau-mysql>"
```

Các giá trị mặc định và URL có thể được cấu hình trong `src/main/resources/application.properties`. Không thay đổi tài khoản ADMIN đang dùng để kiểm thử.

## Build và chạy

Cần cài Java 21, Maven và MySQL trước. Từ thư mục project:

```powershell
mvn clean package
mvn clean spring-boot:run
```

Hoặc chạy JAR sau khi build:

```powershell
java -jar target\TechStoreSpringBoot-1.0.0-SNAPSHOT.jar
```

Maven Wrapper đã được cấu hình với Maven 3.9.9:

```powershell
.\mvnw.cmd clean package
```

## Chức năng hiện có

- Đăng ký, đăng nhập, đăng xuất qua session; phân quyền USER/ADMIN.
- Băm mật khẩu bằng PBKDF2-HMAC-SHA256; kiểm tra CSRF cho POST.
- CRUD danh mục và người dùng; tìm kiếm, phân trang với `Page`, `Pageable`, `PageRequest`, `Sort`.
- Validation phía máy chủ và trình duyệt cho các biểu mẫu chính.
- Giao diện responsive, thông báo kết quả CRUD, xác nhận thao tác xóa, trạng thái danh sách trống và trang lỗi 403/404/500.
- Hiển thị ảnh danh mục nếu ảnh có trong `static/images` hoặc thư mục upload cũ `~/WebAssignmentUploads`.

## Đối chiếu với TechStore cũ

| Chức năng | Trạng thái |
| --- | --- |
| Đăng nhập, đăng ký, đăng xuất, phân quyền | Đã chuyển đổi |
| Băm mật khẩu | Đã chuyển đổi (PBKDF2-HMAC-SHA256) |
| Kết nối MySQL và ánh xạ entity JPA | Đã chuyển đổi |
| CRUD danh mục/người dùng, tìm kiếm, phân trang | Đã chuyển đổi |
| Validation | Đã chuyển đổi một phần; các luồng chính có kiểm tra phía máy chủ và biểu mẫu |
| OTP qua email, kích hoạt tài khoản | Chưa chuyển đổi; đăng ký hiện tại kích hoạt tài khoản trực tiếp |
| Quên và đặt lại mật khẩu | Chưa chuyển đổi |
| Quản lý và phát video | Chưa chuyển đổi |
| Upload file mới | Chưa chuyển đổi; chỉ đọc ảnh danh mục sẵn có, không có luồng upload |
| Cập nhật hồ sơ cá nhân | Chưa chuyển đổi |
| Cloudinary | Chưa chuyển đổi |

Các chức năng chưa chuyển đổi được liệt kê để đối chiếu, không được triển khai hoặc dùng để thay đổi schema trong phạm vi hiện tại.
