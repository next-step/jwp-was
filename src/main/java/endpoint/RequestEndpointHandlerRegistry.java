package endpoint;

import endpoint.staticresource.StaticResourceRequestHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestEndpointHandlerRegistry {
    private static final Map<Endpoint, HttpRequestHandler> ENDPOINT_HTTP_REQUEST_HANDLER_MAP = new LinkedHashMap<>();
    private static final HttpRequestHandler STATIC_RESOURCE_REQUEST_HANDLER = new StaticResourceRequestHandler();

    private RequestEndpointHandlerRegistry() {
    }

    public static void initializeEndpointRegistry() {
        HttpRequestEndpointHandlers endpointHandlers = EndPointHandlerScanner.scan();

        for (HttpRequestEndpointHandler endpointHandler : endpointHandlers) {
            ENDPOINT_HTTP_REQUEST_HANDLER_MAP.put(endpointHandler.endpoint, endpointHandler);
        }
    }

    private static HttpRequestHandler getInterceptor(HttpRequestHandler endpointHandler) {
        InterceptableHandler interceptableHandler = endpointHandler.getClass().getDeclaredAnnotation(InterceptableHandler.class);

        if (interceptableHandler == null) {
            return endpointHandler;
        }

        Class<? extends HttpRequestHandler> interceptorClazz = interceptableHandler.interceptor();

        try {
            return getInterceptor(interceptorClazz.getConstructor(HttpRequestHandler.class).newInstance(endpointHandler));
        } catch (Exception e) {
            return endpointHandler;
        }
    }

    public static HttpRequestHandler getEndpointHandler(Endpoint endpoint) {
        return getInterceptor(ENDPOINT_HTTP_REQUEST_HANDLER_MAP.getOrDefault(endpoint, STATIC_RESOURCE_REQUEST_HANDLER));
    }
}
