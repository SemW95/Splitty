package server.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * An interceptor that will send websocket update messages after receiving POST, PUT, DELETE.
 */
@Component
@Profile("!test") // This bean will be loaded when the profile is not "test"
public class UpdateInterceptor implements HandlerInterceptor {
    private final WebSocketConfig webSocketConfig;

    public UpdateInterceptor(WebSocketConfig webSocketConfig) {
        this.webSocketConfig = webSocketConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        String method = request.getMethod();
        if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method)) {
            webSocketConfig.getWebSocketHandler().sendUpdateMessage();
        }
    }
}
