package webserver.interceptors;

import webserver.domain.DefaultView;
import webserver.domain.HttpRequest;
import webserver.domain.ResponseEntity;

import javax.annotation.Nullable;

public interface Interceptor {
    default boolean preHandle(HttpRequest request, Object handler) throws Exception{

        return true;
    }

    default void postHandle(HttpRequest request, ResponseEntity<?> response, Object handler,
                            DefaultView view) throws Exception {
    }

    default void afterCompletion(HttpRequest request, ResponseEntity<?> response, Object Handler,
                                 @Nullable Exception ex) throws Exception{
    }
}
