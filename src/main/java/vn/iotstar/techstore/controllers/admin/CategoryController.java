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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.iotstar.techstore.entities.Category;
import vn.iotstar.techstore.services.CategoryService;
import vn.iotstar.techstore.services.CategoryImageService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryImageService categoryImageService;

    public CategoryController(CategoryService categoryService, CategoryImageService categoryImageService) {
        this.categoryService = categoryService;
        this.categoryImageService = categoryImageService;
    }

    @GetMapping
    public String list() {
        return "redirect:/admin/categories/searchpaginated";
    }

    @GetMapping("/searchpaginated")
    public String search(ModelMap model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "page", required = false) Optional<Integer> page,
                         @RequestParam(name = "size", required = false) Optional<Integer> size) {
        int currentPage = page.filter(value -> value > 0).orElse(1);
        int pageSize = size.filter(value -> value == 3 || value == 5 || value == 10 || value == 15 || value == 20)
                .orElse(3);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryName"));

        String keyword = name == null ? "" : name.trim();
        Page<Category> categoryPage = keyword.isBlank()
                ? categoryService.findAll(pageable)
                : categoryService.findByNameContaining(keyword, pageable);

        int totalPages = categoryPage.getTotalPages();
        if (totalPages > 0 && currentPage > totalPages) {
            pageable = PageRequest.of(totalPages - 1, pageSize, Sort.by("categoryName"));
            categoryPage = keyword.isBlank()
                    ? categoryService.findAll(pageable)
                    : categoryService.findByNameContaining(keyword, pageable);
            currentPage = totalPages;
        }

        int from = totalPages == 0 ? 1 : Math.max(1, currentPage - 2);
        int to = totalPages == 0 ? 0 : Math.min(totalPages, currentPage + 2);
        List<Integer> pageNumbers = IntStream.rangeClosed(from, to).boxed().toList();

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("availableCategoryImageIds", categoryPage.getContent().stream()
                .filter(category -> categoryImageService.exists(category.getImages()))
                .map(Category::getCategoryId)
                .collect(java.util.stream.Collectors.toSet()));
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("name", keyword);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("category", new Category());
        return "admin/category/list";
    }

    @GetMapping("/add")
    public String addForm(ModelMap model) {
        model.addAttribute("category", new Category());
        return "admin/category/form";
    }

    @PostMapping("/add")
    public String add(@Valid Category category,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return "admin/category/form";
        }
        if (categoryService.existsByName(category.getCategoryName())) {
            model.addAttribute("error", "Tên danh mục đã được sử dụng.");
            model.addAttribute("category", category);
            return "admin/category/form";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", "Thêm danh mục thành công.");
        return "redirect:/admin/categories/searchpaginated";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id,
                           ModelMap model,
                           RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy danh mục.");
            return "redirect:/admin/categories/searchpaginated";
        }
        model.addAttribute("category", category);
        return "admin/category/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,
                       @Valid Category category,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       ModelMap model) {
        Category existing = categoryService.findById(id);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy danh mục.");
            return "redirect:/admin/categories/searchpaginated";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return "admin/category/form";
        }
        String categoryName = category.getCategoryName().trim();
        if (!existing.getCategoryName().equalsIgnoreCase(categoryName)
                && categoryService.existsByName(categoryName)) {
            model.addAttribute("error", "Tên danh mục đã được sử dụng.");
            model.addAttribute("category", category);
            return "admin/category/form";
        }
        category.setCategoryId(id);
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", "Cập nhật danh mục thành công.");
        return "redirect:/admin/categories/searchpaginated";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (categoryService.findById(id) != null) {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa danh mục thành công.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy danh mục.");
        }
        return "redirect:/admin/categories/searchpaginated";
    }
}
