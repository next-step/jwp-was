package endpoint;

import endpoint.api.CreateUserGetMethodEndpointHandler;
import endpoint.api.CreateUserPostMethodEndpointHandler;
import endpoint.api.LoginEndpointHandler;
import endpoint.page.CreateUserPageHandlerHomeHttpRequestHandler;
import endpoint.page.LoginFailedPageHandlerHomeHttpRequestHandler;
import endpoint.page.LoginPageHandlerHomeHttpRequestHandler;
import endpoint.page.MainPageHandlerHomeHttpRequestHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestEndpointRegistry {
    public static final Map<Endpoint, HttpRequestEndpointHandler> endpointHandlerMap = new LinkedHashMap<>();

    static {
        initializeEndpointRegistry();
    }

    public static void initializeEndpointRegistry() {
        List<HttpRequestEndpointHandler> endpoints = List.of(
                new MainPageHandlerHomeHttpRequestHandler(),
                new CreateUserPageHandlerHomeHttpRequestHandler(),
                new CreateUserGetMethodEndpointHandler(),
                new CreateUserPostMethodEndpointHandler(),
                new LoginPageHandlerHomeHttpRequestHandler(),
                new LoginFailedPageHandlerHomeHttpRequestHandler(),
                new LoginEndpointHandler()
        );

        for (HttpRequestEndpointHandler endpointHandler : endpoints) {
            endpointHandlerMap.put(endpointHandler.endpoint, endpointHandler);
        }
    }

    public static HttpRequestEndpointHandler getEndpoint(Endpoint endpoint) {
        return endpointHandlerMap.getOrDefault(endpoint, HttpRequestEndpointHandler.NONE);
    }
}
