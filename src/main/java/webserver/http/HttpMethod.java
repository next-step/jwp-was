package webserver.http;

public enum HttpMethod {
    GET("GET"),
    POST("POST")
    ;

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
