package endpoint;

public abstract class HttpRequestEndpoint implements HttpRequestHandle {
    public static final HttpRequestEndpoint NONE = new NoneEndPoint();

    protected String httpEndpointPath;

    public HttpRequestEndpoint(String httpEndpointPath) {
        this.httpEndpointPath = httpEndpointPath;
    }
}
