package vn.iotstar.techstore.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.techstore.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    Page<User> findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String fullname, String email, Pageable pageable);
    long countByRoleId(Integer roleId);
}
