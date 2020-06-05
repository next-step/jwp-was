package http;

public class HttpEntity<T> {
    private static final HttpEntity EMPTY = new HttpEntity<>();

    private final HttpHeaders httpHeaders;
    private final T Body;

    public HttpEntity() {
        this(null, null);
    }

    public HttpEntity(HttpHeaders httpHeaders) {
        this(httpHeaders, null);
    }

    public HttpEntity(T body) {
        this(null, body);
    }

    public HttpEntity(HttpHeaders httpHeaders, T body) {
        this.httpHeaders = httpHeaders;
        Body = body;
    }


}
