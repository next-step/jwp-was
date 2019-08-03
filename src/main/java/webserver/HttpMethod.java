package webserver;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {

    GET, POST, PUT, DELETE;

    private static Map<String, HttpMethod> httpMethodMap = new HashMap<>(HttpMethod.values().length);

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethodMap.put(httpMethod.name(), httpMethod);
        }
    }

    public static HttpMethod getHttpMethod(String method) {
        if (httpMethodMap.containsKey(method)) {
            return httpMethodMap.get(method);
        }

        throw new IllegalArgumentException(method + " Method Not Allowed");
    }

}
