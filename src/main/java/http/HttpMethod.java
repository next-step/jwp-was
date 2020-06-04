package http;

public enum HttpMethod {
    POST("POST"),
    GET("GET");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
