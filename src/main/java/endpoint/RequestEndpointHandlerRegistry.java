package endpoint;

import endpoint.staticresource.StaticResourceRequestHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestEndpointHandlerRegistry {
    public static final Map<Endpoint, HttpRequestHandler> ENDPOINT_HTTP_REQUEST_HANDLER_MAP = new LinkedHashMap<>();
    private static final HttpRequestHandler STATIC_RESOURCE_REQUEST_HANDLER = new StaticResourceRequestHandler();

    static {
        initializeEndpointRegistry();
    }

    public static void initializeEndpointRegistry() {
        HttpRequestEndpointHandlers endpointHandlers = EndPointHandlerScanner.scan();

        for (HttpRequestEndpointHandler endpointHandler : endpointHandlers) {
            addInterceptorForOnlyInterceptableHandler(endpointHandler);

            ENDPOINT_HTTP_REQUEST_HANDLER_MAP.put(endpointHandler.endpoint, endpointHandler);
        }
    }

    private static void addInterceptorForOnlyInterceptableHandler(HttpRequestEndpointHandler endpointHandler) {
        HttpRequestHandler interceptor = getInterceptor(endpointHandler);
        if (interceptor == null) {
            return;
        }

        ENDPOINT_HTTP_REQUEST_HANDLER_MAP.put(endpointHandler.endpoint, interceptor);
    }

    private static HttpRequestHandler getInterceptor(HttpRequestEndpointHandler endpointHandler) {
        InterceptableHandler interceptableHandler = endpointHandler.getClass().getDeclaredAnnotation(InterceptableHandler.class);

        if (interceptableHandler == null) {
            return null;
        }

        Class<? extends HttpRequestHandler> interceptorClazz = interceptableHandler.interceptor();

        try {
            return interceptorClazz.getConstructor(HttpRequestHandler.class).newInstance(endpointHandler);
        } catch (Exception e) {
            return null;
        }
    }

    public static HttpRequestHandler getEndpointHandler(Endpoint endpoint) {
        return ENDPOINT_HTTP_REQUEST_HANDLER_MAP.getOrDefault(endpoint, STATIC_RESOURCE_REQUEST_HANDLER);
    }
}
