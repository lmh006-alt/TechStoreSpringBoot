package vn.iotstar.techstore.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @Column(length = 255)
    private String fullname;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 255)
    private String avatar;

    @Column(name = "roleid")
    private Integer roleId = 2;

    @Column(length = 20)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "otp_code", length = 255)
    private String otpCode;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

    @Column(name = "reset_otp_code", length = 255)
    private String resetOtpCode;

    @Column(name = "reset_otp_expiry")
    private LocalDateTime resetOtpExpiry;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
    public LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }
    public String getResetOtpCode() { return resetOtpCode; }
    public void setResetOtpCode(String resetOtpCode) { this.resetOtpCode = resetOtpCode; }
    public LocalDateTime getResetOtpExpiry() { return resetOtpExpiry; }
    public void setResetOtpExpiry(LocalDateTime resetOtpExpiry) { this.resetOtpExpiry = resetOtpExpiry; }
}
