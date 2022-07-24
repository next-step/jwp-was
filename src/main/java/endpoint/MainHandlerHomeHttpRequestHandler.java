package endpoint;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;

public class MainHandlerHomeHttpRequestHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/index.html";

    public MainHandlerHomeHttpRequestHandler() {
        super(new Endpoint(HttpMethod.GET, ENDPOINT));
    }

    @Override
    public void handle(HttpRequestMessage httpRequestMessage) {

    }
}
