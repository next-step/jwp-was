package endpoint;

import endpoint.api.CreateUserGetMethodEndpointHandler;
import endpoint.api.CreateUserPostMethodEndpointHandler;
import endpoint.api.LoginEndpointHandler;
import endpoint.page.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestEndpointRegistry {
    public static final Map<Endpoint, HttpRequestHandler> endpointHandlerMap = new LinkedHashMap<>();

    static {
        initializeEndpointRegistry();
    }

    public static void initializeEndpointRegistry() {
        // TODO 리플렉션 기반으로 변경.. 하나하나 등록해주기 너무 귀찮다 (어노테이션? 인터페이스?)
        List<HttpRequestEndpointHandler> endpoints = List.of(
                new MainPageHandlerHomeHttpRequestHandler(),
                new CreateUserPageHandlerHomeHttpRequestHandler(),
                new CreateUserGetMethodEndpointHandler(),
                new CreateUserPostMethodEndpointHandler(),
                new LoginPageHandlerHomeHttpRequestHandler(),
                new LoginFailedPageHandlerHomeHttpRequestHandler(),
                new LoginEndpointHandler(),
                new UserListPageHandlerHomeHttpRequestHandler()
        );

        for (HttpRequestEndpointHandler endpoint : endpoints) {

            HttpRequestHandler interceptor = getInterceptor(endpoint);
            if (interceptor != null) {
                endpointHandlerMap.put(endpoint.endpoint, interceptor);
                continue;
            }

            endpointHandlerMap.put(endpoint.endpoint, endpoint);
        }
    }

    private static HttpRequestHandler getInterceptor(HttpRequestEndpointHandler endpoint) {
        InterceptableHandler interceptableHandler = endpoint.getClass().getDeclaredAnnotation(InterceptableHandler.class);

        if (interceptableHandler == null) {
            return null;
        }

        Class<? extends HttpRequestHandler> interceptorClazz = interceptableHandler.interceptor();

        try {
            return interceptorClazz.getConstructor(HttpRequestHandler.class).newInstance(endpoint);
        } catch (Exception e) {
            return null;
        }
    }

    public static HttpRequestHandler getEndpoint(Endpoint endpoint) {
        return endpointHandlerMap.getOrDefault(endpoint, HttpRequestEndpointHandler.NONE);
    }
}
