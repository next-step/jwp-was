package endpoint;

public abstract class HttpRequestEndpointHandler implements HttpRequestHandler {
    public static final HttpRequestEndpointHandler NONE = new NoneEndPointHandler();

    protected Endpoint endpoint;

    public HttpRequestEndpointHandler(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
}
