package server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.component.UpdateInterceptor;
import server.component.WebSocketConfig;

/**
 * Spring boot configuration.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final WebSocketConfig webSocketConfig;

    public WebConfig(WebSocketConfig webSocketConfig) {
        this.webSocketConfig = webSocketConfig;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UpdateInterceptor(webSocketConfig));
    }
}