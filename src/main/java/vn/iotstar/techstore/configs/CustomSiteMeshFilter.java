package vn.iotstar.techstore.configs;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;

public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder
            .addDecoratorPath("/login", "/login.jsp")
            .addDecoratorPath("/register", "/login.jsp")
            .addDecoratorPath("/admin/*", "/admin.jsp")
            .addDecoratorPath("/*", "/web.jsp")
            .addExcludedPath("/alogin")
            .addExcludedPath("/category-image")
            .addExcludedPath("/api/*")
            .addExcludedPath("/static/*");
    }
}
