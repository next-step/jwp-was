package webserver.http.model;

import java.util.Locale;

public enum Method {
    GET,
    POST;

    public static Method of(String methodName) {
        return valueOf(methodName);
    }

    public static boolean isPost(String methodName) {
        return POST == valueOf(methodName.toUpperCase(Locale.ROOT));
    }

    public static boolean isPost(Method method) {
        return POST == method;
    }
}
