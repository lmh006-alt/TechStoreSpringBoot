package vn.iotstar.techstore.configs;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.WebResourceRoot;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

public class JSPStaticResourceConfigurer implements LifecycleListener {
    private final Context context;

    public JSPStaticResourceConfigurer(Context context) {
        this.context = context;
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (!Lifecycle.CONFIGURE_START_EVENT.equals(event.getType())) {
            return;
        }
        try {
            Resource resource = new PathMatchingResourcePatternResolver()
                    .getResource("classpath:/META-INF");
            URL url = resource.getURL();
            if (ResourceUtils.isFileURL(url)) {
                context.getResources().createWebResourceSet(
                        WebResourceRoot.ResourceSetType.RESOURCE_JAR,
                        "/", url, "/");
                return;
            }
            if (ResourceUtils.isJarURL(url)) {
                URLConnection connection = url.openConnection();
                if (connection instanceof JarURLConnection jarConnection) {
                    URL jarFileUrl = jarConnection.getJarFileURL();
                    context.getResources().createWebResourceSet(
                            WebResourceRoot.ResourceSetType.RESOURCE_JAR,
                            "/", jarFileUrl, "/META-INF");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Không thể đăng ký tài nguyên JSP trong META-INF", e);
        }
    }
}
