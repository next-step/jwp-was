package endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestEndpointRegistry {
    public static final Map<String, HttpRequestEndpointHandler> endpointMap = new LinkedHashMap<>();

    static {
        initializeEndpointRegistry();
    }

    public static void initializeEndpointRegistry() {
        List<HttpRequestEndpointHandler> endpoints = List.of(
                new CreateUserHttpRequestEndpointHandler()
        );

        for (HttpRequestEndpointHandler endpoint : endpoints) {
            endpointMap.put(endpoint.httpEndpointPath, endpoint);
        }
    }

    public static HttpRequestEndpointHandler getEndpoint(String endPoint) {
        return endpointMap.getOrDefault(endPoint, HttpRequestEndpointHandler.NONE);
    }
}
