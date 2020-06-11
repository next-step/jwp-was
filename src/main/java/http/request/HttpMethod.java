package http.request;

public enum HttpMethod {
    POST("POST"),
    GET("GET");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
