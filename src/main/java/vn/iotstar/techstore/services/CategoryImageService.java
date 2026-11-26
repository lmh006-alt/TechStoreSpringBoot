package vn.iotstar.techstore.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class CategoryImageService {
    private static final String IMAGE_NAME_PATTERN =
            "(?:[0-9a-fA-F-]{36}|[A-Za-z0-9][A-Za-z0-9._-]{0,100})\\.(?:png|jpe?g|gif|webp)";

    private final ResourceLoader resourceLoader;

    public CategoryImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public boolean exists(String file) {
        if (!isSafeName(file)) {
            return false;
        }
        return Files.isRegularFile(uploadPath(file)) || staticResource(file).exists();
    }

    public Optional<InputStream> open(String file) throws IOException {
        if (!isSafeName(file)) {
            return Optional.empty();
        }
        Path uploadedImage = uploadPath(file);
        if (Files.isRegularFile(uploadedImage)) {
            return Optional.of(Files.newInputStream(uploadedImage));
        }
        Resource image = staticResource(file);
        if (image.exists() && image.isReadable()) {
            return Optional.of(image.getInputStream());
        }
        return Optional.empty();
    }

    public String contentType(String file) {
        String extension = file.substring(file.lastIndexOf('.') + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "image/png";
        };
    }

    private boolean isSafeName(String file) {
        return file != null && file.matches(IMAGE_NAME_PATTERN);
    }

    private Path uploadPath(String file) {
        return Path.of(System.getProperty("user.home"), "WebAssignmentUploads", file)
                .toAbsolutePath().normalize();
    }

    private Resource staticResource(String file) {
        return resourceLoader.getResource("classpath:/static/images/" + file);
    }
}
