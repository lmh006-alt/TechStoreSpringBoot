package vn.iotstar.techstore.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteMeshConfiguration {
    @Bean
    public FilterRegistrationBean<CustomSiteMeshFilter> siteMeshFilterRegistration() {
        FilterRegistrationBean<CustomSiteMeshFilter> registration =
                new FilterRegistrationBean<>(new CustomSiteMeshFilter());
        registration.setName("customSiteMeshFilter");
        registration.addUrlPatterns("/*");
        return registration;
    }
}
