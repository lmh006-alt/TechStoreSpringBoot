package vn.iotstar.techstore.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.techstore.services.CategoryImageService;

@Controller
public class CategoryImageController {
    private final CategoryImageService categoryImageService;

    public CategoryImageController(CategoryImageService categoryImageService) {
        this.categoryImageService = categoryImageService;
    }

    @GetMapping("/category-image")
    public void image(@RequestParam String file, HttpServletResponse response) throws IOException {
        Optional<InputStream> image = categoryImageService.open(file);
        if (image.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(categoryImageService.contentType(file));
        response.setHeader("X-Content-Type-Options", "nosniff");
        try (InputStream input = image.get()) {
            input.transferTo(response.getOutputStream());
        }
    }
}
