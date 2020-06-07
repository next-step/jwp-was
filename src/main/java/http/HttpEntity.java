package http;

public class HttpEntity {
    private final HttpHeaders httpHeaders;
    private final String body;

    public HttpEntity() {
        this(null, null);
    }

    public HttpEntity(HttpHeaders httpHeaders) {
        this(httpHeaders, null);
    }

    public HttpEntity(String body) {
        this(null, body);
    }

    public HttpEntity(HttpHeaders httpHeaders, String body) {
        this.httpHeaders = httpHeaders;
        this.body = body;
    }
}
