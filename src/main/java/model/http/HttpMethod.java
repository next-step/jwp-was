package model.http;

public enum HttpMethod {
    GET("GET", false),
    POST("POST", true),
    PUT("PUT", true),
    DELETE("DELETE", false),
    HEAD("HEAD", false),
    CONNECT("CONNECT", false),
    OPTIONS("OPTIONS", false),
    TRACE("TRACE", false),
    PATCH("PATCH", true);

    final private String method;

    final private boolean containsBody;

    private HttpMethod(String method, boolean containsBody) {
        this.method = method;
        this.containsBody = containsBody;
    }

    public String getMethod() {
        return method;
    }

    public boolean containsBody() {
        return containsBody;
    }

    @Override
    public String toString() {
        return "HttpMethod{" +
                "method='" + method + '\'' +
                ", containsBody=" + containsBody +
                '}';
    }
}
