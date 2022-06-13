package webserver.request;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod from(String method) {
        return valueOf(method);
    }
}
