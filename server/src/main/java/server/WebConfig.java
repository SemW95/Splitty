package server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.component.UpdateInterceptor;

/**
 * Spring boot configuration.
 */
@Configuration
@Profile("!test")
// In order for the websockets not to be run this configuration will not be active
// when the profile is "test"
public class WebConfig implements WebMvcConfigurer {
    private final UpdateInterceptor updateInterceptor;

    public WebConfig(UpdateInterceptor updateInterceptor) {
        this.updateInterceptor = updateInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(updateInterceptor);
    }
}