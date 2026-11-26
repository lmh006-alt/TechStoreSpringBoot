package vn.iotstar.techstore.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.techstore.filters.AdminAuthorizationInterceptor;
import vn.iotstar.techstore.filters.CsrfFilter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    public WebConfiguration(AdminAuthorizationInterceptor adminAuthorizationInterceptor) {
        this.adminAuthorizationInterceptor = adminAuthorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthorizationInterceptor).addPathPatterns("/admin/**");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Bean
    public FilterRegistrationBean<Filter> errorDispatcherEncodingFilter() {
        Filter filter = (request, response, chain) -> {
            ((HttpServletResponse) response).setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        };
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(filter);
        registration.setName("errorDispatcherEncodingFilter");
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.ERROR);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CsrfFilter> csrfFilterRegistration() {
        FilterRegistrationBean<CsrfFilter> registration = new FilterRegistrationBean<>(new CsrfFilter());
        registration.setName("csrfFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }
}
