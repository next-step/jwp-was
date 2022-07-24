package endpoint;

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
                new CreateUserGetMethodEndpointHandler(),
                new CreateUserPostMethodEndpointHandler()
        );

        for (HttpRequestEndpointHandler endpointHandler : endpoints) {
            endpointHandlerMap.put(endpointHandler.endpoint, endpointHandler);
        }
    }

    public static HttpRequestEndpointHandler getEndpoint(Endpoint endpoint) {
        return endpointHandlerMap.getOrDefault(endpoint, HttpRequestEndpointHandler.NONE);
    }
}
