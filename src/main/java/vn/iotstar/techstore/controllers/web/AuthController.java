package vn.iotstar.techstore.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.techstore.entities.User;
import vn.iotstar.techstore.services.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("account") != null) {
            return "redirect:/home";
        }
        return "web/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request,
                        Model model) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("alert", "Vui lòng nhập tên đăng nhập và mật khẩu.");
            return "web/login";
        }
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("alert", "Tên đăng nhập hoặc mật khẩu không đúng, hoặc tài khoản chưa hoạt động.");
            return "web/login";
        }

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("account", user.getUsername());
        session.setAttribute("userId", user.getId());
        session.setAttribute("roleId", user.getRoleId());
        session.setMaxInactiveInterval(30 * 60);
        session.setAttribute("csrfToken", java.util.UUID.randomUUID().toString());

        return user.getRoleId() != null && user.getRoleId() == 1
                ? "redirect:/admin/categories"
                : "redirect:/home";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "web/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String email,
                           @RequestParam(required = false) String fullname,
                           @RequestParam(required = false) String phone,
                           Model model) {
        if (username == null || username.isBlank() || password == null || password.isBlank()
                || email == null || email.isBlank()) {
            model.addAttribute("alert", "Vui lòng nhập tên đăng nhập, mật khẩu và email.");
            return "web/register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("alert", "Mật khẩu xác nhận không khớp.");
            return "web/register";
        }
        if (password.length() < 8) {
            model.addAttribute("alert", "Mật khẩu phải có ít nhất 8 ký tự.");
            return "web/register";
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            model.addAttribute("alert", "Địa chỉ email không hợp lệ.");
            return "web/register";
        }
        if (userService.existsByUsername(username)) {
            model.addAttribute("alert", "Tên đăng nhập đã tồn tại.");
            return "web/register";
        }
        if (userService.existsByEmail(email)) {
            model.addAttribute("alert", "Email đã được sử dụng.");
            return "web/register";
        }

        User user = userService.register(username, password, email, fullname, phone);
        if (user == null) {
            model.addAttribute("alert", "Không thể hoàn tất đăng ký.");
            return "web/register";
        }
        model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "web/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
