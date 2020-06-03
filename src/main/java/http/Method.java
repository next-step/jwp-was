package http;

public enum Method {

    GET("GET"),
    POST("POST");

    private String method;

    Method(final String method) {
        this.method = method;
    }
}
