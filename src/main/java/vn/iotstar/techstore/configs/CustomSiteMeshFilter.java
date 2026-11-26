package vn.iotstar.techstore.configs;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;

public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder
            .addDecoratorPath("/*", "/web.jsp")
            .addDecoratorPath("/admin/*", "/admin.jsp")
            .addExcludedPath("/login")
            .addExcludedPath("/register")
            .addExcludedPath("/alogin")
            .addExcludedPath("/api/*")
            .addExcludedPath("/static/*");
    }
}
