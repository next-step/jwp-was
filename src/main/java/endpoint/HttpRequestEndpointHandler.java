package endpoint;

public abstract class HttpRequestEndpointHandler implements HttpRequestHandler {
    public static final HttpRequestEndpointHandler NONE = new NoneEndPointHandler();

    protected String httpEndpointPath;

    public HttpRequestEndpointHandler(String httpEndpointPath) {
        this.httpEndpointPath = httpEndpointPath;
    }
}
