package vn.iotstar.techstore.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.techstore.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByCategoryNameContaining(String keyword, Pageable pageable);
    Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
