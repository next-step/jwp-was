package webserver.http;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod find(String method) {
        return HttpMethod.valueOf(method.toUpperCase());
    }

}
