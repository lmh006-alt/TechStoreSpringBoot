package vn.iotstar.techstore.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.iotstar.techstore.entities.Category;
import vn.iotstar.techstore.services.CategoryService;
import vn.iotstar.techstore.services.CategoryImageService;

@Controller
public class HomeController {
    private final CategoryService categoryService;
    private final CategoryImageService categoryImageService;

    public HomeController(CategoryService categoryService, CategoryImageService categoryImageService) {
        this.categoryService = categoryService;
        this.categoryImageService = categoryImageService;
    }

    @GetMapping("/")
    public String root(Model model) {
        var categories = categoryService.findAll(
                PageRequest.of(0, 6, Sort.by("categoryName").ascending())).getContent();
        model.addAttribute("categories", categories);
        model.addAttribute("availableCategoryImageIds", categories.stream()
                .filter(category -> categoryImageService.exists(category.getImages()))
                .map(Category::getCategoryId)
                .collect(java.util.stream.Collectors.toSet()));
        return "web/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return root(model);
    }
}
