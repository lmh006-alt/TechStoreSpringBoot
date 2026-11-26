package vn.iotstar.techstore.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iotstar.techstore.entities.Category;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByNameContaining(String keyword, Pageable pageable);
    Category findById(Integer id);
    Category save(Category category);
    void deleteById(Integer id);
    boolean existsByName(String name);
}
