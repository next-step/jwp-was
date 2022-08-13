package webserver.http.model.request;

import java.util.Locale;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod of(String methodName) {
        return valueOf(methodName);
    }

    public static boolean isPost(String methodName) {
        return POST == valueOf(methodName.toUpperCase(Locale.ROOT));
    }

    public static boolean isPost(HttpMethod httpMethod) {
        return POST == httpMethod;
    }
}
