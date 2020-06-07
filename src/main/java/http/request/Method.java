package http.request;

import utils.RequestLineUtils;

import java.util.Arrays;

public enum Method {
    POST("POST"),
    GET("GET");

    private String method;

    Method(String method) {
        this.method = method;
    }

    public static Method match(String requestLine) {
        return Arrays.stream(values())
                .filter(m -> requestLine.startsWith(m.method))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not Found Matched HTTP method!"));
    }

    public static boolean isGet(String requestLine) {
        return GET.equals(match(requestLine));
    }

    public static boolean hasQueryString(String requestLine) {
        return RequestLineUtils.hasQueryStrings(requestLine);
    }
}
