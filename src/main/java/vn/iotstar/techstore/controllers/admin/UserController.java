package vn.iotstar.techstore.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.techstore.entities.User;
import vn.iotstar.techstore.services.UserService;
import vn.iotstar.techstore.utils.PasswordUtil;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list() {
        return "redirect:/admin/users/searchpaginated";
    }

    @GetMapping("/searchpaginated")
    public String search(ModelMap model,
                         @RequestParam(name = "keyword", required = false) String keyword,
                         @RequestParam(name = "page", required = false) Optional<Integer> page,
                         @RequestParam(name = "size", required = false) Optional<Integer> size) {
        int currentPage = page.filter(value -> value > 0).orElse(1);
        int pageSize = size.filter(value -> value == 3 || value == 5 || value == 10 || value == 15 || value == 20)
                .orElse(3);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("username"));
        String value = keyword == null ? "" : keyword.trim();
        Page<User> userPage = value.isBlank()
                ? userService.findAll(pageable)
                : userService.search(value, pageable);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0 && currentPage > totalPages) {
            pageable = PageRequest.of(totalPages - 1, pageSize, Sort.by("username"));
            userPage = value.isBlank()
                    ? userService.findAll(pageable)
                    : userService.search(value, pageable);
            currentPage = totalPages;
        }

        int from = totalPages == 0 ? 1 : Math.max(1, currentPage - 2);
        int to = totalPages == 0 ? 0 : Math.min(totalPages, currentPage + 2);
        List<Integer> pageNumbers = IntStream.rangeClosed(from, to).boxed().toList();

        model.addAttribute("userPage", userPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("keyword", value);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("user", new User());
        return "admin/user/list";
    }

    @GetMapping("/add")
    public String addForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin/user/form";
    }

    @PostMapping("/add")
    public String add(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String email,
                      @RequestParam(required = false) String fullname,
                      @RequestParam(required = false) String phone,
                      @RequestParam Integer roleId,
                      @RequestParam(defaultValue = "false") boolean active,
                      ModelMap model,
                      RedirectAttributes redirectAttributes) {
        User user = createFormUser(username, email, fullname, phone, roleId, active);
        String validationError = validateUserFields(username, password, email, fullname, phone, true);
        if (validationError != null) {
            model.addAttribute("error", validationError);
            model.addAttribute("user", user);
            return "admin/user/form";
        }
        if (userService.existsByUsername(username) || userService.existsByEmail(email)) {
            model.addAttribute("error", "Username hoặc email đã tồn tại.");
            model.addAttribute("user", user);
            return "admin/user/form";
        }
        user.setUsername(username.trim());
        user.setPassword(PasswordUtil.hash(password));
        user.setEmail(email.trim());
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRoleId(roleId == 1 ? 1 : 2);
        user.setActive(active);
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Thêm người dùng thành công.");
        return "redirect:/admin/users/searchpaginated";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id,
                           ModelMap model,
                           RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng.");
            return "redirect:/admin/users/searchpaginated";
        }
        model.addAttribute("user", user);
        return "admin/user/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,
                       @RequestParam String username,
                       @RequestParam(required = false) String password,
                       @RequestParam String email,
                       @RequestParam(required = false) String fullname,
                       @RequestParam(required = false) String phone,
                       @RequestParam Integer roleId,
                       @RequestParam(defaultValue = "false") boolean active,
                       HttpSession session,
                       ModelMap model,
                       RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng.");
            return "redirect:/admin/users/searchpaginated";
        }
        Integer sessionUserId = (Integer) session.getAttribute("userId");
        if (sessionUserId != null && sessionUserId.equals(id) && roleId != 1) {
            model.addAttribute("error", "Không thể bỏ quyền ADMIN của tài khoản đang đăng nhập.");
            model.addAttribute("user", user);
            return "admin/user/form";
        }
        String validationError = validateUserFields(username, password, email, fullname, phone, false);
        if (validationError != null) {
            model.addAttribute("error", validationError);
            model.addAttribute("user", createFormUser(id, username, email, fullname, phone, roleId, active));
            return "admin/user/form";
        }
        if (!user.getUsername().equalsIgnoreCase(username.trim()) && userService.existsByUsername(username)) {
            model.addAttribute("error", "Username đã được sử dụng.");
            model.addAttribute("user", createFormUser(id, username, email, fullname, phone, roleId, active));
            return "admin/user/form";
        }
        if (!user.getEmail().equalsIgnoreCase(email.trim()) && userService.existsByEmail(email)) {
            model.addAttribute("error", "Email đã được sử dụng.");
            model.addAttribute("user", createFormUser(id, username, email, fullname, phone, roleId, active));
            return "admin/user/form";
        }
        if (roleId != 1 && roleId != 2) {
            model.addAttribute("error", "Vai trò người dùng không hợp lệ.");
            model.addAttribute("user", user);
            return "admin/user/form";
        }
        user.setUsername(username.trim());
        user.setEmail(email.trim());
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRoleId(roleId == 1 ? 1 : 2);
        user.setActive(active);
        if (password != null && !password.isBlank()) {
            user.setPassword(PasswordUtil.hash(password));
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Cập nhật người dùng thành công.");
        return "redirect:/admin/users/searchpaginated";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Integer sessionUserId = (Integer) session.getAttribute("userId");
        if (sessionUserId != null && sessionUserId.equals(id)) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản đang đăng nhập.");
            return "redirect:/admin/users/searchpaginated";
        }
        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng.");
        } else if (user.getRoleId() != null && user.getRoleId() == 1
                && userService.countAdmins() <= 1) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản ADMIN cuối cùng.");
        } else {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa người dùng thành công.");
        }
        return "redirect:/admin/users/searchpaginated";
    }

    private User createFormUser(String username, String email, String fullname, String phone,
                                Integer roleId, boolean active) {
        return createFormUser(null, username, email, fullname, phone, roleId, active);
    }

    private User createFormUser(Integer id, String username, String email, String fullname, String phone,
                                Integer roleId, boolean active) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRoleId(roleId);
        user.setActive(active);
        return user;
    }

    private String validateUserFields(String username, String password, String email,
                                      String fullname, String phone, boolean requirePassword) {
        if (username == null || username.isBlank() || username.length() > 255) {
            return "Username là bắt buộc và không được vượt quá 255 ký tự.";
        }
        if (email == null || email.isBlank() || email.length() > 255
                || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return "Email là bắt buộc, tối đa 255 ký tự và phải đúng định dạng.";
        }
        if (fullname != null && fullname.length() > 255) {
            return "Họ tên không được vượt quá 255 ký tự.";
        }
        if (requirePassword && (password == null || password.length() < 8)) {
            return "Mật khẩu là bắt buộc và phải có ít nhất 8 ký tự.";
        }
        if (password != null && !password.isBlank() && (password.length() < 8 || password.length() > 255)) {
            return "Mật khẩu mới phải có từ 8 đến 255 ký tự.";
        }
        if (phone != null && (phone.length() > 20 || (!phone.isBlank()
                && (!phone.matches("\\+?[0-9][0-9 .()-]*")
                    || !phone.replaceAll("[^0-9]", "").matches("[0-9]{7,15}"))))) {
            return "Số điện thoại cần có 7–15 chữ số và tối đa 20 ký tự.";
        }
        return null;
    }
}
