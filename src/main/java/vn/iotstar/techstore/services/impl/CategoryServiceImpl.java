package vn.iotstar.techstore.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.techstore.entities.Category;
import vn.iotstar.techstore.repositories.CategoryRepository;
import vn.iotstar.techstore.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findByNameContaining(String keyword, Pageable pageable) {
        return categoryRepository.findByCategoryNameContaining(keyword, pageable);
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        category.setCategoryName(category.getCategoryName().trim());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByCategoryNameIgnoreCase(name.trim());
    }
}
