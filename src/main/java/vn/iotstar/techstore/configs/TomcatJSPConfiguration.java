package vn.iotstar.techstore.configs;

import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatJSPConfiguration {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> jspCustomizer() {
        return factory -> factory.addContextCustomizers(context -> {
            context.addLifecycleListener(new JSPStaticResourceConfigurer(context));
        });
    }
}
