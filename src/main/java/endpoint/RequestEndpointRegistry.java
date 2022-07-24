package endpoint;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestEndpointRegistry {
    public static final Map<String, HttpRequestEndpoint> endpointMap = new LinkedHashMap<>();

    static {
        initializeEndpointRegistry();
    }

    public static void initializeEndpointRegistry() {
        List<HttpRequestEndpoint> endpoints = List.of(
                new CreateUserHttpRequestHandle()
        );

        for (HttpRequestEndpoint endpoint : endpoints) {
            endpointMap.put(endpoint.httpEndpointPath, endpoint);
        }
    }

    public static HttpRequestEndpoint getEndpoint(String endPoint) {
        return endpointMap.getOrDefault(endPoint, HttpRequestEndpoint.NONE);
    }
}
