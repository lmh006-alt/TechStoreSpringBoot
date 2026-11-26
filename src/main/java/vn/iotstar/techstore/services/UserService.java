package vn.iotstar.techstore.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iotstar.techstore.entities.User;

public interface UserService {
    User login(String username, String password);
    User register(String username, String password, String email, String fullname, String phone);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findById(Integer id);
    Page<User> search(String keyword, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    User save(User user);
    void deleteById(Integer id);
    long countAdmins();
}
