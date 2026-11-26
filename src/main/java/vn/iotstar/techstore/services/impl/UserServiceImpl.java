package vn.iotstar.techstore.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.techstore.entities.User;
import vn.iotstar.techstore.repositories.UserRepository;
import vn.iotstar.techstore.services.UserService;
import vn.iotstar.techstore.utils.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username.trim()).orElse(null);
        if (user == null || !user.isActive() || !PasswordUtil.verify(password, user.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User register(String username, String password, String email, String fullname, String phone) {
        if (existsByUsername(username) || existsByEmail(email)) {
            return null;
        }
        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(PasswordUtil.hash(password));
        user.setEmail(email.trim());
        user.setFullname(fullname == null ? null : fullname.trim());
        user.setPhone(phone == null || phone.isBlank() ? null : phone.trim());
        user.setRoleId(2);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username.trim());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email.trim());
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> search(String keyword, Pageable pageable) {
        String value = "%" + keyword.trim() + "%";
        return userRepository.findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                value, value, value, pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public long countAdmins() {
        return userRepository.countByRoleId(1);
    }
}
